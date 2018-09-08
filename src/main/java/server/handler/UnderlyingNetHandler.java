package server.handler;

import common.Constants;
import common.Logger;
import common.model.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import server.model.Session;
import server.service.RoomService;
import server.service.SessionService;
import server.service.UserService;

/**
 * 一个处理网络相关事情的handler，上层的handler不再关注网络相关
 *
 * @author guyue
 * @date 2018/9/7
 */
public class UnderlyingNetHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Logger.server.info("{} registered.", ctx);
        SessionService.add(ctx);
        Session session = SessionService.get(ctx);
        if (!UserService.loginAndRegister(session.getId(), Constants.INIT_PSWD, session)) {
            SessionService.remove(ctx);
            ctx.close();
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Logger.server.info("{} unregistered.", ctx);
        SessionService.remove(ctx);
        UserService.logout(SessionService.get(ctx).getId());
        ctx.fireChannelRead(new Message(RoomService.QUIT_MESSAGE));
    }
}
