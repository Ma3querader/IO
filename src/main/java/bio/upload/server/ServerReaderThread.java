package bio.upload.server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * @Author: panyusheng
 * @Date: 2021/3/30
 * @Version 1.0
 */
public class ServerReaderThread extends Thread {

    private Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 获取输入流并包装成数据流
            InputStream is = this.socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            // 读取客户端发送过来的文件类型
            String suffix = dis.readUTF();
            // 服务端输出管道
            OutputStream os = new FileOutputStream("C:\\Users\\panyusheng.GOSUNCN\\Desktop\\" + UUID.randomUUID().toString() + suffix);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = dis.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            System.out.println("服务端接收文件保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
