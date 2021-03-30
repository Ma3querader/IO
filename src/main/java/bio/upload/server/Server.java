package bio.upload.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: panyusheng
 * @Date: 2021/3/30
 * @Version 1.0
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            while (true) {
                Socket socket = ss.accept();
                // 交给一个独立的线程来处理与这个客户端的文件通信需求。
                new ServerReaderThread(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
