package test;

import java.util.ArrayList;
import java.util.List;

import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.config.Config;

public class ReddisonTest {
	
	public static void main(String[] args) {
		
		Config config = new Config();
		// config.useSingleServer().setAddress(address)
		
		RedissonClient redisson = Redisson.create();
		
		List<RTopic> list = new ArrayList<RTopic>();
		
		for (int i = 0 ; i < 1 ; i ++ ) {
			RTopic topic = redisson.getTopic("messages:1234");
			
			topic.addListener(String.class, new MessageListener<String>() {
	            @Override
	            public void onMessage(CharSequence channel, String msg) {
	                System.out.println(channel + " : " + msg);
	            }
	        });
			
			list.add(topic);
		}
	}
}
