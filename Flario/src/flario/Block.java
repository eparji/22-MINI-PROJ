package flario;

import flario.Block;
import javafx.scene.image.Image;


public class Block extends Sprite{
    private final static double CHARACTER_SPEED = 1;
    private final static double BLOCK_WIDTH = 64;
    private final static double BLOCK_HEIGHT = 64;
    
    private final static Image BLOCK_IMAGE = new Image(Pipe.class.getResource("/images/block.png").toString(), BLOCK_WIDTH, BLOCK_HEIGHT, false, false);
    
    
    public Block(double positionX, double positionY) {
    	super(positionX, positionY, Block.BLOCK_WIDTH, Block.BLOCK_HEIGHT, BLOCK_IMAGE);
    }
    
    void move() {
		this.positionX = this.positionX-CHARACTER_SPEED;
	}

}
