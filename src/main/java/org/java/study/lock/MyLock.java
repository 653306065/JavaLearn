package org.java.study.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/**
 * AQS是自定义锁的模板，利用多个线程对一个原子资源(int)(CAS)的访问，控制并发，当第一个线程获取锁，
 * 会对共享的原子资源赋值(+1)，其他线程获取锁会进入一个双向的虚拟等待队列进行等待(unsafa.park),
 * 等待持有锁资源的线程释放锁，当线程释锁会加原子资源恢复到之前的模样(-1),其他线程会去竞争，
 * 竞争又分为公平和非公平，非公平是指等待队列里面的任何一个线程都有可能获取到锁，公平的是指根据先进先出的原则，
 * 把队列里面的第一个线程获取到锁，就是在调用lock方法的时候，判断当前线程是否在队列里面的第一个，
 * 锁的重入性指的是同一个线程的执行过程中可以多次调用lock方法进行赋值，也需要进行多次的调用unlock进行解锁
 * @author 付强
 *
 */
public class MyLock implements Lock {

	private LockHelper lockHelper = new LockHelper();

	private static class LockHelper extends AbstractQueuedSynchronizer {

		private static final long serialVersionUID = 1L;
		
		protected boolean tryAcquire(int arg) {
			if (getState() == 0 && compareAndSetState(0, arg)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}else if(Thread.currentThread()==getExclusiveOwnerThread()){//重入性
				setState(getState()+arg);
				return true;
			}
			return false;
		}

		protected boolean tryRelease(int arg) {
           if(getExclusiveOwnerThread()==Thread.currentThread()) {
        	   int state=getState()-arg;
        	   if(state==0) {
        	 	   setState(state);
            	   setExclusiveOwnerThread(null);
            	   return true;
        	   }else {//可重入性
        		   setState(state);
        		   return false; 
        	   }
           }
		   return false;
		}
	}

	@Override
	public void lock() {
		lockHelper.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {

	}

	@Override
	public boolean tryLock() {
		return lockHelper.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return lockHelper.tryAcquire(1);
	}

	@Override
	public void unlock() {
		lockHelper.release(1);
	}

	@Override
	public Condition newCondition() {
		return null;
	}
	
	
	public static int num=0;
	
	public static void main(String[] args) {
		MyLock myLock=new MyLock();
		for(int i=0;i<20;i++) {
			Thread thread=new Thread("thread-"+i) {
				public void run() {
					myLock.lock();//重入性
					myLock.lock();
					num++;
					System.out.println(getName()+",获取锁成功");
					System.out.println(getName()+",释放锁");
					myLock.unlock();
					myLock.unlock();

					System.out.println(num);
				}
			};
			thread.start();
		}
	}

}
