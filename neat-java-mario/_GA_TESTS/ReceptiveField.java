package _GA_TESTS;

import java.util.ArrayList;

import engine.core.MarioForwardModel;

public class ReceptiveField {

	public final int lenX = 5;
	public final int lenY = 6;
	//private final int midX = 16;
	private final int midX = 12;
	private final int midY = 8;

	ArrayList<Integer> getObstacleValues() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		result.add(MarioForwardModel.OBS_BRICK);
		result.add(MarioForwardModel.OBS_PIPE);
		result.add(MarioForwardModel.OBS_PIPE_BODY_LEFT);
		result.add(MarioForwardModel.OBS_PIPE_BODY_RIGHT);
		result.add(MarioForwardModel.OBS_PIPE_TOP_LEFT);
		result.add(MarioForwardModel.OBS_PIPE_TOP_RIGHT);
		result.add(MarioForwardModel.OBS_CANNON);
		result.add(MarioForwardModel.OBS_SOLID);
		result.add(MarioForwardModel.OBS_PYRAMID_SOLID);
		return result;
	}

	public ArrayList<Integer> obstacleValues;
	private boolean alwaysRight = false;
	public ReceptiveField() {
		obstacleValues = getObstacleValues();
	}
	public ReceptiveField(boolean alwaysRight) {
		this.alwaysRight = alwaysRight;
		obstacleValues = getObstacleValues();
	}

	private int[][] getRightReceptiveField(int[][] observations) {
		int[][] field = new int[lenX][lenY];
		int xBegin = midX;
		int yBegin = midY - (int) Math.ceil((float) lenY / 2);
		for (int y = yBegin; y < yBegin + lenY; y++) {
			for (int x = xBegin; x < xBegin + lenX; x++) {
				field[x - xBegin][y - yBegin] = observations[x][y];
			}
		}
		return field;
	}

	private int[][] getLefttReceptiveField(int[][] observations) {
		int[][] field = new int[lenX][lenY];
		int xBegin = midX;
		int yBegin = midY - (int) Math.ceil((float) lenY / 2);
		for (int y = yBegin; y < yBegin + lenY; y++) {
			for (int x = xBegin - lenX + 1; x <= xBegin; x++) {
				field[x - xBegin + lenX - 1][y - yBegin] = observations[x][y];
			}
		}
		mirrorReceptiveField(field);
		return field;
	}

	private void swapArrayElems(int[][] arr, int i, int j, int y) {
		int tmp = arr[i][y];
		arr[i][y] = arr[j][y];
		arr[j][y] = tmp;
	}

	public void mirrorReceptiveField(int[][] field) {
		for (int y = 0; y < lenY; y++) {
			int swapDist = lenX - 1;
			for (int x = 0; x < lenX / 2; x++) {
				swapArrayElems(field, x, x + swapDist, y);
				swapDist -= 2;
			}
		}
	}

	private int[][] getField(MarioForwardModel model, int[][] observations) {
		int[][] field = null;
		if (alwaysRight || model.isFacingRight())
			field = getRightReceptiveField(observations);
		else
			field = getLefttReceptiveField(observations);
		return field;
	}

	public int[][] getReceptiveField(MarioForwardModel model) {
		int[][] observations = model.getMarioCompleteObservation(0, 0);
		return getField(model, observations);
	}
	
	public int[][] getBlockReceptiveField(MarioForwardModel model) {
		int[][] observations = model.getMarioSceneObservation();
		for(int i = 0 ; i < observations[0].length; i++){
			for(int j = 0 ; j < observations.length; j++){
				if(observations[j][i] == MarioForwardModel.OBS_COIN)
					observations[j][i] = 0;
			}
		}
		return getField(model, observations);
	}

	public int[][] getEnemyReceptiveField(MarioForwardModel model) {
		int[][] observations = model.getMarioEnemiesObservation();
		for(int i = 0 ; i < observations[0].length; i++){
			for(int j = 0 ; j < observations.length; j++){
				if(observations[j][i] == MarioForwardModel.OBS_FIREBALL)
					observations[j][i] = 0;
			}
		}
		return getField(model, observations);
	}
	
	public boolean upIsPushable(int[][] field){
		for(int i = 0 ; i < 3; i++)
			if (field[0][i] == MarioForwardModel.OBS_QUESTION_BLOCK || field[0][i] == MarioForwardModel.OBS_BRICK)
				return true;
		return false;
	}

	public boolean tunneledFieldContains(int[][] field, int itemValue) {
		for (int x = 0; x < lenX; x++) {
			for (int y = 2; y < lenY - 2; y++) {
				if (field[x][y] == itemValue)
					return true;
			}
		}
		return false;
	}

	public boolean levelledFieldContains(int[][] field, int itemValue) {
		for (int x = 0; x < lenX; x++) {
			for (int y = 0; y < lenY - 2; y++) {
				if (field[x][y] == itemValue)
					return true;
			}
		}
		return false;
	}

	public boolean fieldContains(int[][] field, int itemValue) {
		for (int x = 0; x < lenX; x++) {
			for (int y = 0; y < lenY; y++) {
				if (field[x][y] == itemValue)
					return true;
			}
		}
		return false;
	}

	public boolean fieldContainsAnything(int[][] field) {
		for (int x = 0; x < lenX; x++) {
			for (int y = 0; y < lenY; y++) {
				if (field[x][y] != 0)
					return true;
			}
		}
		return false;
	}

	public boolean isGroundUnder(int[][] field) {
		if (field[0][4] == 0 && field[0][5] == 0)
			return false;
		return true;
	}

	public boolean isGapAhead(int[][] field) {
		if (isGroundUnder(field)) {
			for (int x = 0; x < lenX; x++) {
				if (field[x][3] == 0 && field[x][4] == 0 && field[x][5] == 0)
					return true;
			}
		}
		return false;
	}

	public void printReceptiveField(int[][] field) {
		System.out.println("-------------------");
		for (int y = 0; y < lenY; y++) {
			for (int x = 0; x < lenX; x++) {
				System.out.print(field[x][y] + ",");
			}
			System.out.println();
		}
		System.out.println("-------------------");
	}
}