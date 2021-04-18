package netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: panyusheng
 * @Date: 2021/4/19
 * @Version 1.0
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {
        //创建一个 ByteBuf
        //说明
        //1. 创建 对象，该对象包含一个数组 arr , 是一个 byte[10]
        //2. 在 netty 的 buffer 中，不需要使用 flip 进行反转
        // 底层维护了 readerindex 和 writerIndex
        //3. 通过 readerindex 和 writerIndex 和 capacity， 将 buffer 分成三个区域
        // 0---readerindex 已经读取的区域
        // readerindex---writerIndex ， 可读的区域
        // writerIndex -- capacity, 可写的区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        System.out.println("capacity=" + buffer.capacity());//10

//        for (int i = 0; i < 5; i++) {
//            System.out.println(buffer.getByte(i));
//        }
//        System.out.println(buffer.readerIndex());
//        System.out.println("--------");
//        for (int i = 0; i < buffer.readerIndex(); i++) {
//            System.out.println(buffer.getByte(i));
//        }

        for(int i = 0; i < 5; i++) {
            System.out.println(buffer.readByte());
        }
        System.out.println("-------------");
        System.out.println(buffer.readerIndex());
        System.out.println("------------");
        for(int i = 5; i < buffer.writerIndex(); i++) {
            System.out.println(buffer.readByte());
        }
    }
}
