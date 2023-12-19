package flario;

import javafx.scene.image.Image;

public class StackOverflow extends Sprite {
	
	 private final static double SO_WIDTH = 64;
	 private final static double SO_HEIGHT = 64;
	    
	 private final static Image SO_IMAGE = new Image(Module.class.getResource("/sprites/stack_overflow.png").toString(), SO_WIDTH, SO_HEIGHT, false, false);
	  
	    
	    
	 public StackOverflow(double positionX, double positionY) {
		 super(positionX, positionY, StackOverflow.SO_WIDTH, StackOverflow.SO_HEIGHT, SO_IMAGE);
	 }
}
