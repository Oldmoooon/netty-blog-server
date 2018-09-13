package server.service;

import server.model.TestModel;

/**
 * @author guyue
 * @date 2018/9/13
 */
public class TestService {
    public static int add(int a, TestModel b) {
        return a + b.getValue();
    }
}
