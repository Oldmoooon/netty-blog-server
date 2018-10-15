package base;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class Logger {
    public static Log system = LogFactory.get(System.class);
    public static Log logic = LogFactory.get(Logic.class);

    private class System {
    }

    private class Logic {
    }
}
