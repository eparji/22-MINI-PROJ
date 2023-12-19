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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameTimer extends AnimationTimer {
	private GraphicsContext gc;
	private Character character;
	private Scene scene;
	private Level level;
	
	// added
	private static boolean goUp, goDown, goLeft, goRight;
	private static boolean moveScreenLeft, moveScreenRight;
	private static boolean isTouchingGround;
	
	private ArrayList<Pipe> pipes;
	private ArrayList<Pipe> topPipes;
	private ArrayList<Block> blocks;
	
	private static boolean gameOver;
	private Timeline timeline;

	private double backgroundX;
	private Image background = new Image( "background.png", 1280, 720, false, false);

//	private Image background = new Image( "file:src/images/background.png", 1280, 720, false, false);
	
	// added
	private Image background2 = new Image("plainsky.png", 1280, 720, false, false);
	private long startSpawn;
	public final static double SPAWN_DELAY = 1;
    
	
	private static int gameTime;
	private static int elapsedTime;
	public final static double TIME_SCORE_MULT = 0.5;
	public final static double FINAL_SCORE_MULT= 0.15;
	
	public final static int GROUND_POSITION = 510;
	public final static double GRAVITY_SPEED = 0.1;
	public final static int BACKGROUND_SPEED = 1;
	public final static int SCREEN_MOVE_SPEED = 2;
	public final static int COLLISION_BUFFER = 16;
	
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
    	// obstacle generation done through level class
    	this.level = new Level();
    	this.pipes = this.level.pipes;
    	this.topPipes = this.level.topPipes;
    	this.blocks = this.level.blocks;
    	
    	// game duration
    	GameTimer.gameTime = 90;
    	GameTimer.elapsedTime = 0;
    	GameTimer.gameOver = false;
    	
    	this.prepareActionHandlers();
    }
    
    @Override
	public void handle(long currentNanoTime)
    {
    	this.redrawBackgroundImage();
		
        //this.autoSpawn(currentNanoTime);
        this.renderSprites();
        this.handleCollision();
        this.moveSprites();
        this.drawScore();
//        System.out.println(Level.isFinished(this.character.getXPos()));
        if(GameTimer.gameOver || !this.character.isAlive()) {
        	this.stop();
        	endCountdown();
        	if(!this.character.isAlive()) {
        		GameStage.showGameOver(computeFinalScore(1), gameTime, 1);
        	}
        	else {
        		GameStage.showGameOver(computeFinalScore(0), gameTime, 0);
        	}
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
        
        // added
        // draw Sprites in ArrayLists
        for (Pipe pipe : this.pipes) {
        	pipe.render( this.gc );
        	
        }
        
        for(Pipe topPipe : this.topPipes) {
        	topPipe.render( this.gc );
        }

        for(Block block : this.blocks) {
        	block.render(this.gc);
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
        this.moveObstacle();
    }
  
    private void moveObstacle() {
		for(int i = 0; i < this.pipes.size(); i++){
			Pipe pipe = this.pipes.get(i);
			Pipe topPipe = this.topPipes.get(i);
			//System.out.println(moveScreenRight);
			//System.out.println(moveScreenLeft);
			if(moveScreenRight) {
				System.out.print("exec");
				pipe.setVelocityX(SCREEN_MOVE_SPEED);
				topPipe.setVelocityX(SCREEN_MOVE_SPEED);
			}
			else if(moveScreenLeft) {
				pipe.setVelocityX(-SCREEN_MOVE_SPEED);
				topPipe.setVelocityX(-SCREEN_MOVE_SPEED);

			}
			else {

				pipe.setVelocityX(0);
				topPipe.setVelocityX(0);
			}
			
			pipe.updatePosition();
			topPipe.updatePosition();
		}
    	
		for(int i = 0; i < this.blocks.size(); i++){
			Block block = this.blocks.get(i);
			if(moveScreenRight) {
				block.setVelocityX(SCREEN_MOVE_SPEED);
			}
			else if(moveScreenLeft) {
				block.setVelocityX(-SCREEN_MOVE_SPEED);
			}
			else {
				block.setVelocityX(0);
			}
			block.updatePosition();
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
				GameTimer.moveScreenLeft = false;
				GameTimer.moveScreenRight = false;
				this.character.setVelocityX(-Character.CHARACTER_SPEEDX); //move left
			}
		}
		else if(GameTimer.goRight) {
//			this.character.updateXPos();
			if(this.character.getPositionX() >= RIGHT_EDGE) {
				GameTimer.moveScreenLeft = true;
				GameTimer.moveScreenRight = false;
				this.character.setPositionXY(RIGHT_EDGE, this.character.getPositionY());
			}
			else {
				GameTimer.moveScreenLeft = false;
				this.character.setVelocityX(Character.CHARACTER_SPEEDX); //move right
			}
		}
		else {
			GameTimer.moveScreenRight = false;
			GameTimer.moveScreenLeft = false;
			this.character.setVelocityX(0); //stop moving
		}
		  
		if(this.character.getPositionY() >= GROUND_POSITION - character.getHeight()) {
			GameTimer.isTouchingGround = true;
		}
		
		if(GameTimer.goUp && isTouchingGround) {
			this.character.setVelocityY(-Character.CHARACTER_SPEEDY); //jump
			GameTimer.isTouchingGround = false;
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
				GameTimer.moveScreenLeft = false;
				GameTimer.moveScreenRight = false;
				this.character.setVelocityX(-Character.CHARACTER_SPEEDX); //move left
			}
		}
		else if(GameTimer.goRight) {
//			this.character.updateXPos();
			if(this.character.getPositionX() >= RIGHT_EDGE) {
				GameTimer.moveScreenLeft = true;
				GameTimer.moveScreenRight = false;
				this.character.setPositionXY(RIGHT_EDGE, this.character.getPositionY());
			}
			else {
				GameTimer.moveScreenLeft = false;
				this.character.setVelocityX(Character.CHARACTER_SPEEDX); //move right
			}
		}
		else {
			GameTimer.moveScreenRight = false;
			GameTimer.moveScreenLeft = false;
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
		
		for(int i = 0; i < this.blocks.size(); i++){
			Block block = this.blocks.get(i);
			
			block.drawBounds(gc);
			
			if(block.collidesWith(this.character)) {

				System.out.println(this.character.getPositionX() +" "+ this.character.getPositionY() +" "+ block.getPositionX() +" "+ block.getPositionY());
				
				if(this.character.getPositionY() < block.getPositionY() - this.character.getHeight() + COLLISION_BUFFER) {
					this.character.setVelocityY(0);
					System.out.println("trigger1");
					this.character.setPositionXY(this.character.getPositionX(), block.getPositionY()-this.character.getHeight());
					GameTimer.isTouchingGround = true;
				}
				
				if(this.character.getPositionY() > block.getPositionY() + this.character.getHeight() - COLLISION_BUFFER) {
					this.character.setVelocityY(0);
					this.character.setPositionXY(this.character.getPositionX(), block.getPositionY() + this.character.getHeight());
				}
				
				if(this.character.getPositionX() < block.getPositionX() - this.character.getWidth() + COLLISION_BUFFER ) {
					GameTimer.goRight = false;
					System.out.println("trigger3");
					this.character.setPositionXY(block.getPositionX() - this.character.getWidth(), this.character.getPositionY());
				}
				
				if(this.character.getPositionX() > block.getPositionX() + this.character.getWidth() - COLLISION_BUFFER) {
					GameTimer.goLeft = false;
					System.out.println("trigger4");
					this.character.setPositionXY(block.getPositionX()+this.character.getWidth(), this.character.getPositionY());
				}
			
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
	
	private String computeFinalScore(int type) {
		if(type ==1) return("INC");
		int score = (int) (this.character.getScore() + (gameTime*FINAL_SCORE_MULT));
		this.character.setScore(score);
		score = this.character.getScore();
		
		if(score >= 95) return("1.00");
		if(score >= 90) return("1.25");
		if(score >= 85) return("1.50");
		if(score >= 80) return("1.75");
		if(score >= 75) return("2.00");
		if(score >= 70) return("2.25");
		if(score >= 65) return("2.50");
		if(score >= 60) return("2.75");
		if(score >= 55) return("3.00");
		else return("5.00");
		
	}
	
	private void printScore() {
		System.out.println("TIME LEFT: " + computeTime());
		System.out.println("USER'S GRADE IS: " + computeScore() + "\n");
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
	
}
