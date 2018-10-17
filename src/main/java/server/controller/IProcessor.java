package server.controller;

import base.Logger;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author guyue
 * @date 2018/10/16
 */
public interface IProcessor {
    /**
     * process a request by route
     * @param request full http request
     * @param response full http response
     */
    default void process(FullHttpRequest request, FullHttpResponse response) {
        Logger.system.info("there isn't processor for {}", request.uri());
    }
}
