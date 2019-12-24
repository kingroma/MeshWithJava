package mesh.xmpp.constant;

import java.util.Random;

public class Globals {
	private static final Random RANDOM = new Random();
	
	private static final String RANDOM_ID_TEXT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static final int ID_SIZE = 20 ; 
	
	public static String serviceName = "XmppServer";
	
	public static final String redisIp = "127.0.0.1";
	
	public static final int redisPort = 6379 ;
	
	public static String getId(){
		StringBuilder sb = new StringBuilder();
		
		
		for ( int i = 0 ; i < ID_SIZE ; i ++ ){
			sb.append(RANDOM_ID_TEXT.charAt(RANDOM.nextInt(RANDOM_ID_TEXT.length())));
		}
		
		return sb.toString();
	}
}
