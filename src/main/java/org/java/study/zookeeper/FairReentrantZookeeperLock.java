package org.java.study.zookeeper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;

/**
 * 公平的可重入的zookeeper分布式锁
 * 
 * @author fuqiang
 *
 */
public class FairReentrantZookeeperLock {

	public AtomicInteger state = new AtomicInteger(0);// 状态标识

	private ZooKeeper zooKeeper;

	private String lock;// 锁的字符串

	private Thread owner;// 当前占有线程

	private ThreadLocal<String> threadLocal = new ThreadLocal<String>();// 保存线程的节点顺序

	public FairReentrantZookeeperLock(String host, int port, String lock) {
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
		if (!tryLock()) {// 获取锁
			while (true) {// 自旋
				await();// 等待通知
				if (tryLock()) {
					break;
				}
			}
		}
	}

	public void await() {
		try {
			String sequence = threadLocal.get();
			String indexStr = sequence.split(lock)[1];
			int index = Integer.valueOf(indexStr) - 1;// 上一个的sequence
			String text = "";
			while (true) {
				text = text + "0";
				if (text.length() + String.valueOf(index).length() == 10) {
					break;
				}
			}
			String previous = lock + text + index;
			CountDownLatch countDownLatch = new CountDownLatch(1);
			if (zooKeeper.exists(previous, false) != null) {
				zooKeeper.addWatch(previous, new Watcher() {// 监听上一个节点的删除事件
					@Override
					public void process(WatchedEvent event) {
						if (event.getType() == EventType.NodeDeleted) {
							countDownLatch.countDown();
						}
					}

				}, AddWatchMode.PERSISTENT);
				countDownLatch.await();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean tryLock() {
		try {
			if (Thread.currentThread() == owner) {// 重入性
				state.incrementAndGet();
				return true;
			}
			byte[] data = Thread.currentThread().getName().getBytes();
			String value = threadLocal.get();
			if (value == null) {// 如果是第一次进来，就创建排队的节点
				String sequence = zooKeeper.create(lock, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
				threadLocal.set(sequence);// 保存到threadLocal中
				value = sequence;
			}
			List<String> list = zooKeeper.getEphemerals(lock);
			Collections.sort(list);
			if (list.get(0).equals(value)) {// 如果当前节点是在临时节点的第一个就获得锁
				owner = Thread.currentThread();
				state.addAndGet(1);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public String getSequence() {
		return this.threadLocal.get();
	}

	public void unlock() {
		try {
			if (Thread.currentThread() == owner && state.decrementAndGet() == 0) {
				String value = threadLocal.get();
				zooKeeper.delete(value, -1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int value = 0;

	public static void main(String[] args) {
		FairReentrantZookeeperLock fairReentrantZookeeperLock = new FairReentrantZookeeperLock("127.0.0.1", 2181,
				"wd");
		for (int i = 0; i < 100; i++) {
			Thread thrad = new Thread() {
				public void run() {
					fairReentrantZookeeperLock.lock();
					fairReentrantZookeeperLock.lock();
					System.out.println(fairReentrantZookeeperLock.getSequence());
					System.out.println(value++);
					fairReentrantZookeeperLock.unlock();
					fairReentrantZookeeperLock.unlock();
				}
			};
			thrad.start();
		}
	}
}
