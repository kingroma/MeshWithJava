package mesh.redis;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

import mesh.generic.GenericClientController;

public class RedissonClientControllerSubscribe {

	private GenericClientController gcc = null ; 
	
	private RTopic<String> topic = null ;
	
	private String key = "" ; 
	
	public RedissonClientControllerSubscribe(GenericClientController gcc , String key) {
		this.gcc = gcc; 
		this.key = key ; 
	}
	

	public void run() {
		
		RedissonClient redis = RedissonConnection.getRedisson();
		topic = redis.getTopic(key);
		topic.addListener(new MessageListener<String>() {
			
			@Override
			public void onMessage(String channel, String msg) {
				gcc.onMessageFromSubscribe(channel, msg);
			}
		});
	}
	
	public void close() {
		topic.removeAllListeners();
		topic = null ; 
	}
	
}
