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

	public AtomicInteger state = new AtomicInteger(0);

	private ZooKeeper zooKeeper;

	private String lock;

	private Thread owner;

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

	public boolean tryLock() {
		try {
			if(Thread.currentThread()==owner) {
				state.addAndGet(1);
				return true;
			}
			byte[] data = Thread.currentThread().getName().getBytes();
			if (zooKeeper.exists(lock, false) == null) {
				zooKeeper.create(lock, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
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

	public static void main(String[] args) {
		ReentrantZookeeperLock reentrantZookeeperLock = new ReentrantZookeeperLock("127.0.0.1", 2181, "myLock");
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread() {
				public void run() {
					reentrantZookeeperLock.lock();
					reentrantZookeeperLock.lock();
					System.out.println(getName() + ",获取锁成功");
					reentrantZookeeperLock.unlock();
					reentrantZookeeperLock.unlock();
					System.out.println(getName() + ",释放锁成功");
				}
			};
			thread.start();
		}
	}

}
