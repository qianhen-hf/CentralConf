package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ListAdd2 implements Delayed{
	private volatile static List list = new ArrayList();
	public void add() {
		list.add("bjsxt");
	}

	public int size() {
		return list.size();
	}

	ConcurrentHashMap<String, Object> aaa = null;
	
	public static void main(String[] args) {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
		ExecutorService newFixedThreadPool1 = Executors.newCachedThreadPool();
		ExecutorService newFixedThreadPool2 = Executors.newSingleThreadExecutor();
		
		
		ThreadPoolExecutor  threadPoolExecutor = null;
		
		final ListAdd2 list2 = new ListAdd2();
		final Object lock = new Object();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (lock) {
						System.out.println("t1启动..");
						for (int i = 0; i < 10; i++) {
							list2.add();
							System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
							Thread.sleep(500);
							if (list2.size() == 5) {
								System.out.println("已经发出通知..");
								lock.notify();
								lock.wait();
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}, "t1");

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					System.out.println("t2启动..");
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知线程停止..");
					lock.notify();
				}
			}
		}, "t2");
		t2.start();
		t1.start();

	}

	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}
}
