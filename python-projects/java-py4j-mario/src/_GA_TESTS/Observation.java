package _GA_TESTS;

import engine.core.MarioForwardModel;

public class Observation {

	public MarioForwardModel model;
	public static final int numObservations = 237;
	
	public ReceptiveField recField = new ReceptiveField();
	public int[][] solidBlocks = null;
	public int[][] platformBlocks = null;
	public int[][] stompableEnemies = null;
	public int[][] nonStompableEnemies = null;
	
	public Observation copyObservation() {
		Observation observation = new Observation();
		observation.model = model;
		observation.recField = recField;
		observation.solidBlocks = solidBlocks.clone();
		observation.platformBlocks = platformBlocks.clone();
		observation.stompableEnemies = stompableEnemies.clone();
		observation.nonStompableEnemies = nonStompableEnemies.clone();
		return observation;
	}

	public void updateModel(MarioForwardModel model) {
		this.model = model;
		updateFields();
	}

	private void updateFields() {
		solidBlocks = recField.getBlockReceptiveField(model);
		stompableEnemies = recField.getEnemyReceptiveFieldStompable(model);
		platformBlocks = recField.getPlatformReceptiveField(model);
		nonStompableEnemies = recField.getEnemyReceptiveFieldNonStompable(model);
	}
	
	public boolean[] getStateArray() {
		boolean[] result = new boolean[numObservations];
		result[0] = model.isMarioOnGround();
		result[1] = isMarioMovingFast();
		result[2] = isMarioMovingMediumFast();
		result[3] = isMarioMovingSlow();
		result[4] = model.isFacingRight();
		result[5] = model.getMarioCanJumpHigher();
		
		boolean[] fieldBools = getFieldBlocks();
		System.arraycopy(fieldBools, 0, result, 6, 44*2);
		boolean[] enemyBools = getEnemyBlocks();
		System.arraycopy(enemyBools, 0, result, 94, 66*2);
		boolean[] floorMarks = recField.getFloor(model);
		System.arraycopy(floorMarks, 0, result, 226, 11);
		return result;
	}
	
	public boolean isStompableEnemyDetectedCloseToTheRight() {
		boolean[] enemyBools = getEnemyBlocks();
		for(int i = 6*5; i < 6*8; i++) {
			if(enemyBools[i])
				return true;
		}
		return false;
	}
	
	public boolean isNonStompableEnemyDetectedCloseToTheRight() {
		int start = 66;
		boolean[] enemyBools = getEnemyBlocks();
		for(int i = 6*5 + start; i < 6*8 + start; i++) {
			if(enemyBools[i])
				return true;
		}
		return false;
	}
	
	public boolean isStompableEnemyDetectedToTheRight() {
		boolean[] enemyBools = getEnemyBlocks();
		for(int i = 6*5; i < 6*11; i++) {
			if(enemyBools[i])
				return true;
		}
		return false;
	}
	
	public boolean isNonStompableEnemyDetectedToTheRight() {
		int start = 66;
		boolean[] enemyBools = getEnemyBlocks();
		for(int i = 6*5 + start; i < 6*11 + start; i++) {
			if(enemyBools[i])
				return true;
		}
		return false;
	}
	
	public boolean isGapDetectedToTheRight() {
		boolean[] floorMarks = recField.getFloor(model);		
		for(int i = 6; i < 11; i++) {
			if(!floorMarks[i])
				return true;
		}
		return false;
	}
	
	public boolean isGapDetectedCloseToTheRight() {
		boolean[] floorMarks = recField.getFloor(model);
		for(int i = 6; i < 8; i++) {
			if(!floorMarks[i])
				return true;
		}
		return false;
	}
	
	public boolean isMarioMovingFast(){
		return Math.abs(model.getMarioFloatVelocity()[0]) >= 8.f;  //9.7 max
	}
	
	public boolean isMarioMovingMediumFast(){
		float velX = Math.abs(model.getMarioFloatVelocity()[0]);
		return velX >= 4.f && velX < 8.f;  //9.7 max
	}
	
	public boolean isMarioMovingSlow(){
		return Math.abs(model.getMarioFloatVelocity()[0]) < 4.f;  //9.7 max
	}
	
	private boolean[] getFieldBlocks() {
		boolean[] result = new boolean[44*2];
		int counter = 0;
		for (int i = 0; i < 5*2+1; i++) {
			for (int j = 0; j < 4; j++) {
				if (solidBlocks[i][j] != 0)
					result[counter] = true;
				counter++;
			}
		}
		for (int i = 0; i < 5*2+1; i++) {
			for (int j = 0; j < 4; j++) {
				if (platformBlocks[i][j] != 0)
					result[counter] = true;
				counter++;
			}
		}
		return result;
	}
	
	private boolean[] getEnemyBlocks() {
		boolean[] result = new boolean[66*2];
		int counter = 0;
		for (int i = 0; i < 5*2+1; i++) {
			for (int j = 0; j < 6; j++) {
				if (stompableEnemies[i][j] != 0)
					result[counter] = true;
				counter++;
			}
		}
		for (int i = 0; i < 5*2+1; i++) {
			for (int j = 0; j < 6; j++) {
				if (nonStompableEnemies[i][j] != 0)
					result[counter] = true;
				counter++;
			}
		}
		return result;
	}

}
