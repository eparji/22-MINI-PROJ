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
	public static int finishDistance;
	
	private final static int LEVEL_LENGTH = 5120; //10240;
	private final static int OBSTACLE_INTERVAL = 256;
	private final static int PIPE_DISTANCE = 750;
	
	public static int currentDistance;
	
	public Level() {
		Level.currentDistance = 0;
		Level.finishDistance = LEVEL_LENGTH;
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
		int pipeHeight, blockHeight, n;
		int lastObstacleHeight = GameTimer.GROUND_POSITION-64;
		
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
					n = r.nextInt(3);
					if(n == 1) {
						blockHeight = lastObstacleHeight - 128;
					}
					else if(n == 2) {
						blockHeight = lastObstacleHeight + 128;
					}
					else {
						blockHeight = lastObstacleHeight;
					}
					
					if(blockHeight < 64) {
						blockHeight += 128;
					}
					if(blockHeight > GameTimer.GROUND_POSITION-64) {
						blockHeight -= 128;
					}

					n = r.nextInt(3);
					if(n != 1) {
					addBlock(currentDistance+(64*i), blockHeight);
					}
					lastObstacleHeight = blockHeight;
				}
			}
			if(!isGroundArea) {
				pipeHeight = lastObstacleHeight + (r.nextInt(32)-64)*4;
				
				if(pipeHeight < 256) {
					pipeHeight += 256;
				}
				if(pipeHeight > GameTimer.GROUND_POSITION+192) {
					pipeHeight -= 256;
				}
				
				addPipes(currentDistance, pipeHeight, pipeHeight-PIPE_DISTANCE);

				lastObstacleHeight = pipeHeight;
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
	
//	public static boolean isFinished(double xPos) {
//		System.out.println(xPos);
//		return (xPos >= finishDistance);
//	}
}
