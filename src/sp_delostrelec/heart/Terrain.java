package sp_delostrelec.heart;


/**
 *
 * @author Stepan
 */
public class Terrain {

	private static final int ELEV_RANGE_FIX = 1;
	
	private final int elevHighest, elevLowest;
	int width, height;
	int[][] field;

	public Terrain(int width, int height) {
		this.width = width;
		this.height = height;
		this.elevHighest = this.elevLowest = 0;
		this.field = new int[height][width];
	}

	Terrain(int[][] field, int highest, int lowest) {
		this.field = field;
		this.height = field.length;
		this.width = field[0].length;
		this.elevHighest = highest + ELEV_RANGE_FIX;
		this.elevLowest = lowest - ELEV_RANGE_FIX;
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getElevHighest() {
		return elevHighest;
	}

	public int getElevLowest() {
		return elevLowest;
	}

}
