package main;

import flario.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		launch(args);

	}
	
	public void start(Stage stage) {
		Game game = new Game();
		game.setStage(stage);
	}
	
}
