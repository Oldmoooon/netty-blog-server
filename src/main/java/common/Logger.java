package common;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class Logger {
    public static Log server = LogFactory.get(Server.class);

    private static class Server {}
}
