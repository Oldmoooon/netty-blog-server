package server;

import common.Constants;
import common.Logger;
import common.handler.MyDecoder;
import common.handler.MyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import server.handler.MyServerHandler;
import server.handler.UnderlyingNetHandler;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class Server {
    public static void start() {
        /*
          这是两个多线程的事件循环，boss接受传入的连接，并把它注册到worker，worker处理它们。
          就像有客户来办理业务，老板接待这个客户，并把这个客户转交给具体负责的工作人员们，在网络请求处理中，
          boss收到请求，并把请求交给worker来处理，read/write将在worker线程中进行，保持boss线程不被阻塞。
          boss、worker的线程池大小和映射关系取决于具体的实现，也可以通过构造函数指定。
        */
        //boss线程池用于处理客户端的连接请求，当同时监听多个端口、并发量较大时单个线程有可能存在性能问题
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //worker线程池用于处理具体的read/write操作，并且和后续的channel绑定
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    //指定一个channel用于处理传入的连接
                    .channel(NioServerSocketChannel.class)
                    //可以指定一组channel，以管道的方式处理传入的请求，类似于mina的filter
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new MyEncoder(),
                                    new MyDecoder(),
                                    new UnderlyingNetHandler(),
                                    new MyServerHandler());
                        }
                    })
                    //此处的参数在bind()或connect()时被设置到ServerChannel，该ServerChannel在boss线程工作
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //此处的参数在一个连接被boss线程accept时设置到ServerChannel的子Channel，该Channel在worker线程工作
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //绑定端口号，返回一个ChannelFuture实例
                    /*
                      jdk中的Future是异步计算的结果，该方法会阻塞直到异步计算完成；
                      netty中的Future继承自jdk中的Future，增加了一些操作；
                      netty中的ChannelFuture继承自netty中的Future，表示channel中I/O操作的结果，在netty中所有的I/O操作都是异步的。
                     */
                    .bind(Constants.PORT)
                    //等待动作完成，并在失败后重新抛出异常，如果动作没有完成则会阻塞
                    .sync()
                    //返回值是一个channel，I/O操作将在这个channel的实例中发生
                    .channel()
                    //当这个channel被关闭时返回结果，这个方法将返回一样的返回值，因为该channel必然已经被关闭
                    .closeFuture()
                    // **如果没有下面这行，由于以上操作都不会对主线程造成阻塞（绑定端口的动作也会完成），所以本程序会
                    // 在开始执行后立即结束，如果加上这一行，因为我们没有在具体的ChannelHandler中关闭channel，所以
                    // 程序会一直阻塞。**
                    .sync();
        } catch (InterruptedException e) {
            Logger.server.error(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String... args) {
        start();
    }
}
