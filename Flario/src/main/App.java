package main;

import flario.GameStage;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		launch(args);

	}
	
	public void start(Stage stage) {
		Font.loadFont(getClass().getClassLoader().getResourceAsStream("PressStart2P-Regular.ttf"), 20);		
		GameStage game = new GameStage();
		game.setStage(stage);
	}
	
}
