package mesh.xmpp.packet;

import mesh.xmpp.constant.Globals;

public class XmppXmlTemplate {
	
	// <stream:stream xmlns:stream="http://etherx.jabber.org/streams" xmlns="jabber:client" version="1.0" id="" from="stbxmpps.voo.be">
	private static String getStreamStarter(){
		StringBuilder sb = new StringBuilder();
        sb.append("<stream:stream");
        sb.append(" to=\"").append(Globals.serviceName).append("\"");
        sb.append(" xmlns=\"jabber:client\"");
        sb.append(" xmlns:stream=\"http://etherx.jabber.org/streams\"");
        sb.append(" version=\"1.0\">");
        
        return sb.toString();
	}
	
	public static String getStreamStarterWithNewId () {
		String id = "";
		StringBuilder sb = new StringBuilder();
        sb.append("<stream:stream");
        sb.append(" id=\"").append(Globals.getId()).append("\"");
        sb.append(" from=\"").append(Globals.serviceName).append("\"");
        sb.append(" xmlns=\"jabber:client\"");
        sb.append(" xmlns:stream=\"http://etherx.jabber.org/streams\"");
        sb.append(" version=\"1.0\">");
//        sb.append("\n");
		
		return sb.toString() ; 
	}
	
	// <stream:features 
	// 	xmlns="http://etherx.jabber.org/streams" 
	// 	xmlns:stream="http://etherx.jabber.org/streams">
	// 		<mechanisms xmlns="urn:ietf:params:xml:ns:xmpp-sasl">
	//		<mechanism>EXTERNAL</mechanism><mechanism>ANONYMOUS</mechanism></mechanisms>
	// </stream:features>
	public static String getStreamFeaturesMechanisms() {
		StringBuilder sb = new StringBuilder();
		sb.append("<stream:features");
		sb.append(" xmlns=\"http://etherx.jabber.org/streams\"");
		sb.append(" xmlns:stream=\"http://etherx.jabber.org/streams\">");
		sb.append("<mechanisms xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
		sb.append("<mechanism>EXTERNAL</mechanism><mechanism>ANONYMOUS</mechanism>");
		sb.append("</mechanisms>");
		sb.append("</stream:features>");
//		sb.append("\n");
		
		return sb.toString() ;
	}
	
	// <stream:features xmlns="http://etherx.jabber.org/streams" xmlns:stream="http://etherx.jabber.org/streams">
	// <bind xmlns="urn:ietf:params:xml:ns:xmpp-bind"/><session xmlns="urn:ietf:params:xml:ns:xmpp-session"/>
	// </stream:features>
	public static String getStreamFeaturesBindSession() {
		StringBuilder sb = new StringBuilder();
		sb.append("<stream:features xmlns=\"http://etherx.jabber.org/streams\" xmlns:stream=\"http://etherx.jabber.org/streams\">");
		sb.append("<bind xmlns=\"urn:ietf:params:xml:ns:xmpp-bind\"/><session xmlns=\"urn:ietf:params:xml:ns:xmpp-session\"/>");
		sb.append("</stream:features>");
		
		return sb.toString() ;
	}
	
// 	send: <iq type="result" id="_xmpp_bind1"><bind xmlns="urn:ietf:params:xml:ns:xmpp-bind"><jid>1848031075@stbxmpps.voo.be/stb</jid></bind></iq>
	public static String getBindResult(String id, String jid) {
		StringBuilder sb = new StringBuilder();
		sb.append("<iq type=\"result\" id=\"");
		sb.append(id);
		sb.append("\"><bind xmlns=\"urn:ietf:params:xml:ns:xmpp-bind\"><jid>");
		sb.append(jid);
		sb.append("</jid></bind></iq>");
		
		return sb.toString();
	}
	
//	<iq type="result" id="_xmpp_session1"><session xmlns="urn:ietf:params:xml:ns:xmpp-session"/></iq>	
	public static String getSessionResult(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("<iq type=\"result\" id=\"");
		sb.append(id);
		sb.append("\"><session xmlns=\"urn:ietf:params:xml:ns:xmpp-session\"/></iq>");
		
		return sb.toString();
	}
	

	public static String getSuccess() {
		StringBuilder sb = new StringBuilder();
		sb.append("<success xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\"/>");
		sb.append("\n");
		
		return sb.toString();
	}
		
	// <stream:features xmlns="http://etherx.jabber.org/streams" xmlns:stream="http://etherx.jabber.org/streams">
	// <bind xmlns="urn:ietf:params:xml:ns:xmpp-bind"/><session xmlns="urn:ietf:params:xml:ns:xmpp-session"/></stream:features>
	public static String getStreamEnder(){
		StringBuilder sb = new StringBuilder();
        sb.append("</stream:stream>");
        
        return sb.toString();
	}
}
