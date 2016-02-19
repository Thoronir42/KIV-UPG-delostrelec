package sp_delostrelec;

import javafx.application.Application;
import javafx.stage.Stage;
import sp_delostrelec.Scenes.SceneManager;

/**
 *
 * @author Stepan
 */
public class SP_Delostrelec extends Application {
	
	private Stage stage;
	private SceneManager sceneManager;
	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		this.sceneManager = new SceneManager(this, this.stage);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
