package flario;

import flario.Pipe;
import javafx.scene.image.Image;

public class Pipe extends Sprite{
//<<<<<<< HEAD
    private double locationX;
    private double locationY;
    private double height;
    private double width;
    
    private final static double CHARACTER_SPEED = 1;
    private final static double PIPE_WIDTH = 100;
    private final static double PIPE_HEIGHT = 525;
    
    private final static Image BOTTOM_PIPE_IMAGE = new Image(Pipe.class.getResource("/sprites/new_pipe.png").toString(), PIPE_WIDTH, PIPE_HEIGHT, false, false);
    private final static Image TOP_PIPE_IMAGE = new Image(Pipe.class.getResource("/sprites/top_new_pipe.png").toString(), PIPE_WIDTH, PIPE_HEIGHT, false, false);
    
    public Pipe(double positionX, double positionY, boolean isFaceUp) {

	// Uses sprite constructor to create pipe, Uses top or bottom pipe image based on isFaceUpValue
    	super(positionX, positionY, Pipe.PIPE_WIDTH, Pipe.PIPE_HEIGHT, isFaceUp? BOTTOM_PIPE_IMAGE: TOP_PIPE_IMAGE);
    }

	// will remove soon in favor of upfatePositionXY in superclass Sprite
    void move() {
		this.positionX = this.positionX-CHARACTER_SPEED;
	}
}
