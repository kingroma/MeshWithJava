package mesh.xmpp.net;

import org.jivesoftware.smack.util.Base64;
import org.redisson.api.RTopic;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import mesh.generic.GenericClientController;
import mesh.redis.RedissonClientControllerSubscribe;
import mesh.redis.RedissonConnection;
import mesh.xmpp.packet.XmppXmlParser;
import mesh.xmpp.packet.XmppXmlTemplate;
import mesh.xmpp.packet.XmppXmlType;

// receive: <?xml version="1.0"?><stream:stream to="stbxmpps.voo.be" xml:lang="en" version="1.0" xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams">
// send: <stream:stream xmlns:stream="http://etherx.jabber.org/streams" xmlns="jabber:client" version="1.0" id="4f5c64eb5cbe1f3ad9fcc59b8d5c1d7b" from="stbxmpps.voo.be">
// send: <stream:features xmlns="http://etherx.jabber.org/streams" xmlns:stream="http://etherx.jabber.org/streams"><mechanisms xmlns="urn:ietf:params:xml:ns:xmpp-sasl"><mechanism>EXTERNAL</mechanism><mechanism>ANONYMOUS</mechanism></mechanisms></stream:features>
// receive: <auth mechanism="EXTERNAL" xmlns="urn:ietf:params:xml:ns:xmpp-sasl">MTg0Nzk3NTk4NEBzdGJ4bXBwcy52b28uYmUvc3Ri</auth>
// send: <success xmlns="urn:ietf:params:xml:ns:xmpp-sasl"/>

public class XmppClientController implements GenericClientController {
	public ChannelHandlerContext ctx = null ;
	
	private XmppXmlParser xmppXmlParser = new XmppXmlParser();
	
	private XmppClientInformation xmppClientInformation = new XmppClientInformation();
	
	// private Jedis redis = RedisConnection.getJedis();
	
	private String pubsubPrefix = "messages:";
	
	private RedissonClientControllerSubscribe rccs = null ; 
	
	public XmppClientController(ChannelHandlerContext ctx) { 
		this.ctx = ctx ; 
	}
	
	public void onClose() {
		// System.out.println("onClose");
		if ( rccs != null ) {
			rccs.close();
		}
		rccs = null ; 
	}
	
