package server.model;

import java.util.Objects;

/**
 * @author guyue
 * @date 2018/9/8
 */
public class User {
    private int id;

    private String name;

    private String pswd;

    private Session session;

    public User() {
    }

    public User(int id, String name, String pswd) {
        this.id = id;
        this.name = name;
        this.pswd = pswd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
