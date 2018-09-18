package server.processor;

import com.google.gson.JsonObject;
import common.model.Message;
import io.netty.channel.ChannelHandlerContext;
import server.annotation.Processor;
import server.model.Session;
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
        JsonObject msg = message.getMsg();
        int id = idGet(msg);
        String pswd = pswdGet(msg);
        if (UserService.online(session.getId()) || !UserService.loginAndRegister(id, pswd, session)) {
            SessionService.remove(ctx);
            ctx.close();
        }
    }

    private String pswdGet(JsonObject msg) {
        return msg.get("pswd").getAsString();
    }

    private int idGet(JsonObject msg) {
        return msg.get("id").getAsInt();
    }
}