	/**
	 * 
	 *	xmpp:connection receive:<stream:stream to="avsystem.com/prod/${?HOSTNAME}@avsystem.com/prod" xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams" version="1.0">
 		xmpp:connection send: 	<stream:stream xmlns:stream="http://etherx.jabber.org/streams" xmlns="jabber:client" version="1.0" id="7819a7905eda06cbd7d91840110ed955" from="avsystem.com/prod/${?HOSTNAME}@avsystem.com/prod">
		xmpp:connection send: 	<stream:features xmlns="http://etherx.jabber.org/streams" xmlns:stream="http://etherx.jabber.org/streams"><mechanisms xmlns="urn:ietf:params:xml:ns:xmpp-sasl"><mechanism>EXTERNAL</mechanism><mechanism>ANONYMOUS</mechanism></mechanisms></stream:features>
		// ==== Connection ====
		
		xmpp:connection receive:<auth mechanism="ANONYMOUS" xmlns="urn:ietf:params:xml:ns:xmpp-sasl"></auth>
		xmpp:connection send: 	<success xmlns="urn:ietf:params:xml:ns:xmpp-sasl"/>
		xmpp:connection receive:<stream:stream to="avsystem.com/prod/${?HOSTNAME}@avsystem.com/prod" xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams" version="1.0">
		xmpp:connection send: 	<stream:stream xmlns:stream="http://etherx.jabber.org/streams" xmlns="jabber:client" version="1.0" id="7819a7905eda06cbd7d91840110ed955" from="avsystem.com/prod/${?HOSTNAME}@avsystem.com/prod">
		xmpp:connection send: 	<stream:features xmlns="http://etherx.jabber.org/streams" xmlns:stream="http://etherx.jabber.org/streams"><bind xmlns="urn:ietf:params:xml:ns:xmpp-bind"/><session xmlns="urn:ietf:params:xml:ns:xmpp-session"/></stream:features>
		
		xmpp:connection receive:<iq id='11Ft2-0' type='set'><bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'></bind></iq>
		xmpp:connection send: 	<iq type="result" id="11Ft2-0"><bind xmlns="urn:ietf:params:xml:ns:xmpp-bind"><jid>avsystem.com/prod/${?hostname}@avsystem.com/prod/8957fcc5f72bafac19b4e576dcd0a352</jid></bind></iq>
		xmpp:connection receive:<iq id='11Ft2-1' type='set'><session xmlns="urn:ietf:params:xml:ns:xmpp-session"/></iq>
		xmpp:connection send: 	<iq type="result" id="11Ft2-1"><session xmlns="urn:ietf:params:xml:ns:xmpp-session"/></iq>
		xmpp:connection receive:<presence id='11Ft2-2'></presence>
		xmpp:connection receive:<iq id='123456' to='987654321@stbxmpps.voo.be/stb' type='get'><connectionRequest xmlns="urn:broadband-forum-org:cwmp:xmppConnReq-1-0">   <username>30918F-STB-987654321</username>   <password>c720e9e5</password></connectionRequest></iq>
	 * */
	public void onMessage(String message){
		boolean wantEnd = false ; 
		if ( message.contains(XmppXmlTemplate.getStreamEnder())) {
			wantEnd = true ; 
			message = message.replace(XmppXmlTemplate.getStreamEnder(), "") ; 
		}
		
		int type = xmppXmlParser.whatIsIt(message);

		if ( XmppXmlType.STREAM == type ) {
			doStream( message ) ;
		} 
		
		else if ( XmppXmlType.AUTH_MECHANISM == type) {
			doAuthMechanism ( message ) ;
		} 
		
		else if ( XmppXmlType.IQ_TYPE_SET_BIND == type ) {
			doBind( message );
		} 
		
		else if ( XmppXmlType.IQ_TYPE_SET_SESSION == type ) {
			doSession( message );
		}
		
		else if ( XmppXmlType.IQ == type ) {
			Document doc = xmppXmlParser.parseStringToDocument(message);
			if ( doc != null && doc.getDocumentElement() != null ) { 
				Element e = doc.getDocumentElement();
				String to = e.getAttribute("to");
				
				if ( to != null && !to.isEmpty() && to.split("@").length == 2 ) {
					String split[] = to.split("@");
					String username = split[0];
					if ( username != null && !username.isEmpty()) {
						doPublish ( username , message ) ;
					}
							
				}
				
			}
		}
		
		else if ( XmppXmlType.PRESENCE == type ) { 
			Document doc = xmppXmlParser.parseStringToDocument(message);
			if ( doc != null && doc.getDocumentElement() != null ) { 
				Element e = doc.getDocumentElement();
				String presenceType = e.getAttribute("type");
				if ( e != null && presenceType != null && !presenceType.isEmpty() && "unavailable".equals(presenceType)) {
					xmppClientInformation.setAbailable(false);
				}else {
					xmppClientInformation.setAbailable(true);
				}
			}
		}
		
		if ( wantEnd ) {
			send (XmppXmlTemplate.getStreamEnder());
			this.onClose();
		}
		
	}
	
