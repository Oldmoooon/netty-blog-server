package common.handler;

import common.model.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class MyEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        int code = msg.getCode();
        out.writeInt(code);
        byte[] bytes = msg.getMsg().getBytes();
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
