package sp_delostrelec.Scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import sp_delostrelec.SP_Delostrelec;

/**
 *
 * @author Stepan
 */
public final class SceneManager {

	public static final int SCENE_MAIN_MENU = 1;
	public static final int SCENE_WIDTH = 800,
			SCENE_HEIGHT = 600;

	SP_Delostrelec app_instance;
	Stage stage;

	public SceneManager(SP_Delostrelec app, Stage stage) {
		this.app_instance = app;
		this.stage = stage;
		this.changeScene(SCENE_MAIN_MENU);
		stage.show();
	}

	public void changeScene(int sceneNumber) {
		DeloScene sc = choseScene(sceneNumber);
		if(sc == null){
			System.err.format("Scene %d not found.", sceneNumber);
			return;
		}
		stage.setTitle(sc.getTitle());
		stage.setScene(sc);
	}
	
	public DeloScene choseScene(int sceneNumber) {
		switch(sceneNumber){
			default: return null;
			case SCENE_MAIN_MENU: return MainMenuScene.getScene(SCENE_WIDTH, SCENE_HEIGHT);
		}
	}
}
