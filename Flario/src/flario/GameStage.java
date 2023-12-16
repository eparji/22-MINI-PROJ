package flario;

import flario.GameStage;
//import flario.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GameStage {
	private Stage stage;
	private Scene splashScene;		// the splash scene
	private Scene gameScene;		// the game scene
	private Scene aboutScene;         // the scene showing devs and references
	private Group root;
	private Canvas canvas;			// the canvas where the animation happens
	private MediaPlayer bgplayer, gameplayer;
	
	public final static int WINDOW_WIDTH = 1280;
	public final static int WINDOW_HEIGHT = 720;
	
//	private final static Image INSTRUCTION_PAGE = new Image("", 1280, 720, false, false);
//	private final static Image ABOUTDEVS_PAGE = new Image("", 1280, 720, false, false);
	
	// Apply custom styles to the buttons
    private final static String BUTTONSTYLE = "-fx-background-color:transparent;"
    										 + "-fx-padding:0;"
    										 + "-fx-background-size:0;"
    										 + "-fx-pref-width: 400;"  // Set the preferred width
    	                                     + "-fx-pref-height: 50;"; // Set the preferred height
    
    private final static Media GAME_MUSIC = new Media(GameStage.class.getResource("/music/flario-theme.mp3").toExternalForm());
    private final static Media BG_MUSIC = new Media(GameStage.class.getResource("/music/menu-theme.mp3").toExternalForm());
	
	public GameStage(){
		this.canvas = new Canvas( GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT );
		this.root = new Group();
        this.root.getChildren().add( this.canvas );
        this.gameScene = new Scene( root );
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
		stage.setTitle( "FlaRio" );
        
		this.initSplash(stage);			// initializes the Splash Screen with the New Game button
		this.initAbout(stage);
		stage.setScene( this.splashScene );
        stage.setResizable(false);
        playbgmusic();
		stage.show();
	}
	
	// Method that plays background music when the stage is called
	void playbgmusic() {
		if(gameplayer != null) gameplayer.stop();
		
	    bgplayer = new MediaPlayer(BG_MUSIC);
	    bgplayer.setVolume(0.18);
	    bgplayer.setCycleCount(MediaPlayer.INDEFINITE); // Play the music indefinitely
	    bgplayer.play();
	}
	
	void playgamemusic()
	{
		if(bgplayer != null) bgplayer.stop();
		
		gameplayer = new MediaPlayer(GAME_MUSIC);
		gameplayer.setVolume(0.12);
		gameplayer.setCycleCount(MediaPlayer.INDEFINITE); // Play the music indefinitely
		gameplayer.play();
	}
	
	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
        root.getChildren().addAll(this.createCanvas("file:src/images/mainmenu-bg.png"),this.createVBox());
        this.splashScene = new Scene(root);
	}
	
	private void initAbout(Stage stage) {
		StackPane about = new StackPane();
		
		Image ret = new Image("file:src/images/return-main-btn.png", 300, 75, true, true);
        ImageView retV = new ImageView(ret);

	    Button retmain = new Button();

	    retmain.setGraphic(retV);
	    retmain.setStyle(BUTTONSTYLE);
	    
	    retmain.setOnMouseClicked(event -> showMenu(stage));
		StackPane.setAlignment(retmain, Pos.BOTTOM_CENTER);
		StackPane.setMargin(retmain, new Insets(0, 0, 10, 0)); // top, right, bottom, left
	    about.getChildren().addAll(createCanvas("file:src/images/about.png"), retmain);

		this.aboutScene = new Scene(about);
	}

	private Canvas createCanvas(String filepath) {
    	Canvas canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Image bg = new Image(filepath, 1280, 720, true, true);
        //        Image bg = new Image("images/mainbg.png", 1280, 720, false, false);
        gc.drawImage(bg, 0, 0);
        return canvas;
    }
	
	void setGame(Stage stage) {
        stage.setScene(this.gameScene);	
        GraphicsContext gc = this.canvas.getGraphicsContext2D();	// we will pass this gc to be able to draw on this Game's canvas
        GameTimer gameTimer = new GameTimer(gameScene, gc);
        playgamemusic();
        gameTimer.start();			// this internally calls the handle() method of our GameTimer
	}	
	
	private void showMenu(Stage stage) {
		stage.setScene(this.splashScene);
	}
	
	void devInfo(Stage stage) {
        stage.setScene(this.aboutScene);
	}
	
	void endGame() {
		System.out.println("Exiting...");
		System.exit(0);
	}
	
    private VBox createVBox() {
    	VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(300, 0, 0, 0));
        vbox.setSpacing(5);

        Image play = new Image("file:src/images/play-btn.png", 300, 75, true, true);
        ImageView playV = new ImageView(play);
        
        Image dev = new Image("file:src/images/dev-btn.png", 300, 75, true, true);
        ImageView devV = new ImageView(dev);
        
        Image ins = new Image("file:src/images/instructions-btn.png", 300, 75, true, true);
        ImageView insV = new ImageView(ins);

        Image exit = new Image("file:src/images/exit-btn.png", 300, 75, true, true);
        ImageView exitV = new ImageView(exit);
        
        Button startBtn = new Button();
        Button devBtn = new Button();
        Button insBtn = new Button();
        Button exitBtn = new Button();
        
        startBtn.setGraphic(playV);
        devBtn.setGraphic(devV);
        insBtn.setGraphic(insV);
        exitBtn.setGraphic(exitV);
        
        // Set styles for the buttons
        startBtn.setStyle(BUTTONSTYLE);
        devBtn.setStyle(BUTTONSTYLE);
        insBtn.setStyle(BUTTONSTYLE);
        exitBtn.setStyle(BUTTONSTYLE);
        
        vbox.getChildren().addAll(startBtn, devBtn, insBtn, exitBtn);
        
        startBtn.setOnAction(event -> setGame(stage));
        
        devBtn.setOnAction(event -> devInfo(stage));
        
        insBtn.setOnAction(event -> setGame(stage));
        
        exitBtn.setOnAction(event -> endGame());
        
        return vbox;
    }
}
