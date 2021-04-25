package netty.heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.test.NettyClientHandler;

/**
 * @Author: panyusheng
 * @Date: 2021/4/25
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) {
        // 只要一个工作组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("------- 客户端准备好 -------");
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 8888).sync();
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
