package notificator;

import java.util.ArrayList;
import java.util.Iterator;

import observer.Observer;

/**
 * 観察対象の状態の変更を通知する抽象クラス
 * @author mrbob
 *
 */
public abstract class Notificator {
	
	/**
	 * この抽象クラスの実装の観察者を保持するリスト
	 */
	private ArrayList<Observer> _observers = new ArrayList<>();
	
	/**
	 * 観察者を追加する
	 * @param $observer 追加する観察者
	 */
	public void addObserver(Observer $observer) {
		_observers.add($observer);
	}
	
	/**
	 * 観察者を削除する
	 * @param $observer 削除する観察者
	 */
	public void deleteObserver(Observer $observer) {
		_observers.remove($observer);
	}
	
	/**
	 * この抽象クラスの実装の観察者全体に変更を伝達する
	 */
	public void notifyObservers() {
		Iterator<Observer> it = _observers.iterator();
		while (it.hasNext()) {
			Observer observer = it.next();
			observer.update(this);
		}
	}
	
	/**
	 * 観察対象の差分を取得する
	 * @return 差分
	 */
	public abstract String getDiff();
	
	/**
	 * 観察対象の変更の通知を実行する
	 */
	public abstract void execute();
}
