package org.java.study.lock;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

	private static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			final  int t=i;
			new Thread(() -> {
				try {
					Thread.sleep(t*1000);
					cyclicBarrier.await();
					System.out.println(System.currentTimeMillis());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
}
