package nio.sz.client;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Author: panyusheng
 * @Date: 2021/4/3
 * @Version 1.0
 */
public class Client {

    private Selector selector;

    private SocketChannel sChannel;

    public Client() throws IOException {
        selector = Selector.open();
        sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
        sChannel.configureBlocking(false);
        sChannel.register(selector, SelectionKey.OP_READ);
    }

    //读取从服务器端回复的消息
    public void readInfo() {
        try {
            int readChannels = selector.select();
            if (readChannels > 0) {//有可以用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int len;
                        while ((len = sc.read(buffer)) > 0) {
                            buffer.flip();
                            String msg = new String(buffer.array(), 0, len);
                            System.out.println(sc.getRemoteAddress() + ": " + msg);
                        }
                    }
                    iterator.remove(); //删除当前的selectionKey, 防止重复操作
                }
            } else {
                //System.out.println("没有可以用的通道...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendInfo(String s) throws IOException {
        s = this.sChannel.getRemoteAddress() + " ：" + s;
        try {
            sChannel.write(ByteBuffer.wrap(s.getBytes()));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client chatClient = new Client();
        new Thread(() -> chatClient.readInfo()).start();

        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }


}
