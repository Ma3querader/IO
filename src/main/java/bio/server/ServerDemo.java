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
        // 在这里暂停等待接收客户端的连接,得到一个端到端的Socket管道
        Socket socket = serverSocket.accept();
        // 从Socket管道中得到一个字节输入流
        InputStream is = socket.getInputStream();
        // 把字节输入流包装成自己需要的流进行数据的读取
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        // 读取数据
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("服务端收到："+line);
        }


    }
}
