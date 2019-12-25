package test;

import mesh.redis.RedisConnection;
import redis.clients.jedis.Jedis;

public class RedisMessageSendTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String channel = "messages:123";
		String message = "\"<message><metadata class=\\\"object\\\"><responseId type=\\\"string\\\"/><route type=\\\"string\\\"/></metadata><rawData type=\\\"string\\\">{\\\"id\\\":\\\"5e01d6ae9f93eb50f972c42a\\\",\\\"devices\\\":[\\\"987654321\\\"],\\\"priority\\\":0,\\\"expireDate\\\":\\\"Fri Nov 01 23:59:59 CET 2019\\\",\\\"messageId\\\":\\\"1577178771011\\\",\\\"title\\\":\\\"\\\",\\\"sendDate\\\":\\\"Tue Dec 24 10:13:18 CET 2019\\\",\\\"version\\\":1,\\\"targetType\\\":\\\"device\\\",\\\"dataType\\\":\\\"txt\\\",\\\"type\\\":\\\"D\\\",\\\"contentType\\\":\\\"contentType\\\",\\\"contents\\\":\\\"1234514567890901234567890123456789012345678901234567890123456789\\\",\\\"revoke\\\":\\\"\\\",\\\"targetIds\\\":[]}</rawData></message>\"";
		
		Jedis redis = RedisConnection.getJedis();
		redis.publish(channel, message.replaceAll("\"", "\\\""));
		
		System.out.println(message);
	}

}
