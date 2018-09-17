package server.processor;

import common.model.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author guyue
 * @date 2018/9/17
 */
@FunctionalInterface
public interface IProcessor {
    /**
     * process a request
     *
     * @param ctx channel handler context from peer
     * @param message message from peer
     */
    void process(ChannelHandlerContext ctx, Message message);
}
