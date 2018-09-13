package common;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class Logger {
    public static Log server = LogFactory.get(Server.class);

    public static Log client = LogFactory.get(Client.class);

    public static Log test = LogFactory.get(Test.class);

    private static class Server {
    }

    private static class Client {
    }

    private static class Test {
    }
}
