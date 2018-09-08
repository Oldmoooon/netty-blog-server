package common.model;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class Message {
    private String msg;

    public Message() {
    }

    public Message(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