	private void doStream(String message) {
		Document doc = xmppXmlParser.parseStringToDocument(message + XmppXmlTemplate.getStreamEnder());
		
		// domain 정보를 저장하기 위하여 
		if ( doc != null && doc.getDocumentElement() != null ) { 
			Element e = doc.getDocumentElement() ;
			String to = e.getAttribute("to") ; 
			xmppClientInformation.setStreamUrl(to) ;
		}
		
		send ( XmppXmlTemplate.getStreamStarterWithNewId() );
		
		// mechanism 보내기 
		if ( xmppClientInformation.getMechanism() == null || xmppClientInformation.getMechanism().isEmpty() ) {
			// first send mecha
			send ( XmppXmlTemplate.getStreamFeaturesMechanisms() );
		} 
		
		// mechanism 완료하였으면 bind session 해라 
		else if ( xmppClientInformation.getMechanism() != null && !xmppClientInformation.getMechanism().isEmpty() 
				&& !xmppClientInformation.isBind() && !xmppClientInformation.isSession() ) {
			// second bind 
			send ( XmppXmlTemplate.getStreamFeaturesBindSession() );
		}
	}
	
	private void doAuthMechanism(String message) {
		try {
			Document doc = xmppXmlParser.parseStringToDocument(message);
			
			if ( doc != null && doc.getDocumentElement() != null ) { 
				Element e = doc.getDocumentElement();
				if ( e.getNodeName().equals("auth") ) {
					String mechanism = e.getAttribute("mechanism");
					xmppClientInformation.setMechanism(mechanism);

					if ( e.getTextContent() != null && !e.getTextContent().isEmpty() ) {
						String authBase64 = new String(Base64.decode(e.getTextContent()));
						
						xmppClientInformation.setStreamUrl(authBase64);
					}
					
					send ( XmppXmlTemplate.getSuccess() ) ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doBind(String message) {
		Document doc = xmppXmlParser.parseStringToDocument(message);
		
		if ( doc != null && doc.getDocumentElement() != null ) { 
			Element e = doc.getDocumentElement();
			String id = e.getAttribute("id");
			
			NodeList nodeList = e.getElementsByTagName("resource");
			if ( nodeList.getLength() > 0 ) { 
				xmppClientInformation.setResource(nodeList.item(0).getTextContent());
			}
			
			xmppClientInformation.setJid(
					xmppClientInformation.getUsername() + 
					"@" + 
					xmppClientInformation.getDomain() + 
					"/" + 
					xmppClientInformation.getResource() 
					);
			
			send ( XmppXmlTemplate.getBindResult(id,xmppClientInformation.getJid()) ) ;
			
			xmppClientInformation.setBind(true);
		}
	}
	
	private void doSession(String message) {
		Document doc = xmppXmlParser.parseStringToDocument(message);
		if ( doc != null && doc.getDocumentElement() != null ) { 
			Element e = doc.getDocumentElement();
			String id = e.getAttribute("id");
			
			send ( XmppXmlTemplate.getSessionResult(id) ) ;
			
			xmppClientInformation.setConnect(true);
			xmppClientInformation.setLogin(true);
			xmppClientInformation.setAbailable(true);
			
			clientRedisStarter();
		}
	}
	
	// ctx.write(Unpooled.copiedBuffer("Hello " + message, CharsetUtil.UTF_8));
	private void send(String message){
		// System.out.println("send : " + message);
		message += "\n\n";
		ctx.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
	}
	
	
	private void clientRedisStarter() {
		String username = xmppClientInformation.getUsername();
		
		rccs = new RedissonClientControllerSubscribe(this,pubsubPrefix + username);
		rccs.run();
		
	}
	
//	private boolean checkOnline(String username) {
//		boolean ret = false ; 
//		Map<String,String> map = redis.pubsubNumSub(pubsubPrefix + username);
//		String value = map.get(pubsubPrefix + username);
//		
//		try {
//			int count = Integer.parseInt(value);
//			if ( count > 0 ) {
//				ret = true ; 
//			}
//		} catch (Exception e) {	}
//		
//		return ret ; 
//	}
	
	public void doPublish(String username , String message) {
		// redis.publish(pubsubPrefix + username, message);
		RTopic<String> topic = RedissonConnection.getRedisson().getTopic(pubsubPrefix + username);
		topic.publish(message);
	}
	
	@Override
	public void onMessageFromSubscribe(String channel , String message) {
		System.out.println("subscribe : " + message);
		
		send ( message ) ;
	}
	
	
}
