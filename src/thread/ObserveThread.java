package thread;

import java.io.File;

import notificator.Notificator;

/**
 * ファイルの監視スレッド
 * @author mrbob
 *
 */
public class ObserveThread extends Thread {
	
	/**
	 * 監視対象のファイルオブジェクト
	 */
	File g_file;
	
	/**
	 * 監視を行うConcreteSubject
	 */
	Notificator g_notificator;

	/**
	 * コンストラクタ
	 * @param x_file 監視対象のファイル
	 * @param x_notificator 監視を行うConcreteSubject
	 */
	public ObserveThread(File x_file, Notificator x_notificator) {
		this.g_file = x_file;
		this.g_notificator = x_notificator;
	}
	
	/**
	 * ファイルの監視を開始する。<br>
	 * 3000ミリ秒につき1回、処理を行う
	 * 終了するときはCtrl + cを実行
	 */
	@Override
	public void run() {
		try {
			System.out.println("Running...");
			while(true) {
				this.g_notificator.execute();
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
