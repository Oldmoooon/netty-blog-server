package server.handler;

import common.Logger;
import common.OpCode;
import common.model.Message;
import common.model.Time;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import server.ProcessorEnum;
import server.annotation.Processor;
import server.model.Session;
import server.processor.IProcessor;
import server.service.SessionService;
import server.service.UserService;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class MyServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new Message(new Time().toString()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        int code = ((Message) msg).getCode();
        Session session = SessionService.get(ctx);
        IProcessor processor = ProcessorEnum.processorGet(OpCode.valueOf(code));
        if (UserService.get(session.getId()) == null
                && !processor.getClass().getAnnotation(Processor.class).isLogin()) {
            SessionService.remove(ctx);
            ctx.close();
            return;
        }
        processor.process(ctx, message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Logger.server.error(cause);
        ctx.close();
    }
}
