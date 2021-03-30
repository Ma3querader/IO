package bio.fakeasync.cilent;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: panyusheng
 * @Date: 2021/3/29
 * @Version 1.0
 */



public class ClientDemo {
    public static void main(String[] args) throws IOException {
        System.out.println("客户端启动...");
        // 创建一个socket通信管道，请求与服务器8888端口连接
        Socket socket = new Socket("127.0.0.1", 8888);
        // 从Socket通信管道中得到一个字节输出流
        OutputStream os = socket.getOutputStream();
        // 把字节流改装成自己需要的流进行数据的发送
        PrintStream ps = new PrintStream(os);
        // 开始发送消息
        Scanner sc = new Scanner(System.in);
        while (true) {
            String msg = sc.nextLine();
            ps.println(msg);
            ps.flush();
        }
    }
}
