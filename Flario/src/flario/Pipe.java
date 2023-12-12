package flario;

import flario.Pipe;
import javafx.scene.image.Image;

public class Pipe extends Sprite{
    private double locationX;
    private double locationY;
    private double height;
    private double width;
    
    private final static Image PIPE_IMAGE = new Image("file:C:/Users/ASUS/git/22-MINI-PROJ/Flario/src/images/pipe.png");
//    private final static Image PIPE_IMAGE = new Image("images/pipe.png");
    private final static double PIPE_WIDTH = 128;

    public Pipe(double positionX, double positionY, boolean isFaceUp, int height) {
    	super(positionX, positionY, Pipe.PIPE_WIDTH, height, Pipe.PIPE_IMAGE);
        //this.setSize(isFaceUp ? "/images/up_pipe.png" : "/images/down_pipe.png", 70, height);
        this.width = 70;
        this.height = height;
        this.locationX = 400;
        this.locationY = isFaceUp? 600 - height : 0;
        this.resizeImage(PIPE_IMAGE.getUrl(), PIPE_WIDTH, height);
    }

    //public Sprite getPipe() {
        //return pipe;
    //}
}
