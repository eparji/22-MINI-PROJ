package flario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MiniWindow extends AnimationTimer{
	private Group root;
	private Stage stage;
	private Canvas canvas;
	private ArrayList<TimeIncrement> collectibles;
	private Scene scene;
	private GraphicsContext gc;
	private Image background = new Image( "minigame_bg.png",1080,1920,true,false );
	
	public final static int TIME_TYPES = 4;
	
	// creates an instance of the miniwindow bject
	public MiniWindow(){
		this.root = new Group();
		this.scene = new Scene(root,this.background.getWidth(),this.background.getHeight());
		this.canvas = new Canvas(this.background.getWidth(),this.background.getHeight());
		this.gc = this.canvas.getGraphicsContext2D();;
    	this.stage = new Stage();
    	this.setStage();
	}
	
	// prints out the time/clickables
	private void renderTime() {
		 Iterator<TimeIncrement> timeIter = collectibles.iterator();
         while ( timeIter.hasNext() )
         {
             TimeIncrement collect = timeIter.next();
             this.root.getChildren().add(collect);
         }
	}
	
	// method for spawning (i.e. placing, typing)
	private void spawnTime(){
		double xPos, yPos;
		int type;
		Random r = new Random();
		this.collectibles= new ArrayList<TimeIncrement>();
		
		int timeCount = 20;
		for(int i=0;i<timeCount;i++){ // twenty collectibles spawn 
			
			type = r.nextInt(MiniWindow.TIME_TYPES); // gets time type(1,2,3,5)
			xPos = r.nextDouble(this.scene.getWidth()); // obtains xpos
			yPos = r.nextDouble(this.scene.getHeight());// ypos
			TimeIncrement newCollect = new TimeIncrement(xPos, yPos, type); // places clickable
			this.collectibles.add(newCollect); // adds to arraylist
		}
	}
	
	// sets stage for the miniwindow
	public void setStage() {
		this.root.getChildren().add(this.canvas);
		this.stage.setTitle("Mini Game na !!"); // title of window
		this.stage.setScene(this.scene); 
		this.addComponents();
		this.scene.getRoot().requestFocus(); // focuses on window
		GameStage.playminigamemusic(); // plays minigame music
		this.stage.show();
		PauseTransition delay = new PauseTransition(Duration.seconds(5)); // pauses gametimer for 5 seconds
		delay.play();
		delay.setOnFinished(event -> { // closes window after 5 seconds
			this.stage.close();
			GameStage.playgamemusic();
		});
	}
	
	// handler for spawning and eventhandler for being clicked
	private void setHandler() {
        Iterator<TimeIncrement> colIter = this.collectibles.iterator();
        while ( colIter.hasNext() ){
            TimeIncrement time = colIter.next();

            time.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					pressed("clicked", time);
					}
            	});
            }
	}
	
	// checker
	private void pressed(String msg, TimeIncrement btn) {
		// TODO Auto-generated method stub
		if(msg.contentEquals("clicked")) {
			System.out.println("Added a total of " + btn.getIncrement() + " seconds to gametime already");
			this.root.getChildren().remove(btn);
			this.increaseTime(btn.getIncrement());
		}
	}
	
	private void increaseTime(int time) {
		GameTimer.setGameTime(time);
		System.out.println("time added: " + time);
	}
	
	private void addComponents(){
		this.gc.drawImage(this.background, 0, 0);
		this.spawnTime();
		this.renderTime();
		this.setHandler();
	}
	
	@Override
	public void handle(long arg0) {
		// TODO Auto-generated method stub
	}


}
