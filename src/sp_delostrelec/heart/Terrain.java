package sp_delostrelec.heart;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.midi.SysexMessage;

/**
 *
 * @author Stepan
 */
public class Terrain {

	public static Terrain loadFromFile(File file) {
		try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
			int width = dis.readInt(),
					height = dis.readInt();
			int[][] field = new int[height][width];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					field[y][x] = dis.readInt();
				}
			}
			return new Terrain(field);
		} catch (IOException e) {
			System.err.println("Failed reading file for terrain data:\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
			return null;
		}
	}

	int width, height;
	int[][] field;

	public Terrain(int width, int height) {
		this.width = width;
		this.height = height;
		this.field = new int[height][width];
	}

	private Terrain(int[][] field) {
		this.field = field;
		this.height = field.length;
		this.width = field[0].length;
	}

	private void checkXY(int x, int y) throws IllegalArgumentException {
		if (x < 0 || x > this.width) {
			throw new IllegalArgumentException("X " + x + " is out of bounds");
		}
		if (y < 0 || y > this.height) {
			throw new IllegalArgumentException("Y " + y + " is out of bounds");
		}
	}

	public void setField(int x, int y, int value) {
		this.checkXY(x, y);
		this.field[y][x] = value;
	}

	public int getField(int x, int y) {
		this.checkXY(x, y);
		return this.field[y][x];

	}
}
