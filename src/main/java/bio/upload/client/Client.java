package bio.upload.client;

import java.io.*;
import java.net.Socket;

/**
 * @Author: panyusheng
 * @Date: 2021/3/30
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) {
        try(InputStream is = new FileInputStream("C:\\Users\\panyusheng.GOSUNCN\\Desktop\\22.jpg")) {
            Socket socket = new Socket("127.0.0.1" , 8888);
            // 字节输出流包装成数据输出流
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            // 先发送上传文件的后缀给服务端
            dos.writeUTF(".jpg");
            // 把文件数据发送给服务端
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                dos.write(bytes, 0, len);
            }
            dos.flush();
            // bio是同步阻塞模型，客户端要发送结束标志，不然服务端会一直等 --> 阻塞
            socket.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
