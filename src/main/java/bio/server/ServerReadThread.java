package bio.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @Author: panyusheng
 * @Date: 2021/3/29
 * @Version 1.0
 */
public class ServerReadThread extends Thread{

    private Socket socket;

    public ServerReadThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 从Socket管道中得到一个字节输入流
            InputStream is = this.socket.getInputStream();
            // 把字节输入流包装成自己需要的流进行数据的读取
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // 读取数据
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("服务端收到："+ socket.getRemoteSocketAddress() + ": " + line);
            }
        } catch (Exception e) {
            System.out.println(socket.getRemoteSocketAddress() + "下线了..");
        }

    }
}
