package test;

public class ProducerConsumer {
	public static void main(String[] args) {

		final MessageQueue mq = new MessageQueue(10);
		// 创建3个生产者
		for (int p = 0; p < 3; p++) {

			new Thread(new Runnable() {

				@Override
				public void run() {

					while (true) {

						mq.put("消息来了！");
						// 生产消息后，休息100毫秒
						try {
							Thread.currentThread().sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}, "Producer" + p).start();
		}

		// 创建3个消费者
		for (int s = 0; s < 3; s++) {

			new Thread(new Runnable() {

				@Override
				public void run() {

					while (true) {

						mq.get();
						// 消费消息后，休息100毫秒
						try {
							Thread.currentThread().sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}, "Consumer" + s).start();
		}
	}

	/**
	 * 内部类模拟一个消息队列，生产者和消费者就去操作这个消息队列
	 */
	private static class MessageQueue {

		private String[] messages;// 放置消息的数据结构
		private int opIndex; // 将要操作的位置索引

		public MessageQueue(int size) {

			if (size <= 0) {

				throw new IllegalArgumentException("消息队列的长度至少为1！");
			}
			messages = new String[size];
			opIndex = 0;
		}

		public synchronized void put(String message) {

			// Java中存在线程假醒的情况，此处用while而不是用if！可以参考Java规范！
			while (opIndex == messages.length) {

				// 消息队列已满，生产者需要等待
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			messages[opIndex] = message;
			opIndex++;
			System.out.println("生产者 " + Thread.currentThread().getName() + " 生产了一条消息: " + message);
			// 生产后，对消费者进行唤醒
			notifyAll();
		}

		public synchronized String get() {

			// Java中存在线程假醒的情况，此处用while而不是用if！可以参考Java规范！
			while (opIndex == 0) {

				// 消息队列无消息，消费者需要等待
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String message = messages[opIndex - 1];
			opIndex--;
			System.out.println("消费者 " + Thread.currentThread().getName() + " 消费了一条消息: " + message);
			// 消费后，对生产者进行唤醒
			notifyAll();
			return message;
		}

	}
}
