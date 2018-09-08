package client.handler;

import common.Logger;
import common.model.Message;
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
            Message message = (Message) msg;
            Logger.server.debug("remote: {}", message);
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
