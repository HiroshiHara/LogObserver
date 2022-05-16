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
	File _file;
	
	/**
	 * 監視を行うConcreteSubject
	 */
	Notificator _notificator;
	
	/**
	 * 差分比較を何ミリ秒ごとに行うか
	 */
	int _milliseconds;

	/**
	 * コンストラクタ
	 * @param $file 監視対象のファイル
	 * @param $notificator 監視を行うConcreteSubject
	 */
	public ObserveThread(File $file, Notificator $notificator, int $milliseconds) {
		this._file = $file;
		this._notificator = $notificator;
		this._milliseconds = $milliseconds;
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
				this._notificator.execute();
				Thread.sleep(this._milliseconds);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
