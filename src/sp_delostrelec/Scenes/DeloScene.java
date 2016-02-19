package sp_delostrelec.Scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Stepan
 */
public abstract class DeloScene extends Scene{

	public DeloScene(Parent root, double width, double height) {
		super(root, width, height);
	}
	
	
	public abstract String getTitle();
}
