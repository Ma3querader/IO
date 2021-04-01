package nio.kc.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author: panyusheng
 * @Date: 2021/4/2
 * @Version 1.0
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocketChannel ssChannel = ServerSocketChannel.open();
            ssChannel.configureBlocking(false);
            ssChannel.bind(new InetSocketAddress(8888));
            // 获取多路复用器
            Selector selector = Selector.open();
            // 通道注册到复用器并设置监听事件
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.select() > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator(); // 选择键集
                while (it.hasNext()) {
                    SelectionKey sk = it.next();
                    if (sk.isAcceptable()) {    // 如果是accept事件
                        SocketChannel sChannel = ssChannel.accept();
                        sChannel.configureBlocking(false);
                        sChannel.register(selector, SelectionKey.OP_READ); // 把客户端注册到多路复用器，监听客户端的可读事件
                    } else if (sk.isReadable()) {   // 读事件
                        SocketChannel sChannel = (SocketChannel) sk.channel();  // 返回此键对应的通道
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int len = 0;
                        while ((len = sChannel.read(buf)) > 0) {
                            buf.flip();
                            System.out.println(new String(buf.array(), 0, len));
                            buf.clear();
                        }
                    }
                }
                it.remove();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
