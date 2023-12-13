package flario;

//import flario.Game;
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
import javafx.scene.image.ImageView;
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
	
	// Apply custom styles to the buttons
    public final static String buttonStyle = "-fx-background-color:transparent;"
    										 + "-fx-padding:0;"
    										 + "-fx-background-size:0;"
    										 + "-fx-pref-width: 400;" // Set the preferred width
    	                                     + "-fx-pref-height: 50;"; // Set the preferred height
	
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
        
        Image bg = new Image("file:C:/Users/ASUS/git/22-MINI-PROJ/Flario/src/images/mainmenu-bg.png", 1280, 720, false, false);
//        Image bg = new Image("images/mainbg.png", 1280, 720, false, false);
        gc.drawImage(bg, 0, 0);
        return canvas;
    }
    
    private VBox createVBox() {
    	VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(300, 0, 0, 0));
        vbox.setSpacing(8);

        Image play = new Image("file:C:/Users/ASUS/git/22-MINI-PROJ/Flario/src/images/play-btn.png", 300, 75, false, false);
        ImageView playV = new ImageView(play);
        
        Image dev = new Image("file:C:/Users/ASUS/git/22-MINI-PROJ/Flario/src/images/dev-btn.png", 300, 75, false, false);
        ImageView devV = new ImageView(dev);
        
        Image opt = new Image("file:C:/Users/ASUS/git/22-MINI-PROJ/Flario/src/images/options-btn.png", 300, 75, false, false);
        ImageView optV = new ImageView(opt);

        Image exit = new Image("file:C:/Users/ASUS/git/22-MINI-PROJ/Flario/src/images/exit-btn.png", 300, 75, false, false);
        ImageView exitV = new ImageView(exit);
        
        Button startBtn = new Button();
        Button devBtn = new Button();
        Button exitBtn = new Button();
        
        startBtn.setGraphic(playV);
        devBtn.setGraphic(devV);
        exitBtn.setGraphic(exitV);
        
        // Set styles for the buttons
        startBtn.setStyle(buttonStyle);
        devBtn.setStyle(buttonStyle);
        exitBtn.setStyle(buttonStyle);
        
        vbox.getChildren().addAll(startBtn, devBtn, exitBtn);
        
        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                setGame(stage);		// changes the scene into the game scene
            }
        });
        
        devBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                setGame(stage);		// changes the scene into the game scene
            }
        });
        
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	System.out.println("Exiting...");
    			System.exit(0);
            }
        });
        
        return vbox;
    }
    
    void devInfo(Stage stage) {
        stage.setScene( this.gameScene );	
        
        GraphicsContext gc = this.canvas.getGraphicsContext2D();	// we will pass this gc to be able to draw on this Game's canvas
        
        GameTimer gameTimer = new GameTimer(gameScene, gc);
        gameTimer.start();			// this internally calls the handle() method of our GameTimer
        
	}
	
	void setGame(Stage stage) {
        stage.setScene( this.gameScene );	
        
        GraphicsContext gc = this.canvas.getGraphicsContext2D();	// we will pass this gc to be able to draw on this Game's canvas
        
        GameTimer gameTimer = new GameTimer(gameScene, gc);
        gameTimer.start();			// this internally calls the handle() method of our GameTimer
        
	}	
}
