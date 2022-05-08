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
	private ArrayList<Observer> g_observers = new ArrayList<>();
	
	/**
	 * 観察者を追加する
	 * @param x_observer 追加する観察者
	 */
	public void addObserver(Observer x_observer) {
		g_observers.add(x_observer);
	}
	
	/**
	 * 観察者を削除する
	 * @param x_observer 削除する観察者
	 */
	public void deleteObserver(Observer x_observer) {
		g_observers.remove(x_observer);
	}
	
	/**
	 * この抽象クラスの実装の観察者全体に変更を伝達する
	 */
	public void notifyObservers() {
		Iterator<Observer> p_it = g_observers.iterator();
		while (p_it.hasNext()) {
			Observer p_observer = p_it.next();
			p_observer.update(this);
		}
	}
	
	/**
	 * 観察対象の変更の差分を取得する
	 * @return 変更の差分
	 */
	public abstract String getDiff();
	
	/**
	 * 観察対象の変更の通知を実行する
	 */
	public abstract void execute();
}
