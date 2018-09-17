package server.processor;

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
        String msg = message.getMsg();
        int id = idGet(msg);
        String pswd = pswdGet(msg);
        if (UserService.online(session.getId()) || !UserService.loginAndRegister(id, pswd, session)) {
            SessionService.remove(ctx);
            ctx.close();
        }
    }

    private String pswdGet(String msg) {
        return null;
    }

    private int idGet(String msg) {
        return 0;
    }
}
