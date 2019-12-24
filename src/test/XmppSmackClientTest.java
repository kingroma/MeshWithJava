package test;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.IQ.Type;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public class XmppSmackClientTest {
	public static SSLContext sslContext = null;
	private static String stbId = "987654321";
	
	private static String ip = "127.0.0.1";
	
	// LAB0
	// private static String ip = "185.3.160.117";
	// PRODUCT VIP
	// private static String ip = "185.3.160.202";
	// PRODUCT 1 
	// private static String ip = "10.11.6.49";
	private static int port = 5222 ;
	private static String messageId = "ABCDE-";
	private static String ssl = "TLS";
	private static String username = "username";
	private static String password = "password";
	private static int loop = 1 ; 
	private static int offset = 1000 ; 
	
	public static void main(String[] args) {
		if ( args != null && args.length > 0 && args.length%2 == 0) {
			if (args.length%2 != 0 ) {
				// System.exit(1);
				System.out.println("parameter error");
			} else {
				for ( int i = 0 ; i < args.length ; i = i + 2) {
					String arg1 = args[i];
					String arg2 = args[i+1];
					
					switch(arg1) {
					case "--ip" :
						// System.out.println("ip : " + arg2);
						ip = arg2 ;
						break;
					case "--messageId":
						// System.out.println("messageId : " + arg2);
						messageId = arg2 ;
						break;
					case "--stbId":
						// System.out.println("stbId : " + arg2);
						stbId = arg2 ; 
						break;
					case "--ssl":
						ssl = arg2 ;
						break;
					case "--username":
						username = arg2 ;
						break;
					case "--password":
						password = arg2 ; 
						break;
					case "--loop" : 
					default : 
						int temp = 1 ; 
						try {
							temp = Integer.parseInt(arg2);
						} catch (Exception e) {
							
						}
						loop = temp ; 
						break;
					}
				}
			}
		}else {
			System.out.println("no param");
			// System.exit(1);
		}
		
		System.out.println("IP : " + ip);
		System.out.println("PORT : " + port);
		System.out.println("STB_ID : " + stbId);
		System.out.println("MESSAGE_ID : " + messageId);
		System.out.println("SSL :" + ssl);
		System.out.println("Username : " + username);
		System.out.println("Password : " + password);
		System.out.println("Loooooop : " + loop);
		
		System.out.println();
		if ( ssl != null && !ssl.isEmpty() )
			setSSL();
		for ( int i = 0 ; i < loop ; i ++ ) {
			System.out.println("==Looop Number : " + i + "=========================");
			sendIq();
		}
		

	}
	
	public static void sendIq() {
		try {
			ConnectionConfiguration gconfig = 
					new ConnectionConfiguration(
							// "185.3.160.117",
							ip ,
							port,
							"avsystem.com/prod/${?hostname}@avsystem.com/prod"
							);
			
			gconfig.setReconnectionAllowed(true);
			gconfig.setDebuggerEnabled(false);
			gconfig.setReconnectionAllowed(true);
			gconfig.setRosterLoadedAtLogin(false);
			gconfig.setSendPresence(true);
			gconfig.setCompressionEnabled(false);
			
			
			if ( ssl != null && !ssl.isEmpty() )
				gconfig.setCustomSSLContext(sslContext);
			
			XMPPTCPConnection conn = new XMPPTCPConnection(gconfig);
			
			conn.addConnectionListener(new ConnectionListener() {
				
				@Override
				public void reconnectionSuccessful() {
					// TODO Auto-generated method stub
					// System.out.println("reconnectionSuccessful done");
				}
				
				@Override
				public void reconnectionFailed(Exception e) {
					// TODO Auto-generated method stub
					// System.out.println("reconnectionFailed done");
				}
				
				@Override
				public void reconnectingIn(int seconds) {
					// TODO Auto-generated method stub
					// System.out.println("reconnectingIn done");
				}
				
				@Override
				public void connectionClosedOnError(Exception e) {
					// TODO Auto-generated method stub
					System.out.println("connectionClosedOnError");
				}
				
				@Override
				public void connectionClosed() {
					// TODO Auto-generated method stub
					// System.out.println("connectionClosed done");
				}
				
				@Override
				public void connected(XMPPConnection connection) {
					// TODO Auto-generated method stub
					// System.out.println("connected done");
				}
				
				@Override
				public void authenticated(XMPPConnection connection) {
					// TODO Auto-generated method stub
					// System.out.println("authenticated done");
				}
			});	
			
			conn.addPacketListener(new PacketListener() {
				
				@Override
				public void processPacket(Packet packet) throws NotConnectedException {
					
					System.out.println("receive : " + packet);
				}
			}, new PacketFilter() {
				
				@Override
				public boolean accept(Packet packet) {
					return true;
				}
			});
			
			conn.addPacketSendingListener(new PacketListener() {
				@Override
				public void processPacket(Packet packet) throws NotConnectedException {
					if ( packet != null && packet.toString() != null && 
							!"available".equals(packet.toString().trim()) && !"unavailable".equals(packet.toString().trim()) ) {
						System.out.println("send : "+packet);
					}
				}
			}, new PacketFilter() {
				
				@Override
				public boolean accept(Packet packet) {
					return true;
				}
			});
			
			conn.connect();
			
			/*
			conn.loginAnonymously();

			int i = 0;

			if ( conn.isConnected()) {
				IQ iq = new IQ() {
					@Override
					public String getChildElementXML() {
						return "<connectionRequest " + "xmlns=\"urn:broadband-forum-org:cwmp:xmppConnReq-1-0\"" + ">"
								+ "<username>" + username + "</username>" + "<password>" + password + "</password>"
								+ "</connectionRequest>";
					}
				};
   
				iq.setType(Type.GET);
				iq.setFrom("avsystem.com/prod/${?HOSTNAME}@avsystem.com/prod");
				iq.setTo(stbId+"@stbxmpps.voo.be/stb");
				iq.setPacketID( messageId + ++offset );
				
				
				// System.out.println("send : " + iq);
				conn.sendPacket(iq);
			}
			
			while (conn.isConnected()) {
				Thread.sleep(500);
				i++;
//				System.out.print(" " + i);
				if (i > 10) {
					break;
				}

			}
			System.out.println("Á¾·á");
			conn.disconnect();
			conn = null ;
			
			*/
			// System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	// <iq type="result" 
	// to="avsystem.com/prod/${?hostname}@avsystem.com/prod/6e6c2488f99d32cfc0b6c535cac4df85" 
	// from="987654321@stbxmpps.voo.be/stb" id="ASDASD-0">
	// <response><metadata><responseId>98e3c5f9-d1e5-4612-9104-3c019dd94fc9</responseId>
	// <code>204</code><status>No Content</status><jobLogs/></metadata><rawData>null</rawData></response>
	// </iq>

	
	
	public static void setSSL () { 
		try {
			sslContext = SSLContext.getInstance(ssl);
			TrustManager tm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
						throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			};
			sslContext.init(null, new TrustManager[] { tm }, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
