package nio.api;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @Author: panyusheng
 * @Date: 2021/3/31
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-------------------------");

        String name = "pys";
        buffer.put(name.getBytes());
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-------------------------");

        buffer.flip();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-------------------------");

        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        String s = new String(bytes);
        System.out.println(s);
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-------------------------");

        buffer.rewind();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println("-------------------------");

        buffer.clear();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        System.out.println((char) buffer.get());

    }
}
