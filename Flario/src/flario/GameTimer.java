package flario;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class GameTimer extends AnimationTimer {
	private Pipe pipe;
	private GraphicsContext gc;
	private Character character;
	private Scene scene;
	
	// added
	private static boolean goUp, goDown, goLeft, goRight;
	private static boolean moveScreenLeft, moveScreenRight;
	private ArrayList<Pipe> pipes; //array list of bottom pipes
	private ArrayList<Pipe> topPipes; //array list of upper pipes
	
	private static boolean gameOver;
	private Timeline timeline;

	private double backgroundX;
	private Image background = new Image( "background.png", 1280, 720, false, false);

//	private Image background = new Image( "file:src/images/background.png", 1280, 720, false, false);
	
	// added
	private Image background2 = new Image("plainsky.png", 1280, 720, false, false);
	private long startSpawn;
	public final static double SPAWN_DELAY = 1;
    
	
//>>>>>>> branch 'master' of https://github.com/eparji/22-MINI-PROJ.git
	private static int gameTime;
	private static int elapsedTime;
	
	public final static int GROUND_POSITION = 510;
	public final static double GRAVITY_SPEED = 0.1;
	public final static int BACKGROUND_SPEED = 1;
	public final static double TIME_SCORE_MULT = 0.5;
	
	// added
	public final static int WIDTH_PER_PIPE = 80;
	public final static int PIPE_INITIAL_XPOS = 1280;
	public final static int PIPE_SPAWN_INTERVAL = 200;
	public final static int LEFT_EDGE = 100;
	public final static int RIGHT_EDGE = 1100;
	
	
    GameTimer(Scene scene, GraphicsContext gc) {
    	this.gc = gc;
    	this.scene = scene;    	
    	this.character = new Character("Mario");
    	//this.startSpawn = this.startShoot = System.nanoTime();
    	
    	// test pipe
    	this.pipe = new Pipe(640, GROUND_POSITION - 192, true);
    	
    	// added
    	this.pipes = new ArrayList<Pipe>();
    	this.topPipes = new ArrayList<Pipe>();
    	this.startSpawn = System.nanoTime();
    	
    	
    	// game duration
    	GameTimer.gameTime = 90;
    	GameTimer.elapsedTime = 0;
    	
    	this.prepareActionHandlers();
    }
    
    @Override
	public void handle(long currentNanoTime)
    {
		this.redrawBackgroundImage();
		
        this.autoSpawn(currentNanoTime);
        this.renderSprites();
        this.handleCollision();
        this.moveSprites();
        this.drawScore();
        
        if(!this.character.isAlive()) {
        	endCountdown();
       	}
    }
    
    void redrawBackgroundImage() {
		// clear the canvas
        this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

        // redraw background image (moving effect)
        this.backgroundX -= GameTimer.BACKGROUND_SPEED;

        this.gc.drawImage( background, this.backgroundX+this.background.getWidth(), 0 );
        this.gc.drawImage( background, this.backgroundX, 0);
        
        // hitbox debug code
        // this.gc.strokeLine(1280, GROUND_POSITION, 0, GROUND_POSITION);
        // this.character.drawBounds(gc);
        
        //reset background x pos to loop background
        if(this.backgroundX <= -this.background.getWidth() && this.backgroundX % this.background.getWidth() == 0) {
        	this.backgroundX = 0;
        	
        }
        
        
        if(this.backgroundX>=GameStage.WINDOW_WIDTH) 
        	this.backgroundX = GameStage.WINDOW_WIDTH-this.background.getWidth();
//        System.out.println("Background X: " + this.backgroundX);
    }
    
      
    void renderSprites() {
    	// draw guardian
        this.character.render(this.gc);
        
        this.pipe.render(this.gc);
       
        // added
        // draw Sprites in ArrayLists
        for (Pipe pipe : this.pipes) {
        	pipe.render( this.gc );
        	
        }
        
        for(Pipe topPipe : this.topPipes) {
        	topPipe.render( this.gc );
        }
    }
    
    void moveSprites() {
    	// added
    	if(this.character.isGrounded()) {
    		this.moveCharacterGrounded();
    	}
    	else {
    		this.moveCharacterFlying();
    	}
        this.movePipe();
    }
	
    // added methods
    void autoSpawn(long currentNanoTime) {
    	double spawnElapsedTime = (currentNanoTime - this.startSpawn) / 1000000000.0;
    	
    	
        if(spawnElapsedTime > GameTimer.SPAWN_DELAY) {
        	this.spawnPipes();
        	this.character.setScore(this.character.getScore()+1); //test score
        	this.startSpawn = System.nanoTime();
        }
    }
    
    
    private void spawnPipes(){
		int xPos = 1280;
		Random r = new Random();
		
		int middle = 250;
		
		int interval = xPos - PIPE_SPAWN_INTERVAL; //spawning interval
		
		int top = r.nextInt(middle); //random number for the basis of bottom and top pipe position
		int bottomPos = (400 + top); //position of bottom pipe
		int topPos = top - 400; //correcting the position of top pipe
		
		//312 and 0
		if(this.pipes.size() == 0) {//first set of pipes
			this.pipes.add(new Pipe(xPos, bottomPos, true));
			this.topPipes.add(new Pipe(xPos, topPos, false));
		}else {//following pipes
			int size = this.pipes.size();
			if(this.pipes.get(size-1).positionX <= interval) {//interval for spawning the next pipe
				this.pipes.add(new Pipe(xPos, bottomPos, true));
				this.topPipes.add(new Pipe(xPos, topPos, false));
			} 		
		}
		

	}
    
    
    private void movePipe() {
		for(int i = 0; i < this.pipes.size(); i++){
			Pipe pipe = this.pipes.get(i);
			Pipe topPipe = this.topPipes.get(i);
			if(pipe.isVisible() && topPipe.isVisible()){
				topPipe.move();
				pipe.move();
			}
			else {
				this.pipes.remove(i);
				this.topPipes.remove(i);
			}
		}
	}
    
    
    /*
     * Catches the left and right key presses for the guardian's movement
     * */
	private void prepareActionHandlers() {
		startCountdown();
    	this.scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {

			public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                System.out.println("User Pressed Key: " + code);
                if(code.equals("LEFT") || code.equals("A") ) {
                	GameTimer.goLeft = true;
                }
                
                if(code.equals("RIGHT") || code.equals("D")) {
                	GameTimer.goRight = true;
                }
                
                if(code.equals("UP") || code.equals("W")) {
                	GameTimer.goUp = true;
                }
                
                if(code.equals("DOWN") || code.equals("S")) {
                	GameTimer.goDown = true;
                	//added as of dec 18
                	//tentative mini game trigger (must be triggered by stack overflow buff)
                	
                	MiniWindow minigame = new MiniWindow(); //launch mini game
                	minigame.start();
                	GameTimer.pauseTimerForDuration(getTimer(), Duration.seconds(5)); //pause main game for 5 secs
                }
                
                if(code.equals("P")) {
                	GameTimer.gameOver = true;
                	GameTimer.gameTime = 1; // adds a gametime setter
                	GameTimer.elapsedTime = 89;
                }
                
            }
        });

    	this.scene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                if(code.equals("LEFT") || code.equals("A")) {
                	GameTimer.goLeft = false;
                }
                
                if(code.equals("RIGHT") || code.equals("D")) {
                	GameTimer.goRight = false;
                	
                }
                
                //if(code.equals("UP") || code.equals("W")) {
                	//GameTimer.goUp = false;
                //}
                
                //if(code.equals("DOWN") || code.equals("S")) {
                	//GameTimer.goDown = false;
                //}
            }
        });
    }
	
	//time increment new method - added as of dec 18
	public static void setGameTime(int time) { //adding collected time to game time
		GameTimer.gameTime += time;
	}
	
	private static void pauseTimerForDuration(AnimationTimer timer, Duration duration) {
	    PauseTransition pt = new PauseTransition(duration); //pauses the game for 5 secs
	    pt.setOnFinished(event -> timer.start()); //game continues after the minigame

	    timer.stop();
	    pt.play();
	}
	
	private AnimationTimer getTimer() {
		return this;
	}
	
	// added methods
	private void moveCharacterGrounded() {
		if (GameTimer.goLeft) {
			if(this.character.getPositionX() <= LEFT_EDGE) {
				GameTimer.moveScreenRight = true;
				this.character.setPositionXY(LEFT_EDGE, this.character.getPositionY());
			}
			else {
			this.character.setVelocityX(-Character.CHARACTER_SPEEDX); //move left
			}
		}
		else if(GameTimer.goRight) {
			if(this.character.getPositionX() >= RIGHT_EDGE) {
				GameTimer.moveScreenLeft = true;
				this.character.setPositionXY(RIGHT_EDGE, this.character.getPositionY());
			}
			else {
			this.character.setVelocityX(Character.CHARACTER_SPEEDX); //move right
			}
		}
		else {
			this.character.setVelocityX(0); //stop moving
		}
		  
		  if(GameTimer.goUp && this.character.getPositionY() >= GROUND_POSITION - character.getHeight()) {
			  this.character.setVelocityY(-Character.CHARACTER_SPEEDY); //jump
		  }else {//go down
			  this.character.setVelocityY(this.character.getVelocityY()+GRAVITY_SPEED);
			  
			  if(this.character.getPositionY() > GROUND_POSITION - character.getHeight()) {
				  this.character.setPositionXY(this.character.getPositionX(), GROUND_POSITION - character.getHeight());
				  this.character.setVelocityY(0);
			  }
			  GameTimer.goUp = false;
			  
		  }
		  
		  this.character.updatePosition();
	}
	
	private void moveCharacterFlying() {
		if (GameTimer.goLeft) {
			if(this.character.getPositionX() <= LEFT_EDGE) {
				GameTimer.moveScreenRight = true;
				this.character.setPositionXY(LEFT_EDGE, this.character.getPositionY());
			}
			else {
			this.character.setVelocityX(-Character.CHARACTER_SPEEDX); //move left
			}
		}
		else if(GameTimer.goRight) {
			if(this.character.getPositionX() >= RIGHT_EDGE) {
				GameTimer.moveScreenLeft = true;
				this.character.setPositionXY(RIGHT_EDGE, this.character.getPositionY());
			}
			else {
			this.character.setVelocityX(Character.CHARACTER_SPEEDX); //move right
			}
		}
		else {
			this.character.setVelocityX(0); //stop moving
		}
		
		if(GameTimer.goUp) {
			this.character.setVelocityY(-Character.CHARACTER_SPEEDY); //jump
			GameTimer.goUp = false;
		}
		else {//go down
			this.character.setVelocityY(this.character.getVelocityY()+GRAVITY_SPEED);
			GameTimer.goUp = false;
		}
		 
		this.character.updatePosition();
	}
	
	private void handleCollision() {
		this.character.drawBounds(gc);
		
		for(int i = 0; i < this.pipes.size(); i++){
			Pipe pipe = this.pipes.get(i);
			Pipe topPipe = this.topPipes.get(i);
			
			//pipe.drawBounds(gc);
			//topPipe.drawBounds(gc);
			
			if(pipe.collidesWith(this.character) || topPipe.collidesWith(this.character)) {
				this.character.setHealth(this.character.getHealth()-25);
				this.character.setVelocityX(0);
				this.character.setVelocityY(0);
			}
			
//			System.out.println(this.character.getHealth());
		}
	}
	
	// implements a counting down mechanism
	private void startCountdown() {

	    // Create a key frame that updates the remaining time every second
	    KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
	        gameTime--;
	        elapsedTime++;
	        printScore();
	        if (gameTime <= 0) {
	            endCountdown();
	        }
	    });

	    // Create a timeline with the key frame
	    timeline = new Timeline(keyFrame);
	    timeline.setCycleCount(gameTime);
	    timeline.play();
	}
	
	private void endCountdown() {
		if (timeline != null) {
	        timeline.stop();
	        GameStage.playgameover();
            this.stop();
            this.drawGameOver();
	    }
	}

	
	// logic behind remaining time printing
	private String computeTime() {
		int time = gameTime;
		String min, sec;
		
		if(time > 59) {
			min = String.valueOf(time/60);
		}
		else {
			min = "0";
		}
		
		int seconds = time%60;
		
		if(seconds == 0) sec = "00";
		if(seconds > 9) sec = String.valueOf(seconds);
		else sec = "0" + String.valueOf(seconds);
		
		String normalizedTime = min + ":" + sec;
		
		return(normalizedTime);
	}
	
	private String computeScore() {
		int score = (int) (Character.INIT_SCORE - (elapsedTime*TIME_SCORE_MULT));
		this.character.setScore(score);
		return(String.format("%d", this.character.getScore()));
	}
	
	private void printScore() {
		System.out.println("TIME LEFT: " + computeTime());
		System.out.println("USER'S SCORE IS: " + computeScore() + "\n");
	}
	
	private void drawScore(){
		this.gc.setFont(GameStage.FONT_8BIT);
		this.gc.setFill(Color.YELLOW);
		// modified the ff code
		this.gc.fillText("Remaining Time: " + computeTime(), 20, 30);
		
		this.gc.setFont(GameStage.FONT_8BIT);
		this.gc.setFill(Color.YELLOW);
		this.gc.fillText("Score: " + computeScore(), 500, 30);
	}
	
	private void drawGameOver() {
		this.gc.setFont(GameStage.FONT_8BIT);
	    this.gc.setFill(Color.WHITE);
	    this.gc.fillText("GAME OVER!", (GameStage.WINDOW_WIDTH/3), (GameStage.WINDOW_HEIGHT/2));
	    
	    Button btn = new Button("Retry");
	    btn.setLayoutX(GameStage.WINDOW_WIDTH/2); // Set the X position of the button
	    btn.setLayoutY(GameStage.WINDOW_HEIGHT/2); // Set the Y position of the button
//		GameStage.setGameOver(this.character.getScore(), gameTime);
	}
	
}
