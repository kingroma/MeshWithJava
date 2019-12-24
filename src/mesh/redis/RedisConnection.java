package mesh.redis;

import mesh.xmpp.constant.Globals;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnection {
	private static boolean isOpen = false ; 
	
	private static JedisPoolConfig jedisPoolConfig = null ; 
	private static JedisPool pool = null ; 
	// private static Jedis jedis = null ; 
	
	private RedisConnection() {
		
	}
	
	private static void init() { 
		if (false == isOpen) {
			String ip = Globals.redisIp;
			int port = Globals.redisPort;		
			
			jedisPoolConfig = new JedisPoolConfig();
	        pool = new JedisPool(jedisPoolConfig, ip , port, 1000, null);
	        isOpen = true ; 
		}
	}
	
	public static Jedis getJedis() {
		init();
		Jedis jedis = pool.getResource();
		return jedis ;
	}
}
