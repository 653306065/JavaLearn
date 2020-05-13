package org.java.study.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.SetParams;

public class RedisLock {

	private AtomicInteger state = new AtomicInteger(0);// 标识状态

	private JedisCluster jedisCluster;// 链接

	private String lock;// 锁
	
	private Thread owner;

	private int time;// 自动释放锁的时间

	public RedisLock(List<String> list, String lock, int time) {
		this.jedisCluster = getJedisCluster(list);
		this.lock = lock;
		this.time = time;
	}

	public void lock() {
		if(Thread.currentThread()==owner) {
			state.incrementAndGet();
			return;
		}
		String value = UUID.randomUUID().toString().replace("-", "");
		long startTime = System.currentTimeMillis();
		while (true) {
			try {
				SetParams setParams = new SetParams();
				setParams.nx();
				setParams.ex(time);
				if ("OK".equals(jedisCluster.set(lock, value, setParams))) {
					owner=Thread.currentThread();
					state.set(1);
					return;
				}
				if (System.currentTimeMillis() - startTime >= time*1000) {
					return;
				}
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void unlock() {
		if(Thread.currentThread()==owner && state.decrementAndGet()==0) {
			jedisCluster.del(lock);
			owner=null;
		}
	}

	public JedisCluster getJedisCluster(List<String> list) {
		HashSet<HostAndPort> set = new HashSet<HostAndPort>();
		for (String str : list) {
			HostAndPort HostAndPort = new HostAndPort(str.split(":")[0], Integer.valueOf(str.split(":")[1]));
			set.add(HostAndPort);
		}
		JedisCluster JedisCluster = new JedisCluster(set);
		return JedisCluster;
	}

	public static int value = 0;

	public static void main(String[] args) {
		long startTime=System.currentTimeMillis();
		CountDownLatch countDownLatch=new CountDownLatch(100);
		List<String> list = new ArrayList<String>();
		list.add("10.220.201.71:8001");
		list.add("10.220.201.71:8002");
		list.add("10.220.201.72:8001");
		list.add("10.220.201.72:8002");
		list.add("10.220.201.71:8003");
		list.add("10.220.201.72:8003");
		RedisLock redisLock = new RedisLock(list, "lock", 1000);
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread() {
				public void run() {
					redisLock.lock();
					redisLock.lock();
					try {
						this.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(value++);
					redisLock.unlock();
					redisLock.unlock();
					countDownLatch.countDown();
				}
			};
			thread.start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime=System.currentTimeMillis();
		System.out.println((endTime-startTime)/1000f);
	}
}
