package flario;
import javafx.scene.image.Image;


public class Module extends Sprite {
	
	    private final static double MODULE_WIDTH = 64;
	    private final static double MODULE_HEIGHT = 64;
	    
	    private final static Image MODULE_IMAGE = new Image(Module.class.getResource("/sprites/module.png").toString(), MODULE_WIDTH, MODULE_HEIGHT, false, false);
	  
	    public Module(double positionX, double positionY) {
	    	super(positionX, positionY, Module.MODULE_WIDTH, Module.MODULE_HEIGHT, MODULE_IMAGE);
	    }
	    
}
