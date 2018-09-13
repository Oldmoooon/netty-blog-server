package server.service;

/**
 * @author guyue
 * @date 2018/9/13
 */
public class TestService {
    public static int add(int a, int b) {
        return a + value(a);
    }

    private static int value(int a) {
        return a;
    }
}
