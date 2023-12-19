package flario;

import javafx.scene.image.Image;

public class ChangeMode extends Sprite {
	
	private final static double CM_WIDTH = 64;
    private final static double CM_HEIGHT = 192;
	    
    private final static Image CM_IMAGE = new Image(ChangeMode.class.getResource("/sprites/changeMode.png").toString(), CM_WIDTH, CM_HEIGHT, false, false);
	  
	    
	    
    public ChangeMode(double positionX, double positionY) {
	    super(positionX, positionY, ChangeMode.CM_WIDTH, ChangeMode.CM_HEIGHT, CM_IMAGE);
    }
}
