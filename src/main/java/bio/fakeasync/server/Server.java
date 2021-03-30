package bio.fakeasync.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: panyusheng
 * @Date: 2021/3/30
 * @Version 1.0
 */
public class Server {
    public static void main(String[] args) {
        System.out.println("-------服务器启动----------");
        try {
            HandlerSocketThreadPool pool = new HandlerSocketThreadPool(3, 10);
            ServerSocket ss = new ServerSocket(8888);
            while (true) {
                Socket socket = ss.accept();
                // 把socket封装成任务对象
                Runnable task = new ServerTask(socket);
                // 把任务对象放进线程池
                pool.execute(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
