package netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Author: panyusheng
 * @Date: 2021/4/13
 * @Version 1.0
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端发送过来的数据
     *
     * @param ctx 上下文
     * @param msg 客户端发送过来的数据
     * @return
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println(buf.toString(CharsetUtil.UTF_8));

        // 一个耗时的任务 -> 异步执行 -> 把这个任务提交到这个channel 对应的 nioEventLoop 中的 taskQueue 中
        /**
         * 三种方法
         * 1、用户程序自定义的普通任务
         * 2、用户自定义定时任务
         * 3、非当前Reactor线程调用Channel的方法
         */
        // 1 自定义的普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("这是耗时的任务1", CharsetUtil.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 2 自定义定时任务 -> 该任务要提交到 scheduledTaskQueue
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("这是耗时的任务3", CharsetUtil.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);
    }

    /**
     * 数据读取完毕
     *
     * @param ctx
     * @return
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("回应你...", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     * @return
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
