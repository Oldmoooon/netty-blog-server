package server.controller;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author guyue
 * @date 2018/10/15
 */
public class HttpServerController extends BaseController {
    @Override
    void doResponse(FullHttpRequest request, FullHttpResponse response) {
        Router.route(request, response);
    }
}
