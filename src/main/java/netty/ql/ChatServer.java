package netty.ql;

/**
 * @Author: panyusheng
 * @Date: 2021/4/21
 * @Version 1.0
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 1) 编写一个 Netty 群聊系统，实现服务器端和客户端之间的数据简单通讯（非阻塞）
 * 2) 实现多人群聊
 * 3) 服务器端：可以监测用户上线，离线，并实现消息转发功能
 * 4) 客户端：通过 channel 可以无阻塞发送消息给其它所有用户，同时可以接受其它用户发送的消息(有服务器转发 得到)
 */
public class ChatServer {

    private int port; //监听端口

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            // 编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            // 自定义处理器
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });

            ChannelFuture cf = bootstrap.bind(this.port).addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("绑定端口成功...");
                } else {
                    System.out.println("绑定端口失败...");
                }
            }).sync();
            cf.channel().closeFuture().sync();

            System.out.println("服务器启动......");
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new ChatServer(8888).run();
    }

}
