package server.service;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.collections.Lists;
import server.model.TestModel;

import java.util.Iterator;
import java.util.List;

@PrepareForTest(TestService.class)
public class TestServiceTest {

    @DataProvider(name = "provider")
    Iterator<Object[]> provider() {
        List<Object[]> list = Lists.newLinkedList();
        list.add(new Object[] { 1, new TestModel(2) });
        list.add(new Object[] { 3, new TestModel(4) });
        list.add(new Object[] { 5, new TestModel(6) });
        return list.iterator();
    }

    @Test(dataProvider = "provider")
    public void testAdd(int a, TestModel b) {
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