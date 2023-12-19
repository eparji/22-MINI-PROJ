package flario;

import java.io.InputStream;
import javafx.application.Platform;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameStage {
	private static Stage stage;
	private static Scene splashScene;		// the splash scene
	private static Scene gameScene;		// the game scene
	private Scene aboutScene;       // the scene showing devs and references
	private Scene instScene;        // the scene showing the how to's of the game
	private static Scene gameoverScene;
	
	private Group root;
	private static Canvas canvas;			// the canvas where the animation happens
	
	private static MediaPlayer bgplayer;
	private static MediaPlayer gameplayer;
	private static MediaPlayer minigameplayer;
	private static MediaPlayer gameover;
	private static MediaPlayer gamewon;
	
	public final static int WINDOW_WIDTH = 1280;
	public final static int WINDOW_HEIGHT = 720;
	
    private final static String BUTTONSTYLE = "-fx-background-color:transparent;"
    										 + "-fx-padding:0;"
    										 + "-fx-background-size:0;"
    										 + "-fx-pref-width: 400;"  // Set the preferred width
    	                                     + "-fx-pref-height: 50;"; // Set the preferred height
    
    private final static Media GAME_MUSIC = new Media(GameStage.class.getResource("/music/flario-theme.mp3").toExternalForm());
    private final static Media BG_MUSIC = new Media(GameStage.class.getResource("/music/menu-theme.mp3").toExternalForm());
    private final static Media MINIGAME = new Media(GameStage.class.getResource("/music/flario-minigame.mp3").toExternalForm());
    private final static Media GAMEOVER = new Media(GameStage.class.getResource("/music/flario-gameover.mp3").toExternalForm());
    private final static Media GAMEWON = new Media(GameStage.class.getResource("/music/flario-gamewon.mp3").toExternalForm());
    
    public final static Font FONT_8BIT;
    
    static {
        InputStream is = GameStage.class.getResourceAsStream("/PressStart2P-Regular.ttf");
        FONT_8BIT = Font.loadFont(is, 20);
      }

    
	public GameStage(){
		canvas = new Canvas( GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT );
		this.root = new Group();
        this.root.getChildren().add( canvas );
        gameScene = new Scene( root );
	}
	
	public void setStage(Stage stage) {
		GameStage.stage = stage;
		stage.setTitle( "FlaRio" );
        
		this.initSplash(stage);			// initializes the Splash Screen with the New Game button
		this.initAbout(stage);          // initializes the About Screen with return to main menu button
		this.initInst(stage);           // initializes Instruction scene
		
		stage.setScene( splashScene );
        stage.setResizable(false);
        playbgmusic();                  // plays background music 
		stage.show();
	}
	
	// method that plays background music when the stage is called
	static void playbgmusic() {
		if(gameplayer != null) {
			gameplayer.stop();
			gameplayer = null;
		}
		
		if(gameover != null) {
			gameover.stop();
			gameover = null;
		}
		
		if(gamewon != null) {
			gamewon.stop();
			gamewon = null;
		}
		
	    bgplayer = new MediaPlayer(GameStage.BG_MUSIC);
	    bgplayer.setVolume(0.22);
	    bgplayer.setCycleCount(MediaPlayer.INDEFINITE); // Play the music indefinitely
	    bgplayer.play();
	}
	
	// method that plays the game music when play button gets clicked
	static void playgamemusic()
	{
		if(bgplayer != null) {
			bgplayer.stop();
			bgplayer = null;
		}
		
		if(minigameplayer != null) {
			minigameplayer.stop();
			minigameplayer = null;
		}
		
		if(gameover != null) {
			gameover.stop();
			gameover = null;
		}
		
		if(gamewon != null) {
			gamewon.stop();
			gamewon = null;
		}
		
		gameplayer = new MediaPlayer(GameStage.GAME_MUSIC);
		gameplayer.setVolume(0.18);
		gameplayer.setCycleCount(MediaPlayer.INDEFINITE); 
		gameplayer.play();
	}
	
	static void playminigamemusic()
	{
		if(gameplayer != null) {
			gameplayer.stop();
			gameplayer = null;
		}
		
		minigameplayer = new MediaPlayer(GameStage.MINIGAME);
		minigameplayer.setVolume(0.20);
		minigameplayer.setCycleCount(MediaPlayer.INDEFINITE); 
		minigameplayer.play();
	}
	
	// method that plays the gameover music when timer runs out
	static void playgameover()
	{
		if(gameplayer != null) {
			gameplayer.stop();
			gameplayer = null;
		}
		
		if(bgplayer != null) {
			bgplayer.stop();
			bgplayer = null;
		}
		
		if(gamewon != null) {
			gamewon.stop();
			gamewon = null;
		}
		
		gameover = new MediaPlayer(GameStage.GAMEOVER);
		gameover.setVolume(0.18);
		gameover.setCycleCount(1); 
		gameover.play();
	}
	
	// method that plays the gameover music when timer runs out
		static void playgamewon()
		{
			if(gameplayer != null) {
				gameplayer.stop();
				gameplayer = null;
			}
			
			if(bgplayer != null) {
				bgplayer.stop();
				bgplayer = null;
			}
			
			if(gameover != null) {
				gameover.stop();
				gameover = null;
			}
			
			gamewon = new MediaPlayer(GameStage.GAMEWON);
			gamewon.setVolume(0.18);
			gamewon.setCycleCount(1); 
			gamewon.play();
			
		}
	
	private void initSplash(Stage stage) {
		StackPane root = new StackPane();
        root.getChildren().addAll(GameStage.createCanvas("mainmenu-bg.png"),this.createVBox());
        GameStage.splashScene = new Scene(root);
	}
	
	// creates an about scene containing information about the devs and the references
	private void initAbout(Stage stage) {
		StackPane about = new StackPane();
		
		Image ret = new Image("return-main-btn.png", 300, 75, true, true);
        ImageView retV = new ImageView(ret);

	    Button retmain = new Button();

	    retmain.setGraphic(retV);
	    retmain.setStyle(BUTTONSTYLE);
	    setButtonActionsAndStyles(retmain, event -> showMenu(stage), BUTTONSTYLE);

		StackPane.setAlignment(retmain, Pos.BOTTOM_CENTER);
		StackPane.setMargin(retmain, new Insets(0, 0, 10, 0)); 
	    about.getChildren().addAll(createCanvas("about.png"), retmain);

		this.aboutScene = new Scene(about);
	}
	
	// create the instruction scene containing the controls, and different things 
	// a player might come accross in the game
	private void initInst(Stage stage) {
		ScrollPane inst = new ScrollPane();
		this.instScene = new Scene(inst,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
        inst.setContent(this.createInstPane());
        
        inst.setContent(this.createInstPane());
        inst.requestFocus(); // Request focus on the ScrollPane
	}
	
	// creates a pane object containing the contents of the scroll pane for the scene
	private Pane createInstPane() {
		Pane root = new Pane();
		
		Image instImg = new Image("instructions.png");
		ImageView instV = new ImageView(instImg);
		
		Image ret = new Image("return-main-btn.png", 300, 75, true, true);
        ImageView retV = new ImageView(ret);

	    Button retmain = new Button();

	    retmain.setGraphic(retV);
	    setButtonActionsAndStyles(retmain, event -> showMenu(stage), BUTTONSTYLE);
	    
	    // positioning a button in a Pane involves the bind() method
	    // number subtraction operation are expressed by using subtract() method
	    retmain.layoutXProperty().bind(root.widthProperty().subtract(retmain.widthProperty()).divide(2)); // Center horizontally
        retmain.layoutYProperty().bind(root.heightProperty().subtract(retmain.heightProperty()).subtract(50)); 
	    
	    Canvas canvas = new Canvas(1280, (ret.getWidth() / 1920 * 720));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image bg = new Image("mainbg.png");
        gc.drawImage(bg, 0, 0);
	    
	    root.getChildren().addAll(canvas, instV, retmain);
	    
	    return(root);
	}
	
	private static void initGameOver(String score, int time, int type) {
	    Pane gameover = new Pane();
	    VBox texts = new VBox();
	    
	    // Create Text objects for score and time
	    Text standingText = new Text("Final Standing:\n");
	    Text standing = new Text(score);
	    Text timeText = new Text("\nTime Remaining:\n");
	    Text seconds = new Text(time + " seconds");

	    // Style the Text objects if needed
	    standingText.setFont(GameStage.FONT_8BIT);
	    standing.setFont(GameStage.FONT_8BIT);
	    timeText.setFont(GameStage.FONT_8BIT);
	    seconds.setFont(GameStage.FONT_8BIT);
	    
	    texts.setAlignment(Pos.CENTER); // set alignment of vbox to center
	    texts.getChildren().addAll(standingText, standing, timeText, seconds);
	    
	    texts.layoutXProperty().bind(gameover.widthProperty().subtract(texts.prefWidth(-1)).divide(2));
	    texts.layoutYProperty().bind(gameover.heightProperty().subtract(texts.prefHeight(-1)).divide(2));
	    
	    VBox buttons = new VBox();
	    
	    Image tryI = new Image("tryagain-btn.png", 300, 75, true, true);
	    ImageView tryV = new ImageView(tryI);

	    Button trybtn = new Button();
	    trybtn.setGraphic(tryV);
	    setButtonActionsAndStyles(trybtn, event -> setGame(stage), BUTTONSTYLE);

	    Image ret = new Image("return-main-btn.png", 300, 75, true, true);
	    ImageView retV = new ImageView(ret);

	    Button retmain = new Button();
	    retmain.setGraphic(retV);
	    setButtonActionsAndStyles(retmain, event -> showMenu(stage), BUTTONSTYLE);
	    
	    buttons.getChildren().addAll(trybtn, retmain);
	    
	    String filepath;
	    if(type == 1) filepath = "gameover.png";
	    else filepath = "gamewon.png";
	    
	    gameover.getChildren().addAll(createCanvas(filepath), texts, buttons);
	    
	    Platform.runLater(() -> {
	        // Position the VBox at the bottom center of the Pane
	        buttons.setLayoutX((gameover.getWidth() - buttons.getWidth()) / 2);
	        buttons.setLayoutY(gameover.getHeight() - buttons.getHeight() - 50); // subtract more to move it up
	    });
	    
	    gameoverScene = new Scene(gameover);
	}
	
	private static Canvas createCanvas(String filepath) {
    	Canvas canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Image bg = new Image(filepath, 1280, 720, true, true);
        gc.drawImage(bg, 0, 0);
        return canvas;
    }
	
	static void setGame(Stage stage) {
        stage.setScene(gameScene);	
        GraphicsContext gc = canvas.getGraphicsContext2D();	// we will pass this gc to be able to draw on this Game's canvas
        GameTimer gameTimer = new GameTimer(gameScene, gc);
        playgamemusic();
        gameTimer.start();			// this internally calls the handle() method of our GameTimer
	}	
	
	static void showMenu(Stage stage) {
		if(bgplayer == null) playbgmusic();
		stage.setScene(splashScene);
	}
	
	void devInfo(Stage stage) {
        stage.setScene(this.aboutScene);
	}
	
	void gameInst(Stage stage) {
		stage.setScene(this.instScene);
	}
	
	static void showGameOver(String score, int time, int type) {
		if(type == 1) playgameover();
		else playgamewon();
		initGameOver(score, time, type);       // initializes gameover scene
		stage.setScene(gameoverScene);
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

        Image play = new Image("play-btn.png", 300, 75, true, true);
        ImageView playV = new ImageView(play);
        
        Image dev = new Image("dev-btn.png", 300, 75, true, true);
        ImageView devV = new ImageView(dev);
        
        Image ins = new Image("instructions-btn.png", 300, 75, true, true);
        ImageView insV = new ImageView(ins);

        Image exit = new Image("exit-btn.png", 300, 75, true, true);
        ImageView exitV = new ImageView(exit);
        
        Button startBtn = new Button();
        Button devBtn = new Button();
        Button insBtn = new Button();
        Button exitBtn = new Button();
        
        startBtn.setGraphic(playV);
        devBtn.setGraphic(devV);
        insBtn.setGraphic(insV);
        exitBtn.setGraphic(exitV);
        
        vbox.getChildren().addAll(startBtn, devBtn, insBtn, exitBtn);
        
        setButtonActionsAndStyles(startBtn, event -> setGame(stage), BUTTONSTYLE);
        setButtonActionsAndStyles(devBtn, event -> devInfo(stage), BUTTONSTYLE);
        setButtonActionsAndStyles(insBtn, event -> gameInst(stage), BUTTONSTYLE);        
        setButtonActionsAndStyles(exitBtn, event -> endGame(), BUTTONSTYLE);        
        
        return vbox;
    }
    
    private static void setButtonActionsAndStyles(Button button, EventHandler<ActionEvent> actionEvent, String buttonStyle) {
    	button.setStyle(buttonStyle);
    	button.setOnAction(actionEvent);
        button.setOnMouseEntered(event -> button.setStyle(buttonStyle + "-fx-opacity: 0.8;"));
        button.setOnMouseExited(event -> button.setStyle(buttonStyle));
    }
}
