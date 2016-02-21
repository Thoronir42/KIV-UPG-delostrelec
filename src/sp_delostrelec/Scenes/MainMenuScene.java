package sp_delostrelec.Scenes;

import java.io.File;
import java.util.Arrays;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import sp_delostrelec.IO.FileBrowser;
import sp_delostrelec.Render.ColorPicker;
import sp_delostrelec.Render.TerrainRenderer;
import sp_delostrelec.heart.Terrain;
import sp_delostrelec.heart.Warzone;

/**
 *
 * @author Stepan
 */
public class MainMenuScene extends DeloScene {

	private static final double MENU_HEIGHT = 520;

	public static MainMenuScene getScene(double width, double height) {
		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);

		MainMenuScene scene = new MainMenuScene(root, width, height);
		fillPane(root, scene);

		return scene;
	}

	private ListView<String> lw_terrainList;
	private Button btn_start;
	private Button btn_end;
	private Canvas can_preview;

	private final TerrainRenderer tRend;

	private MainMenuScene(Parent parent, double width, double height) {
		super(parent, width, height);
		this.tRend = new TerrainRenderer(MENU_HEIGHT, MENU_HEIGHT);
	}

	private static GridPane fillPane(GridPane root, MainMenuScene scene) {
		String[] files = FileBrowser.getTerrainFileNames();
		ObservableList<String> ol = FXCollections.observableArrayList();
		ol.addAll(Arrays.asList(files));

		scene.can_preview = new Canvas(MENU_HEIGHT, MENU_HEIGHT);
		scene.can_preview.setOnMouseClicked((MouseEvent event) -> {
			scene.canvasClicked(event.getX(), event.getY());
		});

		scene.lw_terrainList = new ListView<>();
		scene.lw_terrainList.setPrefHeight(MENU_HEIGHT);
		scene.lw_terrainList.setItems(ol);
		scene.lw_terrainList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			scene.reloadPreview(newValue);
		});

		HBox buttonRack = new HBox(16);
		buttonRack.setAlignment(Pos.CENTER);
		scene.btn_start = new Button("Start");
		scene.btn_start.setOnAction((ActionEvent event) -> {
			scene.startSimulation();
		});
		buttonRack.getChildren().add(scene.btn_start);
		scene.btn_end = new Button("End");
		scene.btn_end.setOnAction((ActionEvent e) -> {
			scene.endItAll();
		});
		buttonRack.getChildren().add(scene.btn_end);

		root.add(scene.can_preview, 0, 0);
		root.add(scene.lw_terrainList, 1, 0);
		root.add(buttonRack, 0, 1, 2, 1);
		return root;
	}

	private void startSimulation() {
		System.out.println("Starting terrain " + this.lw_terrainList.getSelectionModel().getSelectedItem());
	}

	private void endItAll() {
		System.out.println("U'LL PAY FOR THIS!");
	}

	private void reloadPreview(String newValue) {
		System.out.println("Preview terrain " + newValue);
		File f = FileBrowser.openTerrainFile(newValue);
		if (f == null) {
			System.err.println("Could not find file " + newValue);
			return;
		}
		Warzone wz = Warzone.loadFromFile(f);
		if (wz == null) {
			System.err.println("Could not load warzone data");
			return;
		}
		Terrain t = wz.getTerrain();
		this.tRend.setTerrain(t);
		//this.tRend.render(this.can_preview.getGraphicsContext2D(), new ColorPicker.Monochromatic(t, Color.LIME));
		this.tRend.render(this.can_preview.getGraphicsContext2D(), new ColorPicker.ColorSequence(t));

	}

	@Override
	public String getTitle() {
		return "Hlavní menu";
	}

	private void canvasClicked(double x, double y) {
		double normX = x / this.can_preview.getWidth();
		double normY = y / this.can_preview.getHeight();
		System.out.format("Canvas clicked at %.2fx%.2f height: %d\n", normX, normY, this.tRend.heightOn(normX, normY));

	}

}
