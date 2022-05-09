package core;

import java.io.File;

import notificator.DiffNotificator;
import notificator.Notificator;
import observer.LogObserver;
import observer.Observer;
import thread.ObserveThread;

/**
 * コマンドラインの第一引数に指定されたファイルの監視を開始する<br>
 * 指定するファイルは絶対パスで指定するか、<br>
 * カレントディレクトリにあるファイル名で指定する
 * @author mrbob
 *
 */
public class Main {
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("引数にファイルの絶対パスか、カレントディレクトリにある対象のファイル名を渡してください。");
		}
		if (!args[0].endsWith(".txt") && !args[0].endsWith(".log")) {
			throw new IllegalArgumentException("引数に指定するファイルの拡張子は.txtか.logとしてください。");
		}
		String p_currentDir = System.getProperty("user.dir");
		File p_target1 = new File(args[0]);
		File p_target2 = new File(p_currentDir + "\\" + args[0]);
		
		Thread p_thread = null;
		if (p_target1.exists()) {
			Notificator p_diffNotificator = new DiffNotificator(p_target1);
			Observer g_LogObserver = new LogObserver();
			p_diffNotificator.addObserver(g_LogObserver);
			p_thread = new ObserveThread(p_target1, p_diffNotificator);
		} else if(p_target2.exists()) {
			Notificator p_diffNotificator = new DiffNotificator(p_target2);
			Observer g_LogObserver = new LogObserver();
			p_diffNotificator.addObserver(g_LogObserver);
			p_thread = new ObserveThread(p_target2, p_diffNotificator);
		} else {
			throw new IllegalArgumentException("引数に指定されたファイルが存在しません。");
		}
		
		p_thread.start();
	}
}
