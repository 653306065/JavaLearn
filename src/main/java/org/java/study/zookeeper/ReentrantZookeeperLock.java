package org.java.study.zookeeper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ReentrantZookeeperLock {

	public AtomicInteger state = new AtomicInteger(0);//状态标识

	private ZooKeeper zooKeeper;

	private String lock;//锁的字符串

	private Thread owner;//当前占有线程

	public ReentrantZookeeperLock(String host, int port, String lock) {
		this.lock = "/" + lock;
		this.zooKeeper = getZookeeper(host, port);
	}

	public ZooKeeper getZookeeper(String host, int port) {
		try {
			ZooKeeper zooKeeper = new ZooKeeper(host, port, null);
			return zooKeeper;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void close() {
		try {
			zooKeeper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void lock() {
		if (!tryLock()) {//尝试获取锁
			while (true) {//继续测试获取
				await();//获取不到等待通知
				if (tryLock()) {
					break;
				}
			}
		}
	}

	public boolean tryLock() {
		try {
			if(Thread.currentThread()==owner) {//重入性
				state.addAndGet(1);
				return true;
			}
			byte[] data = Thread.currentThread().getName().getBytes();
			if (zooKeeper.exists(lock, false) == null) {
				zooKeeper.create(lock, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);//zookeeper保证create的原子性(只有一个线程能创建)
				owner=Thread.currentThread();
				state.addAndGet(1);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}

	}

	public void await() {
		try {
			CountDownLatch countDownLatch = new CountDownLatch(1);
			//这里存在多次监听的情况，需要优化
			zooKeeper.addWatch(lock, new Watcher() {
				public void process(WatchedEvent event) {
					if (event.getType() == EventType.NodeDeleted) {
						countDownLatch.countDown();
					}
				}
			}, AddWatchMode.PERSISTENT);
			if (zooKeeper.exists(lock, false) != null) {
				countDownLatch.await();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void unlock() {
		if (owner == Thread.currentThread() && state.addAndGet(-1) == 0) {
			try {
				zooKeeper.delete(lock, -1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int value=0;

	public static void main(String[] args) {
		ReentrantZookeeperLock reentrantZookeeperLock = new ReentrantZookeeperLock("127.0.0.1", 2181, "test");
		for (int i = 0; i < 20; i++) {
			Thread thread = new Thread() {
				public void run() {
					reentrantZookeeperLock.lock();
					reentrantZookeeperLock.lock();
					System.out.println(getName() + ",获取锁成功");
					System.out.println(value++);
					reentrantZookeeperLock.unlock();
					reentrantZookeeperLock.unlock();
					System.out.println(getName() + ",释放锁成功");
				}
			};
			thread.start();
		}
	}

}
