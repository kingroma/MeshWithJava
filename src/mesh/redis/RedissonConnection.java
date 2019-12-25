package mesh.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import mesh.xmpp.constant.Globals;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedissonConnection {
	private static RedissonClient redisson = Redisson.create();
	public RedissonConnection() {
		
		
	}
	
	public static RedissonClient getRedisson() {
		// init();
		return redisson ; 
	}
}
