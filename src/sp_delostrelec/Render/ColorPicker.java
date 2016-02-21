package sp_delostrelec.Render;

import javafx.scene.paint.Color;
import sp_delostrelec.heart.Terrain;

/**
 *
 * @author Stepan
 */
public class ColorPicker {

	public static class Monochromatic implements IColorPicker {

		Terrain terrain;
		Color c;

		public Monochromatic(Terrain t, Color c) {
			this.terrain = t;
			this.c = c;
		}

		@Override
		public Color getColor(int height) {
			int top = terrain.getElevHighest();
			int bot = terrain.getElevLowest();
			float pctA = (height - bot) * 1.0f / (top - bot);

			return new Color(c.getRed() * pctA, c.getGreen() * pctA, c.getBlue() * pctA, 1);

		}
	}

	public static class ColorSequence implements IColorPicker {

		private Terrain terrain;

		private final Color[] elevationColors;

		private final int[] colorBorders;
		private Color[] individualColors;

		public ColorSequence(Terrain t) {
			this.terrain = t;
			elevationColors = new Color[]{Color.DODGERBLUE, Color.BLANCHEDALMOND, Color.DARKGOLDENROD, Color.DARKGREEN, Color.DARKGRAY, Color.SNOW};
			this.colorBorders = new int[elevationColors.length];
			this.prepareColors();
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

		@Override
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

			//System.out.format("Height: %03d is in lb:%d <%03d, %03d>, pctA: %.2f\n", height, lowBorder, colorBorders[lowBorder], colorBorders[lowBorder + 1], pctB);

			return this.individualColors[height - this.terrain.getElevLowest()] = new Color(
					pctB * elevationColors[lowBorder].getRed() + pctA * elevationColors[lowBorder + 1].getRed(),
					pctB * elevationColors[lowBorder].getGreen() + pctA * elevationColors[lowBorder + 1].getGreen(),
					pctB * elevationColors[lowBorder].getBlue() + pctA * elevationColors[lowBorder + 1].getBlue(), 1);
		}

	}

}
