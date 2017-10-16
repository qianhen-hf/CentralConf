package test;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest implements Runnable {
	
	private static  AtomicInteger a = new AtomicInteger(0);

	public void count() {
		for (int i = 0; i < 1000; i++) {
			a.incrementAndGet();
		}
		System.out.println(a);
	}

	public void run() {
		count();
	}
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			new Thread(new VolatileTest()).start();
		}
	}

}
