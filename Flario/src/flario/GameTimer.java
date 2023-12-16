package flario;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class GameTimer extends AnimationTimer {
	private GraphicsContext gc;
	private Character character;
	private Scene scene;
	private static boolean goUp, goDown, goLeft, goRight;
	private static boolean gameOver;
	private double backgroundX;
	private Image background = new Image( "file:src/images/background.png", 1280, 720, false, false);
//	private Image background = new Image( "images/background.png", 1280, 720, false, false);

	private Pipe pipe;
	private static int gameTime;
	
	public final static int GROUND_POSITION = 510;
	public final static double GRAVITY_SPEED = 0.1;
	public final static int BACKGROUND_SPEED = 1;
	
    GameTimer(Scene scene, GraphicsContext gc) {
    	this.gc = gc;
    	this.scene = scene;    	
    	this.character = new Character("Mario");
    	//this.startSpawn = this.startShoot = System.nanoTime();
    	
    	// test pipe
    	this.pipe = new Pipe(640, GROUND_POSITION - 192, true, 192);
    	
    	// game duration
    	GameTimer.gameTime = 60;
    	
    	this.prepareActionHandlers();
    }
    
    @Override
	public void handle(long currentNanoTime)
    {
		this.redrawBackgroundImage();
        this.renderSprites();
        this.moveSprites();
        this.drawScore();
        
        //if(!this.guardian.isAlive()) {
        //	this.stop();				// stops this AnimationTimer (handle will no longer be called) so all animations will stop
        //	this.drawGameOver();		// draw Game Over text
       // }
    }
    
    void redrawBackgroundImage() {
		// clear the canvas
        this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

        // redraw background image (moving effect)
        this.backgroundX -= GameTimer.BACKGROUND_SPEED;

        this.gc.drawImage( background, this.backgroundX+this.background.getWidth(), 0 );
        this.gc.drawImage( background, this.backgroundX, 0);
        
        // hitbox debug code
        this.gc.strokeLine(1280, GROUND_POSITION, 0, GROUND_POSITION);
        this.gc.strokeRect(character.getBounds().getMinX(), character.getBounds().getMinY(), character.getBounds().getWidth(), character.getBounds().getHeight());
        this.gc.strokeRect(pipe.getBounds().getMinX(), pipe.getBounds().getMinY(), pipe.getBounds().getWidth(), pipe.getBounds().getHeight());
        
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
       
    }
    
    void moveSprites() {
        this.moveCharacter();
       
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
                if(code.equals("LEFT")) {
                	GameTimer.goLeft = true;
                }
                
                if(code.equals("RIGHT")) {
                	GameTimer.goRight = true;
                }
                
                if(code.equals("UP")) {
                	GameTimer.goUp = true;
                }
                
                if(code.equals("DOWN")) {
                	GameTimer.goDown = true;
                }
                
                if(code.equals("P")) {
                	GameTimer.gameOver = true;
                	GameTimer.gameTime = 0; // adds a gametime setter
                }
                
            }
        });

    	this.scene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                if(code.equals("LEFT")) {
                	GameTimer.goLeft = false;
                }
                
                if(code.equals("RIGHT")) {
                	GameTimer.goRight = false;
                	
                }
                
                //if(code.equals("UP")) {
                	//GameTimer.goUp = false;
                //}
                
                //if(code.equals("DOWN")) {
                	//GameTimer.goDown = false;
                //}
            }
        });
    }
	
	// new method
	// implements a counting down mechanism
	private void startCountdown() {
	    // Create an array of Timeline with a single element
	    Timeline[] timeline = new Timeline[1];

	    // Create a key frame that updates the remaining time every second
	    KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
	        gameTime--;
	        if (gameTime <= 0) {
	            timeline[0].stop(); // Stop the timeline
	            GameStage.playgameover();
	            this.stop();
	            this.drawGameOver();
	        }
	    });

	    // Create a timeline with the key frame
	    timeline[0] = new Timeline(keyFrame);
	    timeline[0].setCycleCount(gameTime);
	    timeline[0].play();
	}
	
	// added
	private void drawGameOver() {
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("GAME OVER!", (GameStage.WINDOW_WIDTH/3), (GameStage.WINDOW_HEIGHT/2));
	}
	
	/*
     * Gets called in handle() to move the guardian based on the goLeft and goRight flags
     * */
	private void moveCharacter() {
		
		  if (GameTimer.goLeft) {
			  this.character.setVelocityX(-Character.CHARACTER_SPEEDX); //move left
		  }else if(GameTimer.goRight) {
			  this.character.setVelocityX(Character.CHARACTER_SPEEDX); //move right
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
		  
		  //if(GameTimer.goDown){
			  //this.character.setDY(+Character.CHARACTER_SPEEDY);
		  //}
		  
		  this.character.updatePosition();
		 
        
	}
	
	private void drawScore(){
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.gc.setFill(Color.YELLOW);
		// modified the ff code
		this.gc.fillText("Remaining Time: " + String.valueOf(GameTimer.gameTime), 20, 30);
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		this.gc.setFill(Color.WHITE);
	}
	
	/*
	 * private void drawGameOver(){ this.gc.setFont(Font.font("Verdana",
	 * FontWeight.BOLD, 50)); this.gc.setFill(Color.WHITE);
	 * this.gc.fillText("GAME OVER!", 20, Game.WINDOW_HEIGHT/2); }
	 */
	
	
	
}
