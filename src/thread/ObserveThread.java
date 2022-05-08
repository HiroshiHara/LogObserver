package thread;

import java.io.File;

import notificator.DiffNotificator;
import notificator.Notificator;
import observer.LogObserver;
import observer.Observer;

public class ObserveThread extends Thread {
	
	/**
	 * 監視対象のファイルオブジェクト
	 */
	File g_file;

	/**
	 * コンストラクタ
	 * @param x_file 監視対象のファイル
	 */
	public ObserveThread(File x_file) {
		this.g_file = x_file;
	}
	
	/**
	 * ファイルの監視を開始する。<br>
	 * 終了するときはCtrl + cを実行
	 */
	@Override
	public void run() {
		try {
			System.out.println("Running...");
			while(true) {
				Observer g_LogObserver = new LogObserver();
				Notificator g_diffNotificator = new DiffNotificator(g_file);
				g_diffNotificator.addObserver(g_LogObserver);
				g_diffNotificator.execute();
				Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			System.out.println("End");
		}
	}
}
