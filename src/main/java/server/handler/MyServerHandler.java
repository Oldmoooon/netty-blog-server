package server.handler;

import common.Logger;
import common.model.Message;
import common.model.Time;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import server.service.RoomService;
import server.service.SessionService;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class MyServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new Message(new Time().toString()));
        int id = SessionService.get(ctx).getId();
        RoomService.comeIn(id);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        int id = SessionService.get(ctx).getId();
        RoomService.speakIn(id, message);
        if (RoomService.QUIT_MESSAGE.equals(message.getMsg())) {
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Logger.server.error(cause);
        ctx.close();
    }
}
