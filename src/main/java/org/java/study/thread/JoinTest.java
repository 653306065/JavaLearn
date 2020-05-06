package org.java.study.thread;

/**
 * join的底层原理是调用wait方法实现的，也就是启动线程的线程把当前线程当做lock对象，调用wait方法释放CPU资源和锁，
 * 在线程结束运行的时候jvm会判断当前线程是否调用join方法，通知被阻塞的线程继续运行
 * 
 *      jvm源码
 * void JavaThread::exit(bool destroy_vm, ExitType exit_type) {
    // ...

    // Notify waiters on thread object. This has to be done after exit() is called
    // on the thread (if the thread is the last thread in a daemon ThreadGroup the
    // group should have the destroyed bit set before waiters are notified).
    // 有一个贼不起眼的一行代码，就是这行
    ensure_join(this);

    // ...
}



static void ensure_join(JavaThread* thread) {
    // We do not need to grap the Threads_lock, since we are operating on ourself.
    Handle threadObj(thread, thread->threadObj());
    assert(threadObj.not_null(), "java thread object must exist");
    ObjectLocker lock(threadObj, thread);
    // Ignore pending exception (ThreadDeath), since we are exiting anyway
    thread->clear_pending_exception();
    // Thread is exiting. So set thread_status field in  java.lang.Thread class to TERMINATED.
    java_lang_Thread::set_thread_status(threadObj(), java_lang_Thread::TERMINATED);
    // Clear the native thread instance - this makes isAlive return false and allows the join()
    // to complete once we've done the notify_all below
    java_lang_Thread::set_thread(threadObj(), NULL);

    // 同志们看到了没，别的不用看，就看这一句
    // thread就是当前线程，是啥？就是刚才例子中说的threadA线程啊。
    lock.notify_all(thread);

    // Ignore pending exception (ThreadDeath), since we are exiting anyway
    thread->clear_pending_exception();
}

 * 
 * 
 * 
 * @author fuqiang
 *
 */
public class JoinTest {

	public static void main(String[] args) {
		Thread thread=new Thread() {
			public void run() {
				try {
					sleep(5000);
					System.out.println("结束休眠");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		try {
			thread.start();
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("我又可以运行了！");
	}
	
	
}
