package core;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import notificator.DiffNotificator;
import notificator.Notificator;
import observer.LogObserver;
import observer.Observer;
import thread.ObserveThread;

/**
 * コマンドラインの第一引数に指定されたファイルの監視を開始する<br>
 * 指定するファイルは絶対パスで指定するか、<br>
 * カレントディレクトリにあるファイル名で指定する<br>
 * [実行コマンド] java -jar .\LogObserver.jar<br>
 * [第一引数(必須)] 監視対象ファイルの絶対パス OR カレントディレクトリにある監視対象ファイル名<br> 
 * [第二引数(任意)] 差分比較処理を何秒毎に行うか 整数で指定 未指定または数値でない場合1秒
 * @author mrbob
 *
 */
public class Main {
	/**
	 * 観察者に通知するクラス
	 */
	public static Notificator _diffNotificator;
	
	public static int _milliseconds;
	
	public static void main(String[] args) {
		if (args.length < 1 || args.length > 2) {
			throw new IllegalArgumentException("引数の数が不正です。");
		}
		if (args[0] == null) {
			throw new IllegalArgumentException("引数にファイルの絶対パスか、カレントディレクトリにある対象のファイル名を渡してください。");
		}
		if (!args[0].endsWith(".txt") && !args[0].endsWith(".log")) {
			throw new IllegalArgumentException("引数に指定するファイルの拡張子は.txtか.logとしてください。");
		}
		// 第二引数がある時
		if (args.length == 2) {
			if (!StringUtils.isBlank(args[1]) && NumberUtils.isCreatable(args[1])) {
				int milliseconds = NumberUtils.toInt(args[1], 1);
				_milliseconds = milliseconds * 1000;
			}
		}
		
		String currentDir = System.getProperty("user.dir");
		File target1 = new File(args[0]);
		File target2 = new File(currentDir + "\\" + args[0]);
		
		Thread thread = null;
		Observer _LogObserver = new LogObserver();
		
		// 指定ファイルが絶対パスに存在する場合
		if (target1.exists()) {
			_diffNotificator = new DiffNotificator(target1);
			_diffNotificator.addObserver(_LogObserver);
			thread = new ObserveThread(target1, _diffNotificator, _milliseconds);
		// 指定ファイルがカレントディレクトリに存在する場合
		} else if(target2.exists()) {
			_diffNotificator = new DiffNotificator(target2);
			_diffNotificator.addObserver(_LogObserver);
			thread = new ObserveThread(target2, _diffNotificator, _milliseconds);
		} else {
			throw new IllegalArgumentException("引数に指定されたファイルが存在しません。");
		}
		
		thread.start();
	}
}
