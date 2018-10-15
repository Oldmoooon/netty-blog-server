package server;

import base.Constants;
import base.Logger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import server.controller.HttpServerController;

/**
 * @author guyue
 * @date 2018/10/15
 */
public class MainServer {
    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(Constants.BOSS_THREAD_COUNT);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(Constants.WORKER_THREAD_COUNT);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new HttpRequestDecoder(),
                                    new HttpResponseEncoder(),
                                    new HttpObjectAggregator(512 * 1024),
                                    new HttpServerController()
                                    );
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, Constants.SO_KEEP_ALIVE)
                    .bind(Constants.PORT)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            Logger.system.error(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
