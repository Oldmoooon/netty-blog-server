package client;

import client.handler.MyClientHandler;
import com.google.gson.JsonObject;
import common.Constants;
import common.Logger;
import common.OpCode;
import common.handler.MyDecoder;
import common.handler.MyEncoder;
import common.model.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class Client {
    public static void main(String... args) {
        //如果只有一个事件循环线程池，在服务器端，boss线程和worker线程都将从该线程池中获取，客户端没有boss线程
        EventLoopGroup clientGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            Channel channel = bootstrap.group(clientGroup)
                    //这是一个无连接的channel实现
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //服务器端和客户端应该实现相反的channel pipeline， 保证消息在服务器->网络->客户端传输过程中被正确封装和解析
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new MyEncoder(),
                                    new MyDecoder(),
                                    new MyClientHandler());
                        }
                    })
                    //和服务端不同的，客户端应该连接服务器，而服务器会绑定一个端口，并监听网络请求
                    .connect(Constants.HOST, Constants.PORT)
                    .sync()
                    .channel();
            mainLoop(channel);
            //程序将在服务器关闭连接后退出
            channel.closeFuture();
        } catch (InterruptedException e) {
            Logger.server.error(e);
        } finally {
            clientGroup.shutdownGracefully();
        }
    }

    public static void mainLoop(Channel channel) {
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        String pswd = in.next();
        JsonObject loginParams = new JsonObject();
        loginParams.addProperty("id", id);
        loginParams.addProperty("pswd", pswd);
        Message login = new Message(OpCode.LOGIN, loginParams);
        try {
            ChannelFuture channelFuture = channel.writeAndFlush(login).sync();
            if (channelFuture.isSuccess()) {
                Logger.client.info("Login success.");
            } else {
                Logger.client.error(channelFuture.cause(), "login failed.");
            }
        } catch (InterruptedException e) {
            Logger.client.error(e, "login interrupted.");
        }
        while (in.hasNextLine()) {
            String enter = in.nextLine();
            if ("".equals(enter)) {
                continue;
            }
            if ("exit".equals(enter)) {
                break;
            }
            try {
                ChannelFuture future = channel
                        .writeAndFlush(new Message(enter))
                        .sync();
                if (future.isSuccess()) {
                    Logger.client.info("send message success.");
                } else {
                    Logger.client.error("send message failed: {}", future.cause());
                }
            } catch (InterruptedException e) {
                Logger.client.error("send message failed: {}", e);
            }
        }
    }
}
