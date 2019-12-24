package test;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import mesh.xmpp.constant.Globals;
import mesh.xmpp.packet.XmppXmlPattern;

public class Test {

	public static void main(String[] args) {
		 pattern();
		
		// xml();
		
		// base64();
		
		//splitDomain();
	}
	
	public static void splitDomain() {
		String url = "avsystem.com/prod/${?HOSTNAME}@avsystem.com/prod";
		
		System.out.println();
		
		String uri = url.split("@")[0];
		String domain = url.split("@")[1];
		
		System.out.println(uri);
		System.out.println(domain);
		System.out.println(domain.indexOf("/"));
		System.out.println(domain.substring(0,domain.indexOf("/")));
		System.out.println(domain.substring(domain.indexOf("/") + 1,domain.length()));
		
		
	}
	
	public static void base64() {
		String auth = "MTg0Nzk3NTk4NEBzdGJ4bXBwcy52b28uYmUvc3Ri";
		
		String decode = new String ( Base64.getDecoder().decode(auth)  ) ;
		
		System.out.println(decode);
	}
	
	public static void xml(){
		try {
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        
	        String text = "";//"<?xml version=\"1.0\"?>";
	        text += "<stream:stream to=\"stbxmpps.voo.be\" xml:lang=\"en\" version=\"1.0\" xmlns=\"jabber:client\" xmlns:stream=\"http://etherx.jabber.org/streams\">";
	        text += "</stream:stream>";
	        InputSource is = new InputSource( new StringReader( text ) );
	        Document doc = dBuilder.parse(is);
	        
	        System.out.println(doc.getDocumentElement().getNodeName());
	        // System.out.println(doc.g);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void pattern () {
		StringBuilder sb = new StringBuilder();
        sb.append("<stream:stream");
        sb.append(" to=\"").append(Globals.serviceName).append("\"");
        sb.append(" xmlns=\"jabber:client\"");
        sb.append(" xmlns:stream=\"http://etherx.jabber.org/streams\"");
        sb.append(" version=\"1.0\">");
        
        
        String stream = sb.toString();
        stream = "<iq id='yEbyp-0' type='set'><bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'></bind></iq>";
        System.out.println(stream);
        System.out.println(stream.matches(XmppXmlPattern.IQ_TYPE_SET_BIND_PATTERN));
	}
}
