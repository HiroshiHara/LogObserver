package notificator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

/**
 * 監視対象のファイルに差分が生じたら観察者に通知するクラス
 * @author mrbob
 *
 */
public class DiffNotificator extends Notificator {
	
	/**
	 * 監視対象のファイル
	 */
	private File _targetFile;
	
	/**
	 * 差分比較元テキスト(常にファイル最新の内容)
	 */
	private String _newText = "";
	
	/**
	 * 差分比較先テキスト(前回のファイルの内容)
	 */
	private String _oldText = "";
	
	/**
	 * 差分テキスト
	 */
	private String _diff = "";
	
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
	 * @param $targetFile 監視対象のファイル
	 */
	public DiffNotificator(File $targetFile) {
		this._targetFile = $targetFile;
	}
	
	/**
	 * 差分比較処理を実行する<br>
	 * 比較元と新規ログの差分がある場合、<br>
	 * その差分のみを保存する。
	 * @param $newText
	 * @param $oldText
	 */
	public void execDiff(String $newText, String $oldText) {
		// 初回実行時、差分比較先テキストにセットして終了
		if (StringUtils.isBlank(getOldText())) {
			setOldText($newText);
			return;
		}
		// 差分がない場合、差分テキストに空文字をセットして終了
		if (getNewText().equals(getOldText())) {
			setDiff("");
			return;
		// 差分比較処理(ログが対象なので新規行のみ求める)
		} else {
			// 比較元文字列の終了位置から最新文字列の終端までを切り出し
			String diff = getNewText().substring(getOldText().length(), getNewText().length());
			// 差分の開始が改行コード付きの場合取り除く
			if (diff.startsWith(CRLF)) {
				diff = diff.substring(CRLF.length());
			} else if (diff.startsWith(LF)) {
				diff = diff.substring(LF.length());
			} else if (diff.startsWith(CR)) {
				diff = diff.substring(CR.length());
			}
			setDiff(diff);
			// 差分比較終了後、差分比較元を比較先に移動
			setOldText(getNewText());
		}
		
	}
	
	/**
	 * ファイルの内容を読み取り、差分比較処理を呼び出す。<br>
	 * 差分比較後、監視者に通知を行う。
	 */
	@Override
	public void execute() {
		Path file = Paths.get(getTargetFile().getAbsolutePath());
		try {
			String text = Files.readString(file);
			if (!StringUtils.isBlank(text)) {
				setNewText(text);
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
		return this._targetFile;
	}
	
	/**
	 * 差分テキストを取得する
	 * @return 差分テキスト
	 */
	@Override
	public String getDiff() {
		return this._diff;
	}
	
	/**
	 * 差分テキストをセットする
	 * @param $diff 差分テキスト
	 */
	public void setDiff(String $diff) {
		this._diff = $diff;
	}
	
	/**
	 * 差分比較元テキスト(常にファイル最新の内容)を取得する
	 * @return 差分比較元テキスト(常にファイル最新の内容)
	 */
	public String getNewText() {
		return this._newText;
	}
	
	/**
	 * 差分比較先テキスト(前回のファイルの内容)をセットする
	 * @param $newText 差分比較先テキスト(前回のファイルの内容)
	 */
	public void setNewText(String $newText) {
		this._newText = $newText;
	}
	
	/**
	 * 差分比較先テキスト(前回のファイルの内容)を取得する
	 * @return 差分比較先テキスト(前回のファイルの内容)
	 */
	public String getOldText() {
		return this._oldText;
	}
	
	/**
	 * 差分比較元テキスト(常にファイル最新の内容)をセットする
	 * @param $newText 差分比較元テキスト(常にファイル最新の内容)
	 */
	public void setOldText(String $oldText) {
		this._oldText = $oldText;
	}
	
}
