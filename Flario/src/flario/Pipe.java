package flario;

import flario.Pipe;
import javafx.scene.image.Image;

public class Pipe extends Sprite{
//<<<<<<< HEAD
    private double locationX;
    private double locationY;
    private double height;
    private double width;
    
    private final static Image PIPE_IMAGE = new Image("pipe.png");
//=======
//    private final static Image PIPE_IMAGE = new Image("file:src/sprites/pipe.png");
//>>>>>>> branch 'master' of https://github.com/eparji/22-MINI-PROJ.git
//    private final static Image PIPE_IMAGE = new Image("images/pipe.png");
    private final static double CHARACTER_SPEED = 1;
    private final static double PIPE_WIDTH = 100;
    private final static double PIPE_HEIGHT = 525;
    
    private final static Image BOTTOM_PIPE_IMAGE = new Image(Pipe.class.getResource("new_pipe.png").toString(), PIPE_WIDTH, PIPE_HEIGHT, false, false);
    private final static Image TOP_PIPE_IMAGE = new Image(Pipe.class.getResource("top_new_pipe.png").toString(), PIPE_WIDTH, PIPE_HEIGHT, false, false);
    
    public Pipe(double positionX, double positionY, boolean isFaceUp) {
//    	super(positionX, positionY, Pipe.PIPE_WIDTH, height, Pipe.PIPE_IMAGE);
//        //this.setSize(isFaceUp ? "/sprites/up_pipe.png" : "/sprites/down_pipe.png", 70, height);
//        this.width = 70;
//        this.height = height;
//        this.locationX = 400;
//        this.locationY = isFaceUp? 600 - height : 0;
//        this.resizeImage(PIPE_IMAGE.getUrl(), PIPE_WIDTH, height);
    	super(positionX, positionY, Pipe.PIPE_WIDTH, Pipe.PIPE_HEIGHT, isFaceUp? BOTTOM_PIPE_IMAGE: TOP_PIPE_IMAGE);

    }
    
    void move() {
		this.positionX = this.positionX-CHARACTER_SPEED;
	}

    //public Sprite getPipe() {
        //return pipe;
    //}
}
