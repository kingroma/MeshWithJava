package mesh.xmpp.packet;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import mesh.xmpp.constant.Globals;

public class XmppXmlParser {
	
	public XmppXmlParser(){
		
	}
	
	public int whatIsIt(String message){
		int ret = -1 ; 
		
		if ( message != null && !message.isEmpty() ) {
			
			if ( message.matches(XmppXmlPattern.STREAM_STARTER_PATTERN) ) {
				ret = XmppXmlType.STREAM;
			}
			else if ( message.matches(XmppXmlPattern.AUTH_MECHANISM_PATTERN)) {
				ret = XmppXmlType.AUTH_MECHANISM;
			}
			// <iq id='11Ft2-0' type='set'><bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'></bind></iq>
			else if ( message.matches(XmppXmlPattern.IQ_TYPE_SET_BIND_PATTERN)) {
				ret = XmppXmlType.IQ_TYPE_SET_BIND;
			}
			
			else if ( message.matches(XmppXmlPattern.IQ_TYPE_SET_SESSION_PATTERN)) {
				ret = XmppXmlType.IQ_TYPE_SET_SESSION;
			}
			
			else if ( message.matches(XmppXmlPattern.PRESENCE_PATTERN)) {
				ret = XmppXmlType.PRESENCE;
			}
			else if ( message.matches(XmppXmlPattern.IQ_PATTERN)) {
				ret = XmppXmlType.IQ;
			}
			
		}
		return ret;
		// return XmppXmlType.STREAM ; 
	}

	public Document parseStringToDocument(String message) {
		Document doc = null ; 
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        InputSource is = new InputSource( new StringReader( message ) );
	        doc = dBuilder.parse(is);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return doc ; 
        
	}
	
}
