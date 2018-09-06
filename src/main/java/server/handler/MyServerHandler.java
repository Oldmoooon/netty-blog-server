package server.handler;

import cn.hutool.core.util.CharsetUtil;
import common.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class MyServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Logger.server.debug("channel registered.");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Logger.server.debug("channel unregistered.");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer(8);
        buffer.writeLong(System.currentTimeMillis());
        ChannelFuture future = ctx.writeAndFlush(buffer).sync();
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
