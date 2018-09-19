package server.service;

import com.google.common.collect.Maps;
import common.Logger;
import common.model.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import server.model.Session;

import java.util.Map;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class SessionService {
    private final static AttributeKey<Session> CTX_SESSION_ATTR_KEY = AttributeKey
            .valueOf("CTX2SESSION");
    private static Map<Integer, Session> sessions = Maps.newConcurrentMap();

    static void sendTo(int id, Message message) {
        Logger.server.debug("send message {} to {}", message, id);
        Session session = sessions.get(id);
        if (session == null || session.getCtx() == null || session.getCtx().isRemoved()) {
            if (session != null) {
                sessions.values().remove(session);
            }
            return;
        }
        session.getCtx().writeAndFlush(message);
    }

    public static void add(ChannelHandlerContext ctx) {
        Session session = new Session(ctx);
        int id = session.getId();
        bindSessionToCtx(session);
        sessions.put(id, session);
    }

    public static boolean add(Session session) {
        if (sessions.containsKey(session.getId())) {
            return false;
        } else {
            sessions.put(session.getId(), session);
            return true;
        }
    }

    public static void remove(ChannelHandlerContext ctx) {
        Session session = get(ctx);
        if (session != null) {
            int id = session.getId();
            sessions.remove(id);
        }
    }

    public static void remove(int id) {
        sessions.remove(id);
    }

    public static Session get(ChannelHandlerContext ctx) {
        return ctx.attr(CTX_SESSION_ATTR_KEY).get();
    }

    public static int size() {
        return sessions.size();
    }

    private static void bindSessionToCtx(Session session) {
        session.getCtx().attr(CTX_SESSION_ATTR_KEY).setIfAbsent(session);
    }
}
