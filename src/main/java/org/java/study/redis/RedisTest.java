package org.java.study.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanParams;

public class RedisTest {

	public static JedisCluster jedisCluster;

	public static JedisCluster getJedisCluster(List<String> list) {
		HashSet<HostAndPort> set = new HashSet<HostAndPort>();
		for (String str : list) {
			HostAndPort HostAndPort = new HostAndPort(str.split(":")[0], Integer.valueOf(str.split(":")[1]));
			set.add(HostAndPort);
		}
		JedisCluster JedisCluster = new JedisCluster(set);
		return JedisCluster;
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("10.220.201.71:8001");
		list.add("10.220.201.71:8002");
		list.add("10.220.201.72:8001");
		list.add("10.220.201.72:8002");
		list.add("10.220.201.71:8003");
		list.add("10.220.201.72:8003");
		JedisCluster jedisCluster = getJedisCluster(list);

//		jedisCluster.mset(new String[]{"key1","value1","key2","value2"});
//		List<String> mget=  jedisCluster.mget(new String[] {"key1","key2"});
//		System.out.println(JSON.toJSONString(mget));

		jedisCluster.set("test", "testValue");
		jedisCluster.expire("test", 10);
		System.out.println(jedisCluster.get("test"));
		jedisCluster.del("test");
		System.out.println(jedisCluster.get("test"));
		jedisCluster.setex("key", 10, "value");

		jedisCluster.set("count", String.valueOf(0));
		for (int i = 0; i < 100; i++) {
			jedisCluster.incr("count");
		}
		String count = jedisCluster.get("count");
		System.out.println(count);

		count = jedisCluster.getSet("count", String.valueOf(0));
		System.out.println(count);

		for (int i = 0; i < 100; i++) {
			long l = jedisCluster.rpush("myList", "value" + i);
			System.out.println(l);
		}
		long l = jedisCluster.llen("myList");
		System.out.println(l);
		List<String> myList = jedisCluster.lrange("myList", 0, l);
		System.out.println(JSON.toJSONString(myList));

		for (int i = 0; i < l; i++) {
			String value = jedisCluster.lpop("myList");
			System.out.println(value);
		}

		jedisCluster.sadd("set", new String[] { "java", "go", "py", "c++", "js" });
		System.out.println(jedisCluster.spop("set"));
		System.out.println(JSON.toJSONString(jedisCluster.smembers("set")));
		ScanParams ScanParams=new ScanParams();
		ScanParams.match("*");
		ScanParams.count(10);
		System.out.println(JSON.toJSONString(jedisCluster.scan("0", ScanParams)));
		 
	}
}
