package sp_delostrelec.Render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sp_delostrelec.heart.Terrain;

/**
 *
 * @author Stepan
 */
public class TerrainRenderer {

	private static final Color[] elevationColors;

	static {
		elevationColors = new Color[]{
			Color.DODGERBLUE, Color.BLANCHEDALMOND, Color.DARKGOLDENROD, Color.DARKGREEN, Color.DARKGRAY, Color.SNOW};
	}

	private Terrain terrain;
	private int terrainCols, terrainRows;
	private final int[] colorBorders;
	private Color[] individualColors;

	private int blockWidth, blockHeight;
	private double canvasWidth, canvasHeight;

	public TerrainRenderer(double canvasWidth, double canvasHeight) {
		this(null, canvasWidth, canvasHeight);
	}

	public TerrainRenderer(Terrain t, double canvasWidth, double canvasHeight) {
		this.terrain = t;
		this.colorBorders = new int[elevationColors.length];
		if (t != null) {
			this.prepareColors();
		}
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
		this.terrainCols = this.terrain.getWidth();
		this.terrainRows = this.terrain.getHeight();
		this.prepareColors();
		this.prepareRenderSizes();
	}

	private void prepareRenderSizes() {
		blockWidth = (int) (canvasWidth / terrainCols);
		blockHeight = (int) (canvasHeight / terrainRows);
	}

	private void prepareColors() {
		int minHeight = terrain.getElevLowest();
		int elevRange = terrain.getElevHighest() - minHeight;

		int step = (int) Math.ceil(elevRange * 1.0 / elevationColors.length);
		for (int i = 0; i < this.colorBorders.length; i++) {
			this.colorBorders[i] = minHeight + step * i;
		}
		this.individualColors = new Color[elevRange + 1];
	}

	public void render(GraphicsContext g) {
		g.setFill(Color.PINK);
		g.fillRect(0, 0, canvasWidth, canvasHeight);
		for (int row = 0; row < terrainRows; row++) {
			for (int col = 0; col < terrainCols; col++) {
				g.setFill(getColor(this.terrain.getField(col, row)));
				g.fillRect(col * blockWidth, row * blockHeight, blockWidth, blockHeight);
			}
		}
	}

	public Color getColor(int height) {
		Color c = this.individualColors[height - this.terrain.getElevLowest()];
		if (c != null) {
			return c;
		}

		int lowBorder = 0;
		while (height > colorBorders[lowBorder + 1]) {
			++lowBorder;
			//System.out.format("Height %d has new lew lowborder[%d]: %d\n", height, lowBorder, colorBorders[lowBorder]);
			if (lowBorder >= colorBorders.length - 1) {
				//System.out.println("Height overflow, returning highest color");
				return elevationColors[colorBorders.length - 1];
			}
		}
		//System.out.format("Height: %03d is in lb:%d <%03d, %03d>\n", height, lowBorder, colorBorders[lowBorder], colorBorders[lowBorder + 1]);
		float pctA = (height - colorBorders[lowBorder]) * 1.0f
				/ Math.max(colorBorders[lowBorder + 1] - colorBorders[lowBorder], 1);
		float pctB = 1 - pctA;

		System.out.format("Height: %03d is in lb:%d <%03d, %03d>, pctA: %.2f\n", height, lowBorder, colorBorders[lowBorder], colorBorders[lowBorder + 1], pctB);

		return this.individualColors[height - this.terrain.getElevLowest()] = new Color(
				pctB * elevationColors[lowBorder].getRed() + pctA * elevationColors[lowBorder + 1].getRed(),
				pctB * elevationColors[lowBorder].getGreen() + pctA * elevationColors[lowBorder + 1].getGreen(),
				pctB * elevationColors[lowBorder].getBlue() + pctA * elevationColors[lowBorder + 1].getBlue(), 1);
	}

	public int heightOn(double normX, double normY) {
		int physX = (int) Math.round(normX * this.terrain.getWidth());
		int physY = (int) Math.round(normY * this.terrain.getHeight());
		return this.terrain.getField(physX, physY);
	}
}
