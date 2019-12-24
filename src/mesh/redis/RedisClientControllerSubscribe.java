package mesh.redis;

import mesh.generic.GenericClientController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisClientControllerSubscribe extends Thread {

	private GenericClientController gcc = null ; 
	
	private Jedis jedis = null ; 
	
	private JedisPubSub pubsub = null ;
	
	private String key = "" ; 
	
	public RedisClientControllerSubscribe(GenericClientController gcc , String key) {
		this.gcc = gcc; 
		this.key = key ; 
	}
	

	@Override
	public void run() {
		jedis = RedisConnection.getJedis();
		pubsub = new JedisPubSub() {
        	@Override
        	public void onMessage(String channel, String message) {
        		gcc.onMessageFromSubscribe(channel, message);
        	}
		};
        
		jedis.subscribe(pubsub, key);
	}
	
	public void close() {
		System.out.println("thread close");
		pubsub.unsubscribe();
		jedis.close();
		System.out.println("thread close succ");
	}
	
}
