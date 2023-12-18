package flario;

import flario.Character;
import javafx.scene.image.Image;

public class Character extends Sprite {
	private String name;
	private boolean isAlive;
	private int score;
	private boolean isGrounded;
	private int health;
	
	private final static Image CHARACTER_IMAGE = new Image("file:src/images/character.png", 64, 64, false, false);
//	private final static Image CHARACTER_IMAGE = new Image("images/character.png", 64, 64, false, false);
	public final static double INITIAL_X = 0;
	public final static double INITIAL_Y = 410;
	public final static double CHARACTER_SPEEDY = 6;
	public final static double CHARACTER_SPEEDX = 3;
	public final static double CHARACTER_WIDTH = 64;
	public final static double CHARACTER_HEIGHT = 64;
	public final static int INIT_SCORE = 50;
	
	Character(String name){
       	super(Character.INITIAL_X, Character.INITIAL_Y, Character.CHARACTER_WIDTH, Character.CHARACTER_HEIGHT, Character.CHARACTER_IMAGE);
		this.name = name;
		this.isAlive = true;
		this.score = INIT_SCORE;
		this.isGrounded = false;
		this.health = 100;
		
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
}
