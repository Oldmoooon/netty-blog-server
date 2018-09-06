package server.handler;

import common.Logger;
import common.model.Time;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class MyServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        Logger.server.debug("channel registered.");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        Logger.server.debug("channel unregistered.");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelFuture future = ctx.writeAndFlush(new Time()).sync();
        if (future.isSuccess()) {
            Logger.server.debug("result to {} is success.", future.channel().remoteAddress());
            ctx.close();
        } else {
            Logger.server.error(future.cause());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Logger.server.error(cause);
        ctx.close();
    }
}
