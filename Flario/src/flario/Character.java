package flario;

import flario.Character;
import javafx.scene.image.Image;

public class Character extends Sprite {
	private String name;
	private boolean alive;
	private int score;
	
	private final static Image CHARACTER_IMAGE = new Image("file:///C:/Users/ASUS/git/22-MINI-PROJ/Flario/src/images/character.png", 64, 64, false, false);
//	private final static Image CHARACTER_IMAGE = new Image("images/character.png", 64, 64, false, false);
	public final static double INITIAL_X = 0;
	public final static double INITIAL_Y = 410;
	public final static double CHARACTER_SPEEDY = 6;
	public final static double CHARACTER_SPEEDX = 3;
	public final static double CHARACTER_WIDTH = 64;
	public final static double CHARACTER_HEIGHT = 64;

	Character(String name){
       	super(Character.INITIAL_X, Character.INITIAL_Y, Character.CHARACTER_WIDTH, Character.CHARACTER_HEIGHT, Character.CHARACTER_IMAGE);
		this.name = name;
		this.alive = true;
	}

	String getName(){
		return this.name;
	}

	int getScore(){
		return this.score;
	}

    void die(){
    	this.alive = false;
    }

    void gainScore(int increase){
    	this.score+=increase;
    	System.out.println("Score: "+score);
    }

    boolean isAlive(){
    	return this.alive;
    } 
}
