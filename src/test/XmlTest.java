package test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class XmlTest {
	// {"metadata":{"responseId":"","route":""},
		// "rawData":"{"id":"","devices":["987654321"],"priority":0,
		// "expireDate":"Fri Nov 01 23:59:59 CET 2019","messageId":"1577178771011","title":"","sendDate":"Tue Dec 24 10:13:18 CET 2019",
		// "version":1,"targetType":"device","dataType":"txt","type":"D","contentType":"contentType",
		// "contents":"1234514567890901234567890123456789012345678901234567890123456789","revoke":"","targetIds":[]}"}

		// '{"metadata":{"responseId":"","route":""},"rawData":"{"id":"","devices":["987654321"],"priority":0,"expireDate":"Fri Nov 01 23:59:59 CET 2019","messageId":"1577178771011","title":"","sendDate":"Tue Dec 24 10:13:18 CET 2019","version":1,"targetType":"device","dataType":"txt","type":"D","contentType":"contentType","contents":"1234514567890901234567890123456789012345678901234567890123456789","revoke":"","targetIds":[]}"}'

	// node --max-old-space-size=8192 xmpp-msg.js -n 1 -t 1 -i 2 -f 3 -o 0 -r
		// node --max-old-space-size=8192 xmpp-msg.js -n 1 -t 1 -i 0
	public static void main(String[] args) {
		XmlTest();
	}
	
	
	public static void XmlTest() {
		String str = 
				"{\"metadata\":{\"responseId\":\"\",\"route\":\"\"}" 
				+ ","
				+ "\"rawData\":\"{"
				+ "\"id\":\"5e01d6ae9f93eb50f972c42a\",\"devices\":[\"987654321\"],\"priority\":0,\"expireDate\":\"Fri Nov 01 23:59:59 CET 2019\",\"messageId\":\"1577178771011\",\"title\":\"\",\"sendDate\":\"Tue Dec 24 10:13:18 CET 2019\",\"version\":1,\"targetType\":\"device\",\"dataType\":\"txt\",\"type\":\"D\",\"contentType\":\"contentType\",\"contents\":\"1234514567890901234567890123456789012345678901234567890123456789\",\"revoke\":\"\",\"targetIds\":[]}\"}";
		
		
		str = "{\"metadata\":{\"responseId\":\"\",\"route\":\"\"},\"rawData\":\"{\\\"id\\\":\\\"5e01d6ae9f93eb50f972c42a\\\",\\\"devices\\\":[\\\"987654321\\\"],\\\"priority\\\":0,\\\"expireDate\\\":\\\"Fri Nov 01 23:59:59 CET 2019\\\",\\\"messageId\\\":\\\"1577178771011\\\",\\\"title\\\":\\\"\\\",\\\"sendDate\\\":\\\"Tue Dec 24 10:13:18 CET 2019\\\",\\\"version\\\":1,\\\"targetType\\\":\\\"device\\\",\\\"dataType\\\":\\\"txt\\\",\\\"type\\\":\\\"D\\\",\\\"contentType\\\":\\\"contentType\\\",\\\"contents\\\":\\\"1234514567890901234567890123456789012345678901234567890123456789\\\",\\\"revoke\\\":\\\"\\\",\\\"targetIds\\\":[]}\"}";
		// System.out.println(str);
		
		XMLSerializer xmlSerializer = new XMLSerializer();  
		  
        String strXml1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><book>" +   
                        "<title>XML To JSON</title><author>json-lib</author></book>";  
  
        // publish messages:123 "{\"metadata\":{\"responseId\":\"\",\"route\":\"\"},\"rawData\":\"{\\\"id\\\":\\\"5e01d6ae9f93eb50f972c42a\\\",\\\"devices\\\":[\\\"987654321\\\"],\\\"priority\\\":0,\\\"expireDate\\\":\\\"Fri Nov 01 23:59:59 CET 2019\\\",\\\"messageId\\\":\\\"1577178771011\\\",\\\"title\\\":\\\"\\\",\\\"sendDate\\\":\\\"Tue Dec 24 10:13:18 CET 2019\\\",\\\"version\\\":1,\\\"targetType\\\":\\\"device\\\",\\\"dataType\\\":\\\"txt\\\",\\\"type\\\":\\\"D\\\",\\\"contentType\\\":\\\"contentType\\\",\\\"contents\\\":\\\"1234514567890901234567890123456789012345678901234567890123456789\\\",\\\"revoke\\\":\\\"\\\",\\\"targetIds\\\":[]}\"}"
        
        JSONObject jsonObject3 = JSONObject.fromObject(str);    
        String strXml3 = xmlSerializer.write( jsonObject3 );  
        System.out.println( strXml3 );  
        
        
        
        // publish messages:123 "<message><metadata class=\\\"object\\\"><responseId type=\\\"string\\\"/><route type=\\\"string\\\"/></metadata><rawData type=\\\"string\\\">{\\\"id\\\":\\\"5e01d6ae9f93eb50f972c42a\\\",\\\"devices\\\":[\\\"987654321\\\"],\\\"priority\\\":0,\\\"expireDate\\\":\\\"Fri Nov 01 23:59:59 CET 2019\\\",\\\"messageId\\\":\\\"1577178771011\\\",\\\"title\\\":\\\"\\\",\\\"sendDate\\\":\\\"Tue Dec 24 10:13:18 CET 2019\\\",\\\"version\\\":1,\\\"targetType\\\":\\\"device\\\",\\\"dataType\\\":\\\"txt\\\",\\\"type\\\":\\\"D\\\",\\\"contentType\\\":\\\"contentType\\\",\\\"contents\\\":\\\"1234514567890901234567890123456789012345678901234567890123456789\\\",\\\"revoke\\\":\\\"\\\",\\\"targetIds\\\":[]}</rawData></message>"
	}
}
