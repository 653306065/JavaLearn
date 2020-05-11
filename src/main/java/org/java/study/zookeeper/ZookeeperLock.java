package org.java.study.zookeeper;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperLock {

	private String lock;

	private ZooKeeper zooKeeper;

	public ZookeeperLock(String host, int port, String lock) {
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

	public void lock() {
		if (!tryLock()) {
			await();
			while (true) {
				if (tryLock()) {
					break;
				}
			}
		}
	}

	public void await() {
		try {
			CountDownLatch countDownLatch = new CountDownLatch(1);
			zooKeeper.addWatch(lock, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					if (event.getType() == EventType.NodeDeleted) {
						// System.out.println("countDown");
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

	public boolean tryLock() {
		try {
			byte[] data = Thread.currentThread().getName().getBytes();
			if (zooKeeper.exists(lock, false) == null) {
				zooKeeper.create(lock, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void unlock() {
		try {
			zooKeeper.delete(lock, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int value=0;
	
	public static void main(String[] args) {
		//同时开启2个main方法，验证最后的运行时间，判断分布式锁是否生效
		ZookeeperLock zookeepreLock = new ZookeeperLock("127.0.0.1", 2181, "locky");
		CountDownLatch countDownLatch=new CountDownLatch(10);
		long startTime= System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			final int n = i;
			Thread thread = new Thread("thread" + n) {
				public void run() {
					zookeepreLock.lock();
					System.out.println(getName() + ",获取锁成功");
					System.out.println("value:"+value++);
					try {
						this.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					zookeepreLock.unlock();
					System.out.println(getName() + ",释放锁成功");
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
		long endTime= System.currentTimeMillis();
        System.out.println((endTime-startTime)/1000f);

	}
}
