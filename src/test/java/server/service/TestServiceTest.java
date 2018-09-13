package server.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import server.model.TestModel;

import java.util.Collection;
import java.util.LinkedList;

@RunWith(Parameterized.class)
@PrepareForTest(TestModel.class)
@PowerMockIgnore({ "org.apache.logging.*", "com.sun.org.apache.xerces.*" })
public class TestServiceTest {

    private int a;
    private TestModel b;

    public TestServiceTest(int a, TestModel b) {
        this.a = a;
        this.b = b;
    }

    @Parameters
    public static Collection<Object[]> provide() {
        LinkedList<Object[]> list = Lists.newLinkedList();
        list.add(new Object[] { 1, new TestModel(2) });
        list.add(new Object[] { 3, new TestModel(4) });
        list.add(new Object[] { 5, new TestModel(6) });
        return list;
    }

    @Test
    public void add() {
        //setup
        TestModel mock = PowerMockito.spy(b);
        try {
            PowerMockito.when(mock, "getValue").thenReturn(3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //exercise
        int ret = TestService.add(a, mock);
        System.out.println("" + a + " + " + b.getValue() + " = " + ret);

        //verify
        assert ret == a + 3;
        try {
            PowerMockito.verifyPrivate(mock, Mockito.times(1)).invoke("getValue");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //teardown
    }
}