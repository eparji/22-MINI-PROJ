package flario;

import flario.Character;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character extends Sprite {
	private String name;     // assigned name to character
	private boolean isAlive; // alive flag
	private int score;
	private boolean isGrounded; // flag
	private int health;
	public double xPos;
	
	public boolean isColliding; // collision property
	
	// spawn point
	public final static double INITIAL_X = 0;
	public final static double INITIAL_Y = 410;
	
	// velocity of char when moving
	public final static double CHARACTER_SPEEDY = 5;
	public final static double CHARACTER_SPEEDX = 3;
	
	// dimensions
	public final static double CHARACTER_WIDTH = 52;
	public final static double CHARACTER_HEIGHT = 64;
	
	public final static int INIT_SCORE = 50; // default starting score
	
	// import necessary images
	private final static Image RIGHT_CHAR_IMG = new Image("right-char.png", CHARACTER_WIDTH, CHARACTER_HEIGHT, false, false);
	private final static Image LEFT_CHAR_IMG = new Image("left-char.png", CHARACTER_WIDTH, CHARACTER_HEIGHT, false, false);

	private Image rightImage; // The right-facing sprite
    
	Character(String name){
       	super(Character.INITIAL_X, Character.INITIAL_Y, Character.CHARACTER_WIDTH, Character.CHARACTER_HEIGHT, Character.RIGHT_CHAR_IMG);
		this.name = name;
		this.isAlive = true;
		this.score = INIT_SCORE;
		this.isGrounded = false;
		this.health = 100;
		this.xPos = INITIAL_X;
		this.rightImage = RIGHT_CHAR_IMG; 
		this.setImage(rightImage);
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
		if(this.health <= 0) { // updating flag
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
    public void faceRight(double height, double width) {
        this.setImage(new Image("right-char.png", width, height, false, false));
    }

    public void faceLeft(double height, double width) {
        this.setImage(new Image("left-char.png", width, height, false, false));
    }
}
