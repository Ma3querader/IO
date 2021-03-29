package bio.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: panyusheng
 * @Date: 2021/3/29
 * @Version 1.0
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        System.out.println("服务端启动....");
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            // 在这里暂停等待接收客户端的连接,得到一个端到端的Socket管道
            Socket socket = serverSocket.accept();
            new ServerReadThread(socket).start();
            System.out.println(socket.getRemoteSocketAddress() + "上线了...");
        }
    }
}
