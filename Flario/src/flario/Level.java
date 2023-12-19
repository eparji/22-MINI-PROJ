package flario;

import flario.Level;
import java.util.ArrayList;
import java.util.Random;

public class Level {
	public ArrayList<Pipe> pipes;    //array list of bottom pipes
	public ArrayList<Pipe> topPipes; //array list of upper pipes
	public ArrayList<Block> blocks;  //array list of blocks
	
	// arraylists of powerups/debuffs
	public ArrayList<Module> modules;
	public ArrayList<Incentive> incentives;
	public ArrayList<StackOverflow> stacks;
	public ArrayList<ChangeMode> change;

	// arrays of obstacles/terrains
	public int[][] groundIntervals = new int[50][2]; 
	public int[][] flyingIntervals = new int[50][2];
	public int groundIntervalCounter, flyingIntervalCounter;
	public int finishDistance;

	
	private final static int LEVEL_LENGTH = 5120; //10240;
	private final static int OBSTACLE_INTERVAL = 256;
	private final static int PIPE_DISTANCE = 704;
	private final static int BUFF_HEIGHT = 64;
	private final static int BUFF_RARITY = 10; // must be at least 4
	
	public Level() {
		this.finishDistance = LEVEL_LENGTH-720;
		this.groundIntervalCounter = 0;
		this.flyingIntervalCounter = 0;
		this.pipes = new ArrayList<Pipe>();
		this.topPipes = new ArrayList<Pipe>();
		this.blocks = new ArrayList<Block>();
		this.modules = new ArrayList<Module>(); 
		this.incentives = new ArrayList<Incentive>();
		this.stacks = new ArrayList<StackOverflow>();
		this.change = new ArrayList<ChangeMode>();
		this.generateLevel();
	}
	
	// method for world generation
	public void generateLevel() {
		Random r = new Random(); // random number generator
		int currentDistance = 0; // initialize curr area as 0
		int areaLength;
		int pipeHeight, blockHeight, n;
		int lastObstacleHeight = GameTimer.GROUND_POSITION-64; // last obstacle placed
		int buffType;
		boolean isGroundArea, switchedAreas;
		
		while(currentDistance <= LEVEL_LENGTH) { // while area is not filled (5120px)
			
			// block rendering area
			areaLength = (r.nextInt(32)+8) * 64;
			this.setGroundArea(currentDistance, currentDistance + areaLength);
			currentDistance += areaLength;
			
			// pipe rendering area
			areaLength = (r.nextInt(32)+8) * 64;
			this.setFlyingArea(currentDistance, currentDistance + areaLength);
			currentDistance += areaLength;
		}
		
		currentDistance = 1024; // sets value of curr distance to 1024px
		isGroundArea=true;
		
		while(currentDistance <= LEVEL_LENGTH) {
			switchedAreas = false;
			System.out.println(currentDistance);
			for(int i = 0; i < this.groundIntervalCounter; i++) {
				if(this.groundIntervals[i][0] < currentDistance && currentDistance < this.groundIntervals[i][1]) {
					isGroundArea = true;
				}
			}
			for(int i = 0; i < flyingIntervalCounter; i++) {
				if(this.flyingIntervals[i][0] < currentDistance && currentDistance < this.flyingIntervals[i][1]) {
					if(isGroundArea || (this.groundIntervals[i+1][0] <= (currentDistance + OBSTACLE_INTERVAL) && (currentDistance + OBSTACLE_INTERVAL) <= this.groundIntervals[i+1][1])) {
						switchedAreas = true;
					}
					isGroundArea = false;
				}
			}
			
			if(isGroundArea) { // block laying
				for(int i = 0; i<4; i++) {
					n = r.nextInt(4);
					if(n == 1) { // 25% chance to be 128px higher
						blockHeight = lastObstacleHeight - 128;
					}
					else if(n == 2) { //25% chance to be 128px lower
						blockHeight = lastObstacleHeight + 128;
					}
					else { // 50% chance to be of same height with prev
						blockHeight = lastObstacleHeight;
					}
					
					if(blockHeight < 128) {
						blockHeight += 128;
					}
					if(blockHeight > GameTimer.GROUND_POSITION-64) {
						blockHeight -= 128;
					}

					n = r.nextInt(4);
					if(n != 1) { // 25% chance that a block would spawn beside
						addBlock(currentDistance+(64*(i-1)), blockHeight);
					}
					lastObstacleHeight = blockHeight;
				}
			}
			
			// pipe laying
			if(!isGroundArea) {
				pipeHeight = lastObstacleHeight + (r.nextInt(2)-4)*64;
				
				if(pipeHeight < 256) {
					pipeHeight += 324;
				}
				if(pipeHeight > GameTimer.GROUND_POSITION) {
					pipeHeight -= 64;
				}
				
				addPipes(currentDistance, pipeHeight, pipeHeight-PIPE_DISTANCE);

				lastObstacleHeight = pipeHeight;
			}
			
			// buff spawning
			buffType = r.nextInt(BUFF_RARITY);
			
			// switch mode
			if(switchedAreas) {
				this.change.add(new ChangeMode(currentDistance+32, lastObstacleHeight - (3 * BUFF_HEIGHT)));
			}
			else if(buffType == 1) { //spawn incentive buff
				this.incentives.add(new Incentive(currentDistance + OBSTACLE_INTERVAL - 64, lastObstacleHeight - (2*BUFF_HEIGHT)));
			}
			else if(buffType == 2){ //spawn module buff
				this.modules.add(new Module(currentDistance + OBSTACLE_INTERVAL - 64, lastObstacleHeight - (2*BUFF_HEIGHT)));
			}
			else if (buffType == 3){ //spawn minigame buff
				this.stacks.add(new StackOverflow(currentDistance + OBSTACLE_INTERVAL - 64, lastObstacleHeight - (2*BUFF_HEIGHT)));
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
	
	// gets interval of ground area
	private void setGroundArea(int start, int end) {
		int[] newInterval = {start, end};
		this.groundIntervals[groundIntervalCounter] = newInterval;
		groundIntervalCounter += 1;
	}
	
	// interval of flying area
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
