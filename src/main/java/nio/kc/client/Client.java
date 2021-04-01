package nio.kc.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * @Author: panyusheng
 * @Date: 2021/4/2
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) {
        try {
            SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
            sChannel.configureBlocking(false);
            ByteBuffer buf = ByteBuffer.allocate(1024);
            Scanner scan = new Scanner(System.in);
            while(scan.hasNext()){
                String str = scan.nextLine();
                buf.put((new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis())
                        + "\n" + str).getBytes());
                buf.flip();
                sChannel.write(buf);
                buf.clear();
            }
            sChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
