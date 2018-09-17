package common.model;

import common.OpCode;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class Message {
    private int code;
    private String msg;

    public Message() {
    }

    public Message(String msg) {
        this(OpCode.DEFAULT_MESSAGE, msg);
    }

    public Message(OpCode opCode, String msg) {
        this(opCode.getCode(), msg);
    }

    public Message(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return String.format("%d|%s", code, msg);
    }
}
