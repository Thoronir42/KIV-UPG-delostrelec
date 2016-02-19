package sp_delostrelec.IO;

import java.io.File;

/**
 *
 * @author Stepan
 */
public class FileBrowser {
	private static final String FOLDER_TERRAINS = "resources/terrain_data";
	
	
	public static String[] getTerrainFileNames() {
		File f = new File(FOLDER_TERRAINS);
		File[] files = f.listFiles((File dir, String name) -> name.endsWith(".ter"));

		System.out.format("%s contains %d files.\n", f.getAbsolutePath(), files.length);
		String[] s = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			s[i] = files[i].getName();
		}
		return s;
	}

	public static File openTerrainFile(String newValue) {
		File f = new File(FOLDER_TERRAINS+"/"+newValue);
		if(!f.exists()){
			return null;
		}
		return f;
	}
}
