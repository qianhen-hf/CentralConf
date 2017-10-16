package test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyQueue {
	private LinkedList<Object> linkedList = new LinkedList<Object>();
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private AtomicInteger atomic = new AtomicInteger(0);
	private Queue q = new  LinkedBlockingDeque<String>();
	private final int maxSize;
	private final int minSize = 0;
 
	public MyQueue(int maxSize) {
		this.maxSize = maxSize;
	}

	public void put(Object obj) {
		try {
			lock.lock();
			if (this.maxSize == atomic.get()) {
				try {
					System.out.println("添加元素等待");
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			linkedList.add(obj);
			atomic.incrementAndGet();
			condition.signal();
			System.out.println("添加元素" + obj);
		} finally {
			lock.unlock();
		}
	}

	public Object take() {
		Object object = null;
		try {
			lock.lock();
			if (atomic.get() == minSize) {
				try {
					System.out.println("移出元素等待");
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			object = linkedList.removeFirst();
			atomic.decrementAndGet();
			System.out.println("移出元素" + object);
			condition.signal();
		} finally {
			lock.unlock();
		}
		return object;
	}

	public static void main(String[] args) {
		
		//当队为空
		final MyQueue my = new MyQueue(5);
//		Thread t1 = new Thread(new Runnable() {
//			public void run() {
//				my.take();
//			}
//		}, "t1");
//		t1.start();
//		Thread t2 = new Thread(new Runnable() {
//			public void run() {
//				my.put("222");
//			}
//		}, "t2");
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		t2.start();
		
		//当队满
		
		my.put("a");
		my.put("b");
		my.put("c");
		my.put("d");
		my.put("e");
		Thread t3 = new Thread(new Runnable() {
			public void run() {
				my.put("f");
			}
		}, "t3");
		t3.start();
		Thread t4 = new Thread(new Runnable() {
			public void run() {
				my.take();
			}
		}, "t4");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t4.start();
	}
}
