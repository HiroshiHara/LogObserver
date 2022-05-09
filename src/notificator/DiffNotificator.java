package notificator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 監視対象のファイルに差分が生じたら観察者に通知するクラス
 * @author mrbob
 *
 */
public class DiffNotificator extends Notificator {
	
	/**
	 * 監視対象のファイル
	 */
	private File g_targetFile;
	
	/**
	 * 差分比較元テキスト(常にファイル最新の内容)
	 */
	private String g_newText = "";
	
	/**
	 * 差分比較先テキスト(前回のファイルの内容)
	 */
	private String g_oldText = "";
	
	/**
	 * 差分テキスト
	 */
	private String g_diff = "";
	
	/**
	 * コンストラクタ
	 * @param x_targetFile 監視対象のファイル
	 */
	public DiffNotificator(File x_targetFile) {
		this.g_targetFile = x_targetFile;
	}
	
	/**
	 * 差分テキストを取得する
	 * @return 差分テキスト
	 */
	@Override
	public String getDiff() {
		return this.g_diff;
	}
	
	/**
	 * 差分テキストをセットする
	 * @param x_diff 差分テキスト
	 */
	public void setDiff(String x_diff) {
		this.g_diff = x_diff;
	}
	
	public void execDiff(String x_newText, String x_oldText) {
		// 初回実行時、差分比較先テキストにセットして終了
		if (this.g_oldText.isBlank()) {
			this.g_oldText = x_newText;
			return;
		}
		// 差分がない場合、差分テキストに空文字をセットして終了
		if (this.g_newText.equals(this.g_oldText)) {
			setDiff("");
			return;
		// 差分比較処理(ログが対象なので新規行のみ求める)
		} else {
			// 比較元文字列の終了位置から最新文字列の終端までを切り出し
			setDiff(g_newText.substring(g_oldText.length()+1, g_newText.length()));
			// 差分比較終了後、差分比較元を比較先に移動
			this.g_oldText = this.g_newText;
		}
		
	}
	
	@Override
	public void execute() {
		Path p_file = Paths.get(this.g_targetFile.getAbsolutePath());
		try {
			String p_text = Files.readString(p_file);
			if (!p_text.isEmpty()) {
				this.g_newText = p_text;
				execDiff(this.g_newText, this.g_oldText);
				notifyObservers();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
