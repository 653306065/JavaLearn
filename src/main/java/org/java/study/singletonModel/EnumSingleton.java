package org.java.study.singletonModel;

/**
 * 枚举的单例模式
 * @author 付强
 *
 */
public class EnumSingleton {

	private EnumSingleton() {

	}

	/**
	 * 静态内部只有调用的时候才会测试(延迟加载)
	 * @author 付强
	 *
	 */
	private enum Enum {

		Singleton;

		private EnumSingleton enumSingleton=null;

        /**
         * 调用枚举实例时会，调用构造函数
         */
		Enum() {
			enumSingleton = new EnumSingleton();
		}
	}

	public static EnumSingleton getInstance() {
		return Enum.Singleton.enumSingleton;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			new Thread() {
				public void run() {
					System.out.println(getInstance());
				}
			}.start();
		}
	}
}
