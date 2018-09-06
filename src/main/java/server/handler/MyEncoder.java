package server.handler;

import common.model.Time;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class MyEncoder extends MessageToByteEncoder<Time> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Time msg, ByteBuf out) throws Exception {
        out.writeLong(msg.getTimeMillis());
    }
}
