package common.handler;

import common.model.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class MyDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int code = in.readInt();
        int length = in.readInt();
        ByteBuf byteBuf = in.readBytes(length);
        String msg = byteBuf.toString(Charset.defaultCharset());
        Message message = new Message(code, msg);
        out.add(message);
    }
}
