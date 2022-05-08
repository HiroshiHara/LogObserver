package observer;

import notificator.Notificator;

/**
 * 「観察者」を表現するインタフェース
 * @author mrbob
 *
 */
public interface Observer {
	/**
	 * 観察者に情報の更新を伝達する抽象メソッド
	 * @param x_notificator 情報の更新を通知するオブジェクト
	 */
	public abstract void update(Notificator x_notificator);
}
