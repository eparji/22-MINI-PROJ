/***********************************************************
 * 
 * FlaRio, a mix between Mario and another well-known game Flappy Bird.
 * 
 * It combines the best of both worlds, relatively easy controls 
 * (WASD, UP/DOWN/LEFT/RIGHT keys) of Mario, and the replayability of Flappy Bird. 
 * The world you play every time is unique, so brace yourself. 
 * 
 * Play as our character JavaMan and conquer the obstacles and challenges to reach
 * UNO standing. But don't fret as random powerups would also be generated along the course,
 * just don't mistake the debuffs for a powerup as you may get the "nanginginig" effect from
 * getting cold. Also, watch out for a cool pickup. It may give you more time to conquer 
 * your deadlines
 *   
 * @authors FRG Aguitez, 
 * 			Keshia Gayoso, 
 * 			Rsean Gueco, 
 * 			Michael Cariaso
 * 
 * @date_created 2023-11-22 
 * 
 ***********************************************************/

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
