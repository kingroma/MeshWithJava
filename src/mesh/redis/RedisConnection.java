package mesh.redis;

import java.time.Duration;

import mesh.xmpp.constant.Globals;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnection {
	private static boolean isOpen = false ; 
	
	private static JedisPoolConfig jedisPoolConfig = null ; 
	private static JedisPool pool = null ; 
	private static Jedis jedis = null ; 
	
	private RedisConnection() {
		
	}
	
	private static void init() { 
		if (false == isOpen) {
			String ip = Globals.redisIp;
			int port = Globals.redisPort;		
			
			jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxTotal(128);
			jedisPoolConfig.setMaxIdle(128);
			jedisPoolConfig.setMinIdle(36);
			jedisPoolConfig.setTestOnBorrow(true);
	        jedisPoolConfig.setTestOnReturn(true);
	        jedisPoolConfig.setTestWhileIdle(true);
	        jedisPoolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
	        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
	        jedisPoolConfig.setNumTestsPerEvictionRun(3);
	        jedisPoolConfig.setBlockWhenExhausted(true);
			
			
	        pool = new JedisPool(jedisPoolConfig, ip , port, 1000, null);
	        // jedis = pool.getResource();
	        isOpen = true ; 
		}
	}
	
	public static Jedis getJedis() {
		// init();
		String ip = Globals.redisIp;
		int port = Globals.redisPort;		
		
		jedisPoolConfig = new JedisPoolConfig();
		
        pool = new JedisPool(jedisPoolConfig, ip , port, 1000, null);
        
		Jedis jedis = pool.getResource();
		
		return jedis;
	}
}
