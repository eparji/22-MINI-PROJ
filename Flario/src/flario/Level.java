package flario;

import flario.Level;
import java.util.ArrayList;
import java.util.Random;

public class Level {
	public ArrayList<Pipe> pipes; //array list of bottom pipes
	public ArrayList<Pipe> topPipes; //array list of upper pipes
	public ArrayList<Block> blocks;
	public int[][] groundIntervals = new int[50][2]; 
	public int[][] flyingIntervals = new int[50][2];
	public int groundIntervalCounter, flyingIntervalCounter;
	public int finishDistance;
	
	private final static int LEVEL_LENGTH = 5120; //10240;
	private final static int OBSTACLE_INTERVAL = 256;
	private final static int PIPE_DISTANCE = 750;
	
	public Level() {
		this.finishDistance = LEVEL_LENGTH;
		this.groundIntervalCounter = 0;
		this.flyingIntervalCounter = 0;
    	this.pipes = new ArrayList<Pipe>();
    	this.topPipes = new ArrayList<Pipe>();
    	this.blocks = new ArrayList<Block>();
		this.generateLevel();
	}
	
	public void generateLevel() {
		Random r = new Random();
		int currentDistance = 0;
		int areaLength;
		int pipeHeight, blockHeight, blockOffset;
		
		while(currentDistance <= LEVEL_LENGTH) {
			areaLength = r.nextInt(50) * 64;
			this.setGroundArea(currentDistance, currentDistance + areaLength);
			currentDistance += areaLength;
			
			areaLength = r.nextInt(50) * 64;
			this.setFlyingArea(currentDistance, currentDistance + areaLength);
			currentDistance += areaLength;
		}
		
		currentDistance = 1000;
		boolean isGroundArea=true;
		
		while(currentDistance <= LEVEL_LENGTH) {
			System.out.println(currentDistance);
			for(int i = 0; i < this.groundIntervalCounter; i++) {
				if(this.groundIntervals[i][0] < currentDistance && currentDistance < this.groundIntervals[i][1]) {
					isGroundArea = true;
				}
			}
			for(int i = 0; i < flyingIntervalCounter; i++) {
				if(this.flyingIntervals[i][0] < currentDistance && currentDistance < this.flyingIntervals[i][1]) {
					isGroundArea = false;
				}
			}
			
			if(isGroundArea) {
				for(int i = 0; i<4; i++) {
					blockHeight = (r.nextInt(10)+2)*64;
					addBlock(currentDistance+(64*i), blockHeight);
				}
			}
			if(!isGroundArea) {
				pipeHeight = (r.nextInt(44)+20)*10;
				addPipes(currentDistance, pipeHeight, pipeHeight-PIPE_DISTANCE);
			}
			currentDistance += OBSTACLE_INTERVAL;
		}
	}
	
	
	// add a new pair of pipes
	private void addPipes(int xPos, int bottomPos, int topPos) {
		this.pipes.add(new Pipe(xPos, bottomPos, true));
		this.topPipes.add(new Pipe(xPos, topPos, false));
	}
	
	
	// add a block
	private void addBlock(int xPos, int yPos) {
		this.blocks.add(new Block(xPos, yPos));
	}
	
	
	private void setGroundArea(int start, int end) {
		int[] newInterval = {start, end};
		this.groundIntervals[groundIntervalCounter] = newInterval;
		groundIntervalCounter += 1;
	}
	
	private void setFlyingArea(int start, int end) {
		int[] newInterval = {start, end};
		this.flyingIntervals[flyingIntervalCounter] = newInterval;
		flyingIntervalCounter += 1;
	}
}
