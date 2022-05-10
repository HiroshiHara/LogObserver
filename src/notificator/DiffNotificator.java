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
	 * 改行コード(CRLF)
	 */
	private final String CRLF = "\r\n";
	
	/**
	 * 改行コード(LF)
	 */
	private final String LF = "\n";
	
	/**
	 * 改行コード(CR)
	 */
	private final String CR = "\r";
	
	
	/**
	 * コンストラクタ
	 * @param x_targetFile 監視対象のファイル
	 */
	public DiffNotificator(File x_targetFile) {
		this.g_targetFile = x_targetFile;
	}
	
	public void execDiff(String x_newText, String x_oldText) {
		// 初回実行時、差分比較先テキストにセットして終了
		if (getOldText().isBlank()) {
			setOldText(x_newText);
			return;
		}
		// 差分がない場合、差分テキストに空文字をセットして終了
		if (getNewText().equals(getOldText())) {
			setDiff("");
			return;
		// 差分比較処理(ログが対象なので新規行のみ求める)
		} else {
			// 比較元文字列の終了位置から最新文字列の終端までを切り出し
			String p_diff = getNewText().substring(getOldText().length(), getNewText().length());
			// 差分の開始が改行コード付きの場合取り除く
			if (p_diff.startsWith(CRLF)) {
				p_diff = p_diff.substring(CRLF.length());
			} else if (p_diff.startsWith(LF)) {
				p_diff = p_diff.substring(LF.length());
			} else if (p_diff.startsWith(CR)) {
				p_diff = p_diff.substring(CR.length());
			}
			setDiff(p_diff);
			// 差分比較終了後、差分比較元を比較先に移動
			setOldText(getNewText());
		}
		
	}
	
	@Override
	public void execute() {
		Path p_file = Paths.get(getTargetFile().getAbsolutePath());
		try {
			String p_text = Files.readString(p_file);
			if (!p_text.isEmpty()) {
				setNewText(p_text);
				execDiff(getNewText(), getOldText());
				notifyObservers();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 監視対象のファイルを取得する
	 * @return 監視対象のファイル
	 */
	public File getTargetFile() {
		return this.g_targetFile;
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
	
	/**
	 * 差分比較元テキスト(常にファイル最新の内容)を取得する
	 * @return 差分比較元テキスト(常にファイル最新の内容)
	 */
	public String getNewText() {
		return this.g_newText;
	}
	
	/**
	 * 差分比較先テキスト(前回のファイルの内容)をセットする
	 * @param x_newText 差分比較先テキスト(前回のファイルの内容)
	 */
	public void setNewText(String x_newText) {
		this.g_newText = x_newText;
	}
	
	/**
	 * 差分比較先テキスト(前回のファイルの内容)を取得する
	 * @return 差分比較先テキスト(前回のファイルの内容)
	 */
	public String getOldText() {
		return this.g_oldText;
	}
	
	/**
	 * 差分比較元テキスト(常にファイル最新の内容)をセットする
	 * @param x_newText 差分比較元テキスト(常にファイル最新の内容)
	 */
	public void setOldText(String x_oldText) {
		this.g_oldText = x_oldText;
	}
	
}
