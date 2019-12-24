package mesh.xmpp.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import mesh.xmpp.net.XmppHandler;

/**
receive: <?xml version="1.0"?><stream:stream to="stbxmpps.voo.be" xml:lang="en" version="1.0" xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams">
send: <stream:stream xmlns:stream="http://etherx.jabber.org/streams" xmlns="jabber:client" version="1.0" id="4f5c64eb5cbe1f3ad9fcc59b8d5c1d7b" from="stbxmpps.voo.be">
send: <stream:features xmlns="http://etherx.jabber.org/streams" xmlns:stream="http://etherx.jabber.org/streams"><starttls xmlns="urn:ietf:params:xml:ns:xmpp-tls"><required/></starttls><mechanisms xmlns="urn:ietf:params:xml:ns:xmpp-sasl"><mechanism>EXTERNAL</mechanism><mechanism>ANONYMOUS</mechanism></mechanisms></stream:features>
receive: <starttls xmlns="urn:ietf:params:xml:ns:xmpp-tls"/>
send: <proceed xmlns="urn:ietf:params:xml:ns:xmpp-tls"/>

receive: <?xml version="1.0"?><stream:stream to="stbxmpps.voo.be" xml:lang="en" version="1.0" xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams">
send: <stream:stream xmlns:stream="http://etherx.jabber.org/streams" xmlns="jabber:client" version="1.0" id="4f5c64eb5cbe1f3ad9fcc59b8d5c1d7b" from="stbxmpps.voo.be">
send: <stream:features xmlns="http://etherx.jabber.org/streams" xmlns:stream="http://etherx.jabber.org/streams"><mechanisms xmlns="urn:ietf:params:xml:ns:xmpp-sasl"><mechanism>EXTERNAL</mechanism><mechanism>ANONYMOUS</mechanism></mechanisms></stream:features>
receive: <auth mechanism="EXTERNAL" xmlns="urn:ietf:params:xml:ns:xmpp-sasl">MTg0Nzk3NTk4NEBzdGJ4bXBwcy52b28uYmUvc3Ri</auth>
send: <success xmlns="urn:ietf:params:xml:ns:xmpp-sasl"/>
 * 
 * */
public class XmppBootstrap {
	private int port = 5222 ;
	
	public XmppBootstrap(){
		
	}
	
	
	public void run(){
		EventLoopGroup parentGroup = new NioEventLoopGroup(1);
		EventLoopGroup childGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(parentGroup, childGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 100)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					ChannelPipeline cp = sc.pipeline();
					cp.addLast(new XmppHandler());
				}
			});

			ChannelFuture cf = sb.bind(port).sync();

			cf.channel().closeFuture().sync();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}
	}
}
