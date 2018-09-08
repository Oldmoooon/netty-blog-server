package server.service;

import com.google.common.collect.Maps;
import common.Constants;
import common.Logger;
import common.model.Message;
import server.model.Room;
import server.model.User;

import java.util.Map;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class RoomService {
    public final static String QUIT_MESSAGE = "bye";
    private static Map<Integer, Room> rooms = Maps.newConcurrentMap();

    public static void comeIn(int id) {
        Room room = rooms.get(id);
        if (room != null) {
            comeIn(id, room);
        } else {
            checkAndGetRoom(id);
        }
    }

    public static void speakIn(int id, Message msg) {
        if (checkLeave(id, msg)) {
            return;
        }
        Room room = checkAndGetRoom(id);
        speakIn(room, msg, id);
    }

    private static void speakIn(Room room, Message msg, int id) {
        for (User user : room.getUsers()) {
            if (user.getId() != id) {
                speakTo(user.getId(), msg);
            }
        }
    }

    private static void comeIn(int id, Room room) {
        User user = UserService.get(id);
        if (user == null) {
            Logger.server.error("user of id_{} is null.", id);
            return;
        }
        room.getUsers().add(user);
        rooms.put(id, room);
        speakIn(room, new Message("User_" + id + " come in the room."), Constants.ADMIN_USER_ID);
    }

    private static boolean isLeaveMessage(Message message) {
        return QUIT_MESSAGE.equals(message.getMsg());
    }

    private static boolean checkLeave(int id, Message msg) {
        if (!isLeaveMessage(msg)) {
            return false;
        }
        Room room = rooms.get(id);
        if (room != null) {
            room.getUsers().remove(UserService.get(id));
            speakIn(room, new Message("User_" + id + " said bye."), Constants.ADMIN_USER_ID);
        }
        return true;
    }

    private static Room checkAndGetRoom(int id) {
        Room room = rooms.get(id);
        if (room != null) {
            return room;
        }
        for (Room per : rooms.values()) {
            if (sizeOf(per) < Constants.ROOM_CAPACITY) {
                comeIn(id, per);
                return per;
            }
        }
        room = new Room();
        comeIn(id, room);
        return room;
    }

    private static void speakTo(int id, Message message) {
        SessionService.sendTo(id, message);
    }

    private static int sizeOf(Room room) {
        return room.getUsers().size();
    }
}
