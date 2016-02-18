package sp_delostrelec.IO;

import java.io.File;

/**
 *
 * @author Stepan
 */
public class FileBrowser {

	public static String[] getTerrainFileNames() {
		File f = new File("resources/terrain_data");
		File[] files = f.listFiles((File dir, String name) -> name.endsWith(".ter"));

		System.out.format("%s contains %d files. ", f.getAbsolutePath(), files.length);
		String[] s = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			s[i] = files[i].getName();
		}
		return s;
	}
}
