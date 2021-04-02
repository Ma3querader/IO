package nio.sz.server;

/**
 * @Author: panyusheng
 * @Date: 2021/4/3
 * @Version 1.0
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 实现群聊
 * 服务器端：可以监测用户上线，离线，并实现消息转发功能
 * 客户端：通过 channel 可以无阻塞发送消息给其它所有客户端用户，同时可以接受其它客户端用户通过服务端转发来的消息
 */
public class Server {

    private ServerSocketChannel ssChannel;

    private Selector selector;

    public Server() {
        try {
            ssChannel = ServerSocketChannel.open();
            ssChannel.configureBlocking(false);
            ssChannel.bind(new InetSocketAddress(9999));
            selector = Selector.open();
            // 服务器通道注册到多路复用器上，指定监听accept事件
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听客户端线程进入
     */
    public void listen() {
        System.out.println("线程进入：" + Thread.currentThread().getName());
        try {
            while (selector.select() > 0) {
                // 获取所有已经就绪好的事件
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey sk = it.next();
                    // 判断这个事件具体是什么，从接收事件开始
                    if (sk.isAcceptable()) {
                        // 直接获取当前接入的客户端通道
                        SocketChannel sChannel = ssChannel.accept();
                        sChannel.configureBlocking(false);
                        System.out.println(sChannel.getRemoteAddress() + " 上线 ");
                        sChannel.register(selector, SelectionKey.OP_READ);
                    } else if (sk.isReadable()) {   // 读事件
                        readData(sk);
                    }
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务端处理读事件
     *
     * @param sk 事件
     * @return
     */
    private void readData(SelectionKey sk) {
        SocketChannel sChannel = null;
        try {
            // 通过事件反向获取客户端通道
            sChannel = (SocketChannel) sk.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int len;
            while ((len = sChannel.read(buffer)) > 0) {
                buffer.flip();
                String msg = new String(buffer.array(), 0, len);
                System.out.println("客户端：" + msg);
                buffer.clear();
                //向其它的客户端转发消息(去掉自己), 专门写一个方法来处理
                sendInfoToOtherClients(msg, sChannel);
            }
        } catch (Exception e) {
            try {
                System.out.println(sChannel.getRemoteAddress() + " 离线了..");
                e.printStackTrace();
                // 取消注册
                sk.cancel();
                sChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 转发消息给其它客户(通道)，除了自己
     *
     * @param msg         信息
     * @param selfChannel 自己通道
     * @return
     */
    private void sendInfoToOtherClients(String msg, SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息中...");
        // 遍历所有注册到selector上的SocketChannel,并排除 self
        for (SelectionKey key : selector.keys()) {
            // 通过 key 取出对应的 SocketChannel
            Channel target = key.channel();
            if (target instanceof SocketChannel && target != selfChannel) { // 排除自己
                SocketChannel dest = (SocketChannel) target;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer 的数据写入 通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }

}
