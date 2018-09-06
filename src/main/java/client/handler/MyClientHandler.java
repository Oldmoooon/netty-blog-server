package client.handler;

import common.Logger;
import common.model.Time;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class MyClientHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            Time time = (Time) msg;
            Logger.server.debug("Time: {}", time);
        } catch (Exception e) {
            Logger.client.error("msg type is {}.", msg.getClass());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Logger.server.error(cause);
        ctx.close();
    }
}
