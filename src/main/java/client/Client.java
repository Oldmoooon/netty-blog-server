package client;

import common.Constants;
import common.Logger;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;

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
            bootstrap.group(clientGroup)
                    //这是一个无连接的channel实现
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //服务器端和客户端应该实现相反的channel pipeline， 保证消息在服务器->网络->客户端传输过程中被正确封装和解析
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ChannelHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                    try {
                                        ByteBuf buffer = (ByteBuf) msg;
                                        long currentTimeMills = buffer.readLong();
                                        Logger.server.debug("Time: {}", new Date(currentTimeMills));
                                    } finally {
                                        ReferenceCountUtil.release(msg);
                                    }
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    Logger.server.error(cause);
                                    ctx.close();
                                }
                            });
                        }
                    })
                    //和服务端不同的，客户端应该连接服务器，而服务器会绑定一个端口，并监听网络请求
                    .connect(Constants.HOST, Constants.PORT)
                    .sync()
                    .channel()
                    //程序将在服务器关闭连接后退出
                    .closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            Logger.server.error(e);
        } finally {
            clientGroup.shutdownGracefully();
        }
    }
}