package test;

import java.util.Map;

import mesh.redis.RedisConnection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

public class RedisTest {

	public static void main(String[] args) {
		
        Jedis jedis = RedisConnection.getJedis();
        
        
        JedisPubSub pubsub = new JedisPubSub() {
        	@Override
        	public void onMessage(String channel, String message) {
        		System.out.println(new String(channel));
        		System.out.println(new String(message));
        	}
		};
        
        jedis.subscribe(pubsub, "ch");
        
        
        Map<String,String> map = jedis.pubsubNumSub("ch");
        
        System.out.println(" count : " + map.keySet().size() );
        for ( String key : map.keySet() ) {
        	System.out.println(" [ " + key + " / " + map.get(key) + " ] "  );
        }
        
	}

}
