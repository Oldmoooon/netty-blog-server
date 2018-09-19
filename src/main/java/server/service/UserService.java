package server.service;

import com.google.common.collect.Maps;
import common.Constants;
import common.Logger;
import server.model.Session;
import server.model.User;

import java.util.Map;

/**
 * @author guyue
 * @date 2018/9/8
 */
public class UserService {
    private static Map<Integer, User> users = Maps.newConcurrentMap();

    public static User get(int id) {
        return users.get(id);
    }

    public static boolean loginAndRegister(int id, String pswd, Session session) {
        if (login(id, pswd, session)) {
            Logger.server.info("user {} login success.", id);
        } else if (register(id) && login(id, Constants.INIT_PSWD, session)) {
            Logger.server.info("user {} register and login success.", id);
        } else {
            Logger.server.info("user {} try to login and register failed.", id);
            return false;
        }
        return true;
    }

    private static boolean login(int id, String pswd, Session session) {
        if (correct(id, pswd)) {
            if (session.getId() != id) {
                SessionService.remove(session.getId());
                session.setId(id);
                if (!SessionService.add(session)) {
                    Logger.server.error("user_{} already login.", id);
                    session.getCtx().close();
                }
            }
            get(id).setSession(session);
            return true;
        }
        return false;
    }

    public static void logout(int id) {
        if (contains(id)) {
            get(id).setSession(null);
        } else {
            Logger.server.error("none user {} try logout.", id);
        }
    }

    private static boolean register(int id) {
        if (contains(id)) {
            return false;
        }
        User user = new User(id, Constants.INIT_NAME, Constants.INIT_PSWD);
        users.put(user.getId(), user);
        return true;
    }

    public static boolean online(int id) {
        return users.containsKey(id) && users.get(id).getSession() != null;
    }

    private static boolean contains(int id) {
        return users.containsKey(id);
    }

    private static boolean correct(int id, String pswd) {
        if (get(id) == null) {
            return false;
        }
        return pswd.equals(get(id).getPswd());
    }
}
