package _GA_TESTS;

import engine.core.MarioForwardModel;
import engine.helper.MarioActions;

public class Observation {

	private MarioForwardModel model;
	public static final int numObservations = 237;

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

	private ReceptiveField recField = new ReceptiveField();
	private int[][] solidBlocks = null;
	private int[][] platformBlocks = null;
	private int[][] stompableEnemies = null;
	private int[][] nonStompableEnemies = null;
	
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
	
	private boolean isMarioMovingFast(){
		return Math.abs(model.getMarioFloatVelocity()[0]) >= 8.f;  //9.7 max
	}
	
	private boolean isMarioMovingMediumFast(){
		float velX = Math.abs(model.getMarioFloatVelocity()[0]);
		return velX >= 4.f && velX < 8.f;  //9.7 max
	}
	
	private boolean isMarioMovingSlow(){
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
