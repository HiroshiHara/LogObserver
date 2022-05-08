package observer;

import notificator.Notificator;

public class LogObserver implements Observer {
	
	/**
	 * 数値が生成されるたびにコンソールにその数値を「数字」で表示する
	 */
	@Override
	public void update(Notificator x_nottificator) {
		System.out.println("LogObserver:" + x_nottificator.getDiff());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
