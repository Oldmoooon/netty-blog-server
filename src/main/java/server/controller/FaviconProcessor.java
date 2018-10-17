package server.controller;

import base.Constants;
import base.Logger;
import base.Utils;
import base.annoation.Processor;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * @author guyue
 * @date 2018/10/16
 */
@Processor(path = {"/favicon.ico"})
public class FaviconProcessor implements IProcessor{
    @Override
    public void process(FullHttpRequest request, FullHttpResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(Utils.faviconPath());
            byte[] bytes = fileInputStream.readAllBytes();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream output = new GZIPOutputStream(byteArrayOutputStream);
            output.write(bytes);
            output.finish();
            byte[] res = byteArrayOutputStream.toByteArray();
            output.close();
            byteArrayOutputStream.close();
            response.content().writeBytes(res);
            response.headers().add(HttpHeaderNames.CONTENT_ENCODING, HttpHeaderValues.GZIP);
            response.headers().add(HttpHeaderNames.CONTENT_TYPE, Constants.HTML_HEADER_VALUE_ICON);
        } catch (IOException e) {
            Logger.logic.error("solve file {} error {}", Utils.faviconPath(), e);
        }
    }
}
