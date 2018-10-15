package server.controller;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import server.model.HomePage;
import server.view.ThymeleafDraw;

/**
 * @author guyue
 * @date 2018/10/15
 */
public class HttpServerController extends BaseController {
    @Override
    void doResponse(FullHttpRequest request, FullHttpResponse response) {
        response.content().writeBytes(new ThymeleafDraw().draw(new HomePage()).getBytes());
    }
}
