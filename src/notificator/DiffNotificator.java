package notificator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DiffNotificator extends Notificator {
	
	/**
	 * 観察対象のファイル
	 */
	private File g_targetFile;
	
	public DiffNotificator(File x_targetFile) {
		this.g_targetFile = x_targetFile;
	}
	
	@Override
	public String getDiff() {
		return "test";
	}
	
	@Override
	public void execute() {
		Path p_file = Paths.get(g_targetFile.getAbsolutePath());
		try {
			String p_text = Files.readString(p_file);
			if (!p_text.isEmpty()) {
				notifyObservers();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
