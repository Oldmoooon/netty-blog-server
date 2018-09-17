package server.processor;

import common.model.Message;
import io.netty.channel.ChannelHandlerContext;
import server.annotation.Processor;
import server.service.RoomService;
import server.service.SessionService;

/**
 * @author guyue
 * @date 2018/9/17
 */
@Processor
public final class ChatProcessor implements IProcessor{
    @Override
    public void process(ChannelHandlerContext ctx, Message message) {
        int id = SessionService.get(ctx).getId();
        RoomService.speakIn(id, message);
        if (RoomService.QUIT_MESSAGE.equals(message.getMsg())) {
            ctx.close();
        }
    }
}
