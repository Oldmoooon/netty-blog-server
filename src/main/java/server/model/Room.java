package server.model;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class Room {
    private static AtomicInteger idGenerator = new AtomicInteger(1);

    private int id = idGenerator.getAndIncrement();

    private Set<User> users = Sets.newConcurrentHashSet();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
