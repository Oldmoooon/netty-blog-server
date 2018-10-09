package client.aop.test;

import common.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author guyue
 * @date 2018/10/8
 */
public class TestHandler implements InvocationHandler {
    private Object target;

    TestHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger.test.info("method {} is invoked.", method.getName());
        return method.invoke(target);
    }
}
