package _GA_TESTS;

import engine.core.MarioForwardModel;

public class ReceptiveField {

	public final int lenX = 5;
	public final int lenY = 6;
	private final int midX = 16;
	private final int midY = 8;
	
	private int[][] getRightReceptiveField(int[][] observations){
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
	private int[][] getLefttReceptiveField(int[][] observations){
		int[][] field = new int[lenX][lenY];
		int xBegin = midX;
		int yBegin = midY - (int) Math.ceil((float) lenY / 2);
		for (int y = yBegin; y < yBegin + lenY; y++) {
			for (int x = xBegin-lenX+1; x <= xBegin; x++) {
				field[x - xBegin+lenX-1][y - yBegin] = observations[x][y];
			}
		}
		mirrorReceptiveField(field);
		return field;
	}
	
	private void swapArrayElems(int[][] arr, int i, int j, int y){
		int tmp = arr[i][y];
		arr[i][y] = arr[j][y];
		arr[j][y] = tmp;
	}
	
	private void mirrorReceptiveField(int[][] field){
		for(int y = 0; y < lenY; y++){
			int swapDist = lenX-1;
			for(int x= 0; x < lenX/2; x++){
				swapArrayElems(field,x,x+swapDist,y);
				swapDist-=2;
			}
		}
	}
	
	private int[][] getField(MarioForwardModel model, int[][] observations){
		int[][] field = null;
		if(model.isFacingRight())
			field = getRightReceptiveField(observations);
		else
			field = getLefttReceptiveField(observations);
		return field;
	}

	public int[][] getReceptiveField(MarioForwardModel model) {					
		int[][] observations = model.getMarioCompleteObservation(0, 0);
		return getField(model, observations);
	}
	
	public int[][] getEnemyReceptiveField(MarioForwardModel model) {					
		int[][] observations = model.getMarioEnemiesObservation();
		return getField(model, observations);
	}
	
	public boolean tunneledFieldContains(int[][] field, int itemValue){
		for(int x = 0; x < lenX; x++){
			for(int y = 2; y < lenY-2; y++){
				if(field[x][y] == itemValue)
					return true;
			}
		}
		return false;
	}
	
	public boolean levelledFieldContains(int[][] field, int itemValue){
		for(int x = 0; x < lenX; x++){
			for(int y = 0; y < lenY-2; y++){
				if(field[x][y] == itemValue)
					return true;
			}
		}
		return false;
	}
	
	public boolean fieldContains(int[][] field, int itemValue){
		for(int x = 0; x < lenX; x++){
			for(int y = 0; y < lenY; y++){
				if(field[x][y] == itemValue)
					return true;
			}
		}
		return false;
	}
	
	public boolean fieldContainsAnything(int[][] field){
		for(int x = 0; x < lenX; x++){
			for(int y = 0; y < lenY; y++){
				if(field[x][y] != 0)
					return true;
			}
		}
		return false;
	}
	
	public void printReceptiveField(int[][] field){
		System.out.println("-------------------");
		for(int y = 0; y < lenY; y++){
			for(int x = 0; x < lenX; x++){
				System.out.print(field[x][y] + ",");
			}
			System.out.println();
		}
		System.out.println("-------------------");
	}
}
