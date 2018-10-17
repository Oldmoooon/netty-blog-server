package server.controller;

import base.annoation.Processor;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import server.model.HomePage;
import server.view.ThymeleafDraw;

/**
 * @author guyue
 * @date 2018/10/16
 */
@Processor(path = {"/", "/index", "/index.html"})
public class HomeProcessor implements IProcessor  {
    @Override
    public void process(FullHttpRequest request, FullHttpResponse response) {
        HomePage page = new HomePage();
        response.content().writeBytes(new ThymeleafDraw().draw(page).getBytes());
    }
}
