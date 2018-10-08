package server.processor;

import common.model.Message;
import io.netty.channel.ChannelHandlerContext;
import server.annotation.Processor;
import server.model.Session;
import server.service.RoomService;
import server.service.SessionService;
import server.service.UserService;

/**
 * @author guyue
 * @date 2018/9/17
 */
@Processor(isLogin = true)
public final class LoginProcessor implements IProcessor {
    @Override
    public void process(ChannelHandlerContext ctx, Message message) {
        Session session = SessionService.get(ctx);
        int id = idGet(message);
        String pswd = pswdGet(message);
        if (UserService.online(session.getId()) || !UserService.loginAndRegister(id, pswd, session)) {
            SessionService.remove(ctx);
            ctx.close();
        }
        RoomService.comeIn(id);
    }

    private String pswdGet(Message msg) {
        return msg.getMsg().get("pswd").getAsString();
    }

    private int idGet(Message msg) {
        return msg.getMsg().get("id").getAsInt();
    }
}
