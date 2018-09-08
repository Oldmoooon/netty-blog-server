package server.model;

import io.netty.channel.ChannelHandlerContext;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class Session {
    private static AtomicInteger idGenerator = new AtomicInteger(1);

    private int id = idGenerator.getAndIncrement();

    private ChannelHandlerContext ctx;

    public Session() {
    }

    public Session(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        Session session = (Session) o;
        return id == session.id &&
                Objects.equals(ctx, session.ctx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ctx);
    }
}
