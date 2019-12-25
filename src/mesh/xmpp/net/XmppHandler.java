package mesh.xmpp.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import mesh.xmpp.packet.XmppXmlParser;

public class XmppHandler extends ChannelInboundHandlerAdapter {
	// private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	private XmppClientController xmppClientController = null ; 
	private static int activeCount = 0 ;
	private static int registerCount = 0 ; 
	private static int unregisterCount = 0 ;
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // channels.add(ctx.channel());
		super.channelActive(ctx);
        xmppClientController = new XmppClientController(ctx);
        // System.out.println("channelActive" + activeCount++);
    }
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelUnregistered(ctx);
		// System.out.println("channelUnregistered" + unregisterCount++);
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
		// System.out.println("channelRegistered" + registerCount++);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf inBuffer = (ByteBuf) msg;
        String message = inBuffer.toString(CharsetUtil.UTF_8);
        inBuffer.clear();
        // System.out.println("receive: " + message);

        xmppClientController.onMessage(message);
        // ctx.write(Unpooled.copiedBuffer("Hello " + received, CharsetUtil.UTF_8));
    }

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush(); 
	};
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		xmppClientController.onClose();
		if ( ctx != null && ctx.channel() != null ) {
			ctx.channel().close();
		}
		ctx.close();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace(); 
		// System.out.println("[ERROR MESSAGE]" + cause.getMessage());
		if ( xmppClientController != null ) {
			xmppClientController.onClose();
		}
		if ( ctx != null && ctx.channel() != null ) {
			ctx.channel().close();
		}
		ctx.close();
	}
}