package core;

import java.io.File;

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
			p_thread = new ObserveThread(p_target1);
		} else if(p_target2.exists()) {
			p_thread = new ObserveThread(p_target2);
		} else {
			throw new IllegalArgumentException("引数に指定されたファイルが存在しません。");
		}
		
		p_thread.start();
	}
}
