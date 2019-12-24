package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class XmppClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 5222);
 
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
 
			OutputStream out = socket.getOutputStream();
 
			out.write("<?xml version=\"1.0\"?><stream:stream to=\"stbxmpps.voo.be\" xml:lang=\"en\" version=\"1.0\" xmlns=\"jabber:client\" xmlns:stream=\"http://etherx.jabber.org/streams\">".getBytes());
			
			out.flush();
			String line = ""; 
			
			while ( ( line = in.readLine()) != null && !line.isEmpty()  ){
				System.out.println(line);
			}
			
			out.write("<auth mechanism=\"EXTERNAL\" xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">MTg0Nzk3NTk4NEBzdGJ4bXBwcy52b28uYmUvc3Ri</auth>".getBytes());
			out.flush();
 
			
			while ( ( line = in.readLine()) != null && !line.isEmpty()  ){
				System.out.println(line);
			}
			// out.write("<?xml version=\"1.0\"?><stream:stream to=\"stbxmpps.voo.be\" xml:lang=\"en\" version=\"1.0\" xmlns=\"jabber:client\" xmlns:stream=\"http://etherx.jabber.org/streams\">".getBytes());
			// System.out.println("flush");
			// out.flush();
			
			// in.close();
			// out.close();
			// socket.close();
			Thread.sleep(10000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}