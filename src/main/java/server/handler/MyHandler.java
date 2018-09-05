package server.handler;

import cn.hutool.core.util.CharsetUtil;
import common.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class MyHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Logger.server.debug("channel registered.");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Logger.server.debug("channel unregistered.");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf in = (ByteBuf) msg;
            Logger.server.debug(in.toString(CharsetUtil.CHARSET_UTF_8));
            ctx.write(msg);
            ctx.flush();
        } else {
            Logger.server.error("msg is not instance of byteBuf : {}", msg.toString());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Logger.server.error(cause);
        ctx.close();
    }
}
