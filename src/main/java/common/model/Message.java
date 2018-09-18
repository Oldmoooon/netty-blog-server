package common.model;

import com.google.gson.JsonObject;
import common.Constants;
import common.OpCode;

/**
 * @author guyue
 * @date 2018/9/6
 */
public class Message {
    private int code;
    private JsonObject msg;

    public Message() {
    }

    public Message(String msg) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.MSG_KEY, msg);
        this.code = OpCode.DEFAULT_MESSAGE.getCode();
        this.msg = jsonObject;
    }

    public Message(JsonObject msg) {
        this(OpCode.DEFAULT_MESSAGE, msg);
    }

    public Message(OpCode opCode, JsonObject msg) {
        this(opCode.getCode(), msg);
    }

    public Message(int code, JsonObject msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JsonObject getMsg() {
        return msg;
    }

    public void setMsg(JsonObject msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return String.format("%d|%s", code, msg);
    }
}
