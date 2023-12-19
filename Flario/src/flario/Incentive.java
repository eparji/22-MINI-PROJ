package flario;

import javafx.scene.image.Image;

public class Incentive extends Sprite {
	
    private final static double INCENTIVE_WIDTH = 64;
    private final static double INCENTIVE_HEIGHT = 64;
    
    private final static Image MODULE_IMAGE = new Image(Module.class.getResource("/sprites/incentive.png").toString(), INCENTIVE_WIDTH, INCENTIVE_HEIGHT, false, false);
  
    public Incentive(double positionX, double positionY) {
    	super(positionX, positionY, Incentive.INCENTIVE_WIDTH, Incentive.INCENTIVE_HEIGHT, MODULE_IMAGE);
    }
	    
}
