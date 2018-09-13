package server.service;

import common.Logger;
import common.model.Message;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.Iterator;
import java.util.List;

@PrepareForTest(RoomService.class)
@PowerMockIgnore({ "org.apache.logging.*", "com.sun.org.apache.xerces.*" })
public class RoomServiceTest extends PowerMockTestCase {
    @DataProvider(name = "idProvider")
    Iterator<Object> idProvide() {
        List<Object> list = Lists.newLinkedList();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        return list.iterator();
    }

    @Test(dataProvider = "idProvider")
    public void testComeIn(int id) {
        PowerMockito.spy(RoomService.class);
        String speakIn = "speakIn";
        try {
            PowerMockito.doNothing().when(RoomService.class, speakIn, id, new Message());
        } catch (Exception e) {
            Logger.test.error("create mock object error.", e);
        }

        RoomService.comeIn(id);

        try {
            PowerMockito.verifyPrivate(RoomService.class, Mockito.times(0))
                    .invoke(speakIn, id, new Message());
        } catch (Exception e) {
            Logger.test.error("verify method result error.", e);
        }
    }

    @Test
    public void testSpeakIn() {
    }
}