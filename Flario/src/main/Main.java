package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	// testing
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		newStage primStage = new newStage();
		primStage.setStage(stage);

	}

}
