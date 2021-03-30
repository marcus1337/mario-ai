package _GA_TESTS;

import engine.core.MarioForwardModel;

public class ReceptiveField {

	public final int lenX = 5*2+1;
	public final int lenY = 4;
	//private final int midX = 16;
	private final int midX = 12;
	private final int midY = 8;


	private int[][] getRightReceptiveField(int[][] observations, boolean isPlatformBlock) {
		int[][] field = new int[lenX][lenY];
		int xBegin = midX;
		int yBegin = midY - (int) Math.ceil((float) lenY / 2) - 1;		
		for (int y = yBegin; y < yBegin + lenY; y++) {
			for (int x = xBegin; x < xBegin + lenX; x++) {
				int obsValue = observations[x-5][y];
				if(isPlatformBlock && obsValue == MarioForwardModel.OBS_PLATFORM)
					field[x - xBegin][y - yBegin] = 1;
				else if(!isPlatformBlock && obsValue != MarioForwardModel.OBS_PLATFORM && obsValue != 0)
					field[x - xBegin][y - yBegin] = 1;
			}
		}
		return field;
	}
	
	private int[][] getEnemyReceptiveField(int[][] observations) {
		int[][] field = new int[lenX][lenY+2];
		int xBegin = midX;
		int yBegin = midY - (int) Math.ceil((float) lenY / 2) - 1;
		
		for (int y = yBegin; y < yBegin + lenY+2; y++) {
			for (int x = xBegin; x < xBegin + lenX; x++) {
				field[x - xBegin][y - yBegin] = observations[x-5][y];
			}
		}
		return field;
	}
	
	public boolean[] getFloor(MarioForwardModel model){
		int[][] observations = model.getMarioSceneObservation();
		for(int i = 0 ; i < observations[0].length; i++){
			for(int j = 0 ; j < observations.length; j++){
				if(observations[j][i] == MarioForwardModel.OBS_COIN)
					observations[j][i] = 0;
			}
		}

		boolean[] result = new boolean[11];
		
		int xBegin = midX;
		int yBegin = midY - (int) Math.ceil((float) lenY / 2) - 1;
		int counter = 0;
		int maxY = observations[0].length;
		
		for (int x = xBegin; x < xBegin + lenX; x++) {
			for (int y = yBegin+4; y < yBegin + 20 + 4; y++) {
				if(y < maxY && observations[x-5][y] != 0){
					result[counter] = true;
				}
			}
			counter++;
		}		
		
		return result;
	}
	
	public int[][] getBlockReceptiveField(MarioForwardModel model) {
		int[][] observations = getSceneObservation(model);
		return getRightReceptiveField(observations, false);
	}
	
	public int[][] getPlatformReceptiveField(MarioForwardModel model) {
		int[][] observations = getSceneObservation(model);
		return getRightReceptiveField(observations, true);
	}

	private int[][] getSceneObservation(MarioForwardModel model) {
		int[][] observations = model.getMarioSceneObservation(1);
		for(int i = 0 ; i < observations[0].length; i++){
			for(int j = 0 ; j < observations.length; j++){
				if(observations[j][i] == MarioForwardModel.OBS_COIN)
					observations[j][i] = 0;
			}
		}
		return observations;
	}

	public int[][] getEnemyReceptiveFieldStompable(MarioForwardModel model) {
		int[][] observations = getEnemyObservation(model);
		return extractEnemiesOfType(observations, MarioForwardModel.OBS_STOMPABLE_ENEMY);
	}

	private int[][] extractEnemiesOfType(int[][] observations, int enemyType) {
		int[][] field = getEnemyReceptiveField(observations);
		for(int i = 0 ; i < field[0].length; i++){
			for(int j = 0 ; j < field.length; j++){
				if(field[j][i] == enemyType)
					field[j][i] = 1;
				else
					field[j][i] = 0;
			}
		}
		return field;
	}
	
	public int[][] getEnemyReceptiveFieldNonStompable(MarioForwardModel model) {
		int[][] observations = getEnemyObservation(model);
		return extractEnemiesOfType(observations, MarioForwardModel.OBS_NONSTOMPABLE_ENEMY);
	}

	private int[][] getEnemyObservation(MarioForwardModel model) {
		int[][] observations = model.getMarioEnemiesObservation(1);
		for(int i = 0 ; i < observations[0].length; i++){
			for(int j = 0 ; j < observations.length; j++){
				if(observations[j][i] == MarioForwardModel.OBS_FIREBALL || observations[j][i] == MarioForwardModel.OBS_SPECIAL_ITEM)
					observations[j][i] = 0;
			}
		}
		return observations;
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
