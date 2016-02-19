package sp_delostrelec.heart;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Stepan
 */
public class Warzone {

	private static Terrain loadTerrain(DataInputStream dis, int width, int height) throws IOException {
		int highest = Integer.MIN_VALUE,
				lowest = Integer.MAX_VALUE;
		int currentHeight;
		int[][] field = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				currentHeight = field[y][x] = dis.readInt();
				if (currentHeight > highest) {
					highest = currentHeight;
				}
				if (currentHeight < lowest) {
					lowest = currentHeight;
				}
			}
		}
		return new Terrain(field, highest, lowest);
	}

	private static Actor loadActor(DataInputStream dis, Class cl) throws IOException, InstantiationException {
		try {
			int x = dis.readInt(), y = dis.readInt();
			return (Actor) cl.getConstructor(Integer.TYPE, Integer.TYPE).newInstance(x, y);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new InstantiationException(ex.getMessage());
		}

	}

	public static Warzone loadFromFile(File file) {

		try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
			int width = dis.readInt(), height = dis.readInt();

			Shooter sho = (Shooter) loadActor(dis, Shooter.class);
			Target tar = (Target) loadActor(dis, Target.class);
			
			Terrain ter = loadTerrain(dis, width, height);
			return new Warzone(ter, sho, tar);

		} catch (IOException e) {
			System.err.println("Failed reading file for warzone data:\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
			return null;
		} catch (InstantiationException ex) {
			System.err.println("Couldn't create instance of Shooter and Target:\n" + ex.getMessage());
			return null;
		}
	}

	Terrain terrain;
	Shooter shooter;
	Target target;

	public Warzone(Terrain terrain, Shooter shooter, Target target) {
		this.terrain = terrain;
		this.shooter = shooter;
		this.target = target;
	}

	public Terrain getTerrain() {
		return this.terrain;
	}

}
