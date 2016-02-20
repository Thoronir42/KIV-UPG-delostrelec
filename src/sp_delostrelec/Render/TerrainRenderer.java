package sp_delostrelec.Render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sp_delostrelec.heart.Terrain;

/**
 *
 * @author Stepan
 */
public class TerrainRenderer {

	private Terrain terrain;
	private int terrainCols, terrainRows;

	private int blockWidth, blockHeight;
	private double canvasWidth, canvasHeight;

	public TerrainRenderer(double canvasWidth, double canvasHeight) {
		this(null, canvasWidth, canvasHeight);
	}

	public TerrainRenderer(Terrain t, double canvasWidth, double canvasHeight) {
		this.terrain = t;
		
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
		this.terrainCols = this.terrain.getWidth();
		this.terrainRows = this.terrain.getHeight();
		
		this.prepareRenderSizes();
	}

	private void prepareRenderSizes() {
		blockWidth = (int) (canvasWidth / terrainCols);
		blockHeight = (int) (canvasHeight / terrainRows);
	}

	

	public void render(GraphicsContext g, IColorPicker colorPicker) {
		int height;
		g.setFill(Color.PINK);
		g.fillRect(0, 0, canvasWidth, canvasHeight);
		for (int row = 0; row < terrainRows; row++) {
			for (int col = 0; col < terrainCols; col++) {
				height = this.terrain.getField(col, row);
				g.setFill(colorPicker.getColor(height));
				g.fillRect(col * blockWidth, row * blockHeight, blockWidth, blockHeight);
			}
		}
	}

	public int heightOn(double normX, double normY) {
		int physX = (int) Math.round(normX * this.terrain.getWidth());
		int physY = (int) Math.round(normY * this.terrain.getHeight());
		return this.terrain.getField(physX, physY);
	}
}
