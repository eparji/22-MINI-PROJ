package flario;

import flario.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	protected Image image;
	protected double positionX; 
	protected double positionY; 
	protected double velocityX; 
	protected double velocityY;
	protected double width;
	protected double height;
	protected boolean visible;

    public Sprite(double positionX, double positionY, double width, double height, Image image){
		this.positionX = positionX;
		this.positionY = positionY;
		this.setImage(image);
		this.visible = true;
		this.setSize(width, height);
	}

	public Rectangle2D getBounds(){
		return new Rectangle2D(positionX, positionY, width, height);
	}
	
	private void setSize(double width, double height){
		this.width = width;
        this.height = height;
	}
	
	public void setImage(Image image) {
        this.image = image;
        setSize(image.getWidth(), image.getHeight());
    }
	
	public void resizeImage(String filepath, double width, double height) {
        Image toReturn = new Image(filepath, width, height, false, false);
        setImage(toReturn);
    }
	
	protected boolean collidesWith(Sprite s)	{
		return s.getBounds().intersects(this.getBounds());
	}
	
	public void render(GraphicsContext gc){
        gc.drawImage( this.image, this.positionX, this.positionY );
    }

	public Image getImage(){
		return this.image;
	}

	public void setPositionXY(double X, double Y) {
		this.positionX = X;
		this.positionY = Y;
	}
	
	
	public double getPositionX(){
		return positionX;
	}

	
	public double getPositionY(){
		return positionY;
	}
	

	public void setVelocityX(double X){
		this.velocityX = X;
	}
	
	public void setVelocityY(double Y) {
		this.velocityY = Y;
	}
	
	public double getVelocityX() {
		return velocityX;
	}
	
	public double getVelocityY() {
		return velocityY;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
    void updatePosition() {
    	//if(this.yPos+this.dy >= 0 && this.yPos+this.dy <= Game.WINDOW_HEIGHT-this.height)
			//this.yPos += this.dy;
    	if(this.positionX+this.velocityX >= 0 && this.positionX+this.velocityX <= Game.WINDOW_WIDTH-this.width) {
			this.positionX += this.velocityX;
    	}
    	
    	this.positionY += this.velocityY;
	}
    
	public boolean isVisible(){
		return visible;
	}
	
	public void vanish(){
		this.visible = false;
	}
}
