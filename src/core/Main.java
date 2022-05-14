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
	public static Notificator g_diffNotificator;
	
	public static int g_milliseconds;
	
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
				int p_milliseconds = NumberUtils.toInt(args[1], 1);
				g_milliseconds = p_milliseconds * 1000;
			}
		}
		
		String p_currentDir = System.getProperty("user.dir");
		File p_target1 = new File(args[0]);
		File p_target2 = new File(p_currentDir + "\\" + args[0]);
		
		Thread p_thread = null;
		Observer g_LogObserver = new LogObserver();
		
		// 指定ファイルが絶対パスに存在する場合
		if (p_target1.exists()) {
			g_diffNotificator = new DiffNotificator(p_target1);
			g_diffNotificator.addObserver(g_LogObserver);
			p_thread = new ObserveThread(p_target1, g_diffNotificator, g_milliseconds);
		// 指定ファイルがカレントディレクトリに存在する場合
		} else if(p_target2.exists()) {
			g_diffNotificator = new DiffNotificator(p_target2);
			g_diffNotificator.addObserver(g_LogObserver);
			p_thread = new ObserveThread(p_target2, g_diffNotificator, g_milliseconds);
		} else {
			throw new IllegalArgumentException("引数に指定されたファイルが存在しません。");
		}
		
		p_thread.start();
	}
}
