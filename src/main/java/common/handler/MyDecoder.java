package common.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
        String msgStr = byteBuf.toString(Charset.defaultCharset());
        JsonObject msg = new JsonParser().parse(msgStr).getAsJsonObject();
        Message message = new Message(code, msg);
        out.add(message);
    }
}
