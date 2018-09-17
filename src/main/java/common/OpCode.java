package common;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author guyue
 * @date 2018/9/17
 */
public enum OpCode {

    // login
    LOGIN(99),
    // normal chat message
    DEFAULT_MESSAGE(100),
    ;
    private int code;
    private static Map<Integer, OpCode> data = Maps.newConcurrentMap();

    OpCode(int code) {
        this.code = code;
    }

    static {
        for (OpCode per : values()) {
            data.put(per.getCode(), per);
        }
    }

    public static OpCode valueOf(int code) {
        return data.get(code);
    }

    public int getCode() {
        return code;
    }
}
