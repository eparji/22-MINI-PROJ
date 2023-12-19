package flario;

import flario.Character;
import javafx.scene.image.Image;

public class Character extends Sprite {
	private String name;
	private boolean isAlive;
	private int score;
	private boolean isGrounded;
	private int health;
	public double xPos;
	
	
	public final static double INITIAL_X = 0;
	public final static double INITIAL_Y = 410;
	public final static double CHARACTER_SPEEDY = 5;
	public final static double CHARACTER_SPEEDX = 3;
	public final static double CHARACTER_WIDTH = 52;
	public final static double CHARACTER_HEIGHT = 64;
	public final static int INIT_SCORE = 50;
	
	private final static Image RIGHT_CHAR_IMG = new Image("right-char.png", CHARACTER_WIDTH, CHARACTER_HEIGHT, false, false);
	private final static Image LEFT_CHAR_IMG = new Image("left-char.png", CHARACTER_WIDTH, CHARACTER_HEIGHT, false, false);
	private Image rightImage; // The right-facing sprite
    private Image leftImage; // The left-facing sprite
    
	Character(String name){
       	super(Character.INITIAL_X, Character.INITIAL_Y, Character.CHARACTER_WIDTH, Character.CHARACTER_HEIGHT, Character.RIGHT_CHAR_IMG);
		this.name = name;
		this.isAlive = true;
		this.score = INIT_SCORE;
		this.isGrounded = false;
		this.health = 100;
		this.xPos = INITIAL_X;
		
		this.rightImage = RIGHT_CHAR_IMG;
        this.leftImage = LEFT_CHAR_IMG;
	}

	String getName(){
		return this.name;
	}

	int getScore(){
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void setHealth(int health) {
		this.health = health;
		if(this.health <= 0) {
			this.isAlive = false;
		}
	}

    public void die(){
    	this.isAlive = false;
    }

    boolean isAlive(){
    	return this.isAlive;
    }
    
    boolean isGrounded() {
    	return this.isGrounded;
    } 
    
    // methods to change the character's sprite
    public void faceRight() {
        this.setImage(rightImage);
    }

    public void faceLeft() {
        this.setImage(leftImage);
    }
    
//    void updateXPos() {
//    	this.xPos += CHARACTER_SPEEDX;
//    }
//    
//    double getXPos() {
//    	return(this.xPos);
//    }
}
