package netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Author: panyusheng
 * @Date: 2021/4/14
 * @Version 1.0
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道就绪就会触发
     *
     * @param ctx
     * @return
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello...", CharsetUtil.UTF_8));
        Thread.sleep(20000);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello...", CharsetUtil.UTF_8));
        Thread.sleep(20000);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello...", CharsetUtil.UTF_8));
    }

    /**
     * 通道有读取事件时触发
     *
     * @param ctx
     * @param msg
     * @return
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
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
