package mesh.xmpp.packet;

public class XmppXmlPattern {
	public final static String ALL = "[a-zA-Z0-9\n\t\r !@#$%^&*()_+~`|=\\-.,/?><:;\"'{}]*"; //\"':/=?<>]*";
	public final static String WHITE_SPACE = "[\n\t\r ]*";
	
	
	// <?xml version="1.0"?>
	
	// receive: <?xml version="1.0"?><stream:stream to="stbxmpps.voo.be" xml:lang="en" version="1.0" xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams">
	// send: <stream:stream xmlns:stream="http://etherx.jabber.org/streams" xmlns="jabber:client" version="1.0" id="4f5c64eb5cbe1f3ad9fcc59b8d5c1d7b" from="stbxmpps.voo.be">
	// send: <stream:features xmlns="http://etherx.jabber.org/streams" xmlns:stream="http://etherx.jabber.org/streams"><mechanisms xmlns="urn:ietf:params:xml:ns:xmpp-sasl"><mechanism>EXTERNAL</mechanism><mechanism>ANONYMOUS</mechanism></mechanisms></stream:features>
	// receive: <auth mechanism="EXTERNAL" xmlns="urn:ietf:params:xml:ns:xmpp-sasl">MTg0Nzk3NTk4NEBzdGJ4bXBwcy52b28uYmUvc3Ri</auth>
	// send: <success xmlns="urn:ietf:params:xml:ns:xmpp-sasl"/>
	
	// receive: <?xml version="1.0"?>
	// <stream:stream to="stbxmpps.voo.be" xml:lang="en" version="1.0" xmlns="jabber:client" xmlns:stream="http://etherx.jabber.org/streams">
	public final static String STREAM_STARTER_PATTERN = ALL + "<stream:stream" + ALL  ;
	
	public final static String AUTH_MECHANISM_PATTERN = ALL + "<auth" + ALL + "mechanism" + ALL;
	
	// <iq id='11Ft2-0' type='set'><bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'></bind></iq>
	public final static String IQ_TYPE_SET_BIND_PATTERN = ALL + "<iq" + ALL + "type=['\"]set['\"]" + ALL + "<bind" + ALL ;
	
	public final static String IQ_TYPE_SET_SESSION_PATTERN = ALL + "<iq" + ALL + "type=['\"]set['\"]" + ALL + "<session" + ALL ;
	
	public final static String PRESENCE_PATTERN = ALL + "<presence" + ALL ;
	
	public final static String IQ_PATTERN = ALL + "<iq" + ALL ;
}
