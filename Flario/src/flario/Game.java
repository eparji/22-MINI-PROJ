package flario;

import flario.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {
	private Stage stage;
	private Scene splashScene;		// the splash scene
	private Scene gameScene;		// the game scene
	private Group root;
	private Canvas canvas;			// the canvas where the animation happens
	
	public final static int WINDOW_WIDTH = 1280;
	public final static int WINDOW_HEIGHT = 720;
	
	public Game(){
		this.canvas = new Canvas( Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT );
		this.root = new Group();
        this.root.getChildren().add( this.canvas );
        this.gameScene = new Scene( root );
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
		stage.setTitle( "FlaRio" );
        
		this.initSplash(stage);			// initializes the Splash Screen with the New Game button
		
		stage.setScene( this.splashScene );
        stage.setResizable(false);
		stage.show();
	}
	
	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
        root.getChildren().addAll(this.createCanvas(),this.createVBox());
        this.splashScene = new Scene(root);
	}
	
	private Canvas createCanvas() {
    	Canvas canvas = new Canvas(Game.WINDOW_WIDTH,Game.WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Image bg = new Image("images/mainbg.png", 1280, 720, false, false);
        gc.drawImage(bg, 0, 0);
        return canvas;
    }
    
    private VBox createVBox() {
    	VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Button b1 = new Button("START");
        
        vbox.getChildren().add(b1);
        
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                setGame(stage);		// changes the scene into the game scene
            }
        });
        
        return vbox;
    }
	
	void setGame(Stage stage) {
        stage.setScene( this.gameScene );	
        
        GraphicsContext gc = this.canvas.getGraphicsContext2D();	// we will pass this gc to be able to draw on this Game's canvas
        
        GameTimer gameTimer = new GameTimer(gameScene, gc);
        gameTimer.start();			// this internally calls the handle() method of our GameTimer
        
	}	
}
