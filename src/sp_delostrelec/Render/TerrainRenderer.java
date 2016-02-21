package sp_delostrelec.Render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import sp_delostrelec.heart.Terrain;

/**
 *
 * @author Stepan
 */
public class TerrainRenderer {

	private Terrain terrain;
	private int terrainCols, terrainRows;

	private int blockSize;
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
		blockSize = (int) Math.min(canvasWidth / terrainCols, canvasHeight / terrainRows);
	}

	public void render(GraphicsContext g, IColorPicker colorPicker) {
		render(g, colorPicker, false);
	}
	
	public void render(GraphicsContext g, IColorPicker colorPicker, boolean scaleToFit) {
		int height;
		g.setFill(Color.PINK);
		g.fillRect(0, 0, canvasWidth, canvasHeight);

		Affine defAffine = g.getTransform();

		if (!scaleToFit) {
			double dX = (canvasWidth - terrainCols * blockSize) / 2;
			double dY = (canvasHeight - terrainRows * blockSize) / 2;
			g.translate(dX, dY);
		} else {
			double scale = Math.min(canvasWidth / (terrainCols * blockSize) ,
					canvasHeight / (terrainCols * blockSize));
			double dX = (canvasWidth - terrainCols * blockSize * scale) / 2;
			double dY = (canvasHeight - terrainRows * blockSize * scale) / 2;
			g.scale(scale, scale);
			g.translate(dX / scale, dY / scale);
		}
		for (int row = 0; row < terrainRows; row++) {
			for (int col = 0; col < terrainCols; col++) {
				height = this.terrain.getField(col, row);
				g.setFill(colorPicker.getColor(height));
				g.fillRect(col * blockSize, row * blockSize, blockSize, blockSize);
			}
		}

		g.setTransform(defAffine);
	}

	public int heightOn(double normX, double normY) {
		int physX = (int) Math.round(normX * this.terrain.getWidth());
		int physY = (int) Math.round(normY * this.terrain.getHeight());
		return this.terrain.getField(physX, physY);
	}
}
