package nio.api;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: panyusheng
 * @Date: 2021/4/1
 * @Version 1.0
 */
public class Test2 {
    public static void main(String[] args) {
        testCopy();
    }

    /**
     * 复制文件
     */
    public static void testCopy() {
        try {
            File src = new File("C:\\Users\\panyusheng.GOSUNCN\\Desktop\\123.jpg");
            File dest = new File("C:\\Users\\panyusheng.GOSUNCN\\Desktop\\456.jpg");

            FileInputStream fis = new FileInputStream(src);
            FileChannel isChannel = fis.getChannel();

            FileOutputStream fos = new FileOutputStream(dest);
            FileChannel osChannel = fos.getChannel();

            // 方法一
//            isChannel.transferTo(0, isChannel.size(), osChannel);

            // 方法二
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while(true){
                // 必须先清空缓冲然后再写入数据到缓冲区
                buffer.clear();
                // 开始读取一次数据
                int flag = isChannel.read(buffer);
                if(flag == -1){
                    break;
                }
                // 已经读取了数据 ，把缓冲区的模式切换成可读模式
                buffer.flip();
                // 把数据写出到
                osChannel.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读数据
     */
    public static void testRead() {
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\panyusheng.GOSUNCN\\Desktop\\123.txt");
            FileChannel channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.read(buffer);
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String s = new String(bytes);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写数据
     */
    public static void testWrite() {
        try {
            FileOutputStream fos = new FileOutputStream("C:\\Users\\panyusheng.GOSUNCN\\Desktop\\123.txt");
            FileChannel channel = fos.getChannel();
            // 分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("hello world".getBytes());
            // 切换成写出模式
            buffer.flip();
            channel.write(buffer);
            System.out.println("写出成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
