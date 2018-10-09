package client.aop.test;

import common.Logger;

import java.lang.reflect.Proxy;

/**
 * @author guyue
 * @date 2018/10/8
 */
public class TestMain {
    public static void main(String... args) {
        TestImpl target = new TestImpl();
        TestHandler testHandler = new TestHandler(target);
        Class[] interfaces = { ITest.class };
        ClassLoader classLoader = TestMain.class.getClassLoader();
        Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, testHandler);
        ITest test = (ITest) proxyInstance;
        int res = test.test();
        Logger.test.info("{}", res);
    }
}
