package sp_delostrelec;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sp_delostrelec.IO.FileBrowser;

/**
 *
 * @author Stepan
 */
public class SP_Delostrelec extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		String[] s = FileBrowser.getTerrainFileNames();
		Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction((ActionEvent event) -> {
			System.out.println("Hello World!");
		});
		
		StackPane root = new StackPane();
		root.getChildren().add(btn);
		
		Scene scene = new Scene(root, 300, 250);
		
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
