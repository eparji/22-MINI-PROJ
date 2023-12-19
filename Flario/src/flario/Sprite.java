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
		this.loadImage(image);
		this.visible = true;
		this.width = width;
		this.height = height;
	
	
	}

	public Rectangle2D getBounds(){
		return new Rectangle2D(positionX, positionY, width, height);
	}
	
	public void drawBounds(GraphicsContext gc) {
    	gc.strokeRect(this.getBounds().getMinX(), this.getBounds().getMinY(), this.getBounds().getWidth(), this.getBounds().getHeight());
    }
	
	private void setSize(){
		this.width = this.image.getWidth();
        this.height = this.image.getHeight();
	}
	
	public void loadImage(Image image) {
		try{
			this.image = image;
	        this.setSize();
		} catch(Exception e)	{
			e.printStackTrace();
		}
        
    }
	
	protected boolean collidesWith(Sprite s){
		  
		Rectangle2D rectangle1 = this.getBounds(); 
		Rectangle2D rectangle2 = s.getBounds();
	  
		return rectangle1.intersects(rectangle2);
	  
	}
	
	
	
	public void setImage(Image image) {
        this.image = image;
        setSize();
    }
	
	public void resizeImage(String filepath, double width, double height) {
        Image toReturn = new Image(filepath, width, height, false, false);
        setImage(toReturn);
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
    	if(this.positionY+this.velocityY >= 0 && this.positionY+this.velocityY <= GameStage.WINDOW_HEIGHT-this.height)
			this.positionY += this.velocityY;
//    	if(this.positionX+this.velocityX >= 0 && this.positionX+this.velocityX <= GameStage.WINDOW_WIDTH-this.width) {
//			this.positionX += this.velocityX;
//    	}
    	this.positionX += this.velocityX;
//    	this.positionY += this.velocityY;
	}
    
	public boolean isVisible(){
		return visible;
	}
	
	public void vanish(){
		this.visible = false;
	}
}
