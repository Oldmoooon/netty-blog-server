package server;

import com.google.common.collect.Maps;
import common.OpCode;
import server.processor.ChatProcessor;
import server.processor.IProcessor;
import server.processor.LoginProcessor;

import java.util.Map;

/**
 * @author guyue
 * @date 2018/9/17
 */
public enum ProcessorEnum {

    /**
     * process chat request
     */
    LOGIN(OpCode.LOGIN, new LoginProcessor()),
    CHAT(OpCode.DEFAULT_MESSAGE, new ChatProcessor()),
    ;

    private static Map<Integer, IProcessor> data = Maps.newConcurrentMap();
    private OpCode opCode;
    private IProcessor processor;

    ProcessorEnum(OpCode opCode, IProcessor processor) {
        this.opCode = opCode;
        this.processor = processor;
    }

    static {
        for (ProcessorEnum per : values()) {
            data.put(per.opCode.getCode(), per.processor);
        }
    }

    public static IProcessor processorGet(OpCode opCode) {
        return data.get(opCode.getCode());
    }
}
