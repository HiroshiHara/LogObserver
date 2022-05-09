package observer;

import notificator.Notificator;

/**
 * 監視対象のログに変更が生じた場合、その差分をコンソールに表示する
 * @author mrbob
 *
 */
public class LogObserver implements Observer {
	
	/**
	 * 監視対象のログに変更が生じた場合、その差分をコンソールに表示する
	 */
	@Override
	public void update(Notificator x_nottificator) {
		String p_diff = x_nottificator.getDiff();
		if (!p_diff.isBlank()) {
			System.out.println("-------- new lines --------");
			System.out.println(p_diff);
		}
	}
}
