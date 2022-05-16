package observer;

import org.apache.commons.lang3.StringUtils;

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
	public void update(Notificator $nottificator) {
		String diff = $nottificator.getDiff();
		if (!StringUtils.isBlank(diff)) {
			System.out.println("-------- new lines --------");
			System.out.println(diff);
		}
	}
}
