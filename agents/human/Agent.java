package agents.human;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;

public class Agent extends KeyAdapter implements MarioAgent {
	private boolean[] actions = null;
	private final int recLenX = 5;
	private final int recLenY = 6;
	private final int midX = 16;
	private final int midY = 8;

	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {
		actions = new boolean[MarioActions.numberOfActions()];
	}

	private int[] getMarioBoundedPos(MarioForwardModel model) {
		int[][] observ = model.getMarioSceneObservation();
		int maxXPos = observ.length;
		int maxYPos = observ[0].length;
		int[] pos = model.getMarioScreenTilePos();
		pos[0] = Math.max(0, pos[0]);
		pos[0] = Math.min(pos[0], maxXPos - 1);
		pos[1] = Math.max(0, pos[1]);
		pos[1] = Math.min(pos[1], maxYPos - 1);
		return pos;
	}

	/*
	 * int mapBoolToInt(boolean val){ if(val) return 1; return 0; }
	 */

	int[][] observations = null;
	int[][] receptiveField = null;
	
	private int[][] getRightReceptiveField(){
		int[][] field = new int[recLenX][recLenY];
		int xBegin = midX;
		int yBegin = midY - (int) Math.ceil((float) recLenY / 2);
		for (int y = yBegin; y < yBegin + recLenY; y++) {
			for (int x = xBegin; x < xBegin + recLenX; x++) {
				field[x - xBegin][y - yBegin] = observations[x][y];
			}
		}
		return field;
	}
	private int[][] getLefttReceptiveField(){
		int[][] field = new int[recLenX][recLenY];
		int xBegin = midX;
		int yBegin = midY - (int) Math.ceil((float) recLenY / 2);
		for (int y = yBegin; y < yBegin + recLenY; y++) {
			for (int x = xBegin-recLenX+1; x <= xBegin; x++) {
				field[x - xBegin+recLenX-1][y - yBegin] = observations[x][y];
			}
		}
		mirrorReceptiveField(field);
		return field;
	}
	
	void swapArrayElems(int[][] arr, int i, int j, int y){
		int tmp = arr[i][y];
		arr[i][y] = arr[j][y];
		arr[j][y] = tmp;
	}
	
	void mirrorReceptiveField(int[][] field){
		for(int y = 0; y < 6; y++){
			int swapDist = 5-1;
			for(int x= 0; x < 5/2; x++){
				swapArrayElems(field,x,x+swapDist,y);
				swapDist-=2;
			}
		}
	}

	void setReceptiveField(MarioForwardModel model) {// obersvations middle:
														// (16,8)
		observations = model.getMarioCompleteObservation(0, 0);
		System.out.println(observations.length + " length "+ observations[0].length);
		
		if(model.isFacingRight())
			receptiveField = getRightReceptiveField();
		else
			receptiveField = getLefttReceptiveField();
	}
	
	void printReceptiveField(){
		System.out.println("-------------------");
		for(int y = 0; y < 6; y++){
			for(int x = 0; x < 5; x++){
				System.out.print(receptiveField[x][y] + ",");
			}
			System.out.println();
		}
		System.out.println("-------------------");
	}

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {

		setReceptiveField(model);
		printReceptiveField();
		// int[] pos = getMarioBoundedPos(model);

		// System.out.println(model.isFacingRight());

		// System.out.println(observ[0][0] + " x:"+ pos[0] + " y:" + pos[1] + "
		// _ " + observ.length + "_"+observ[0].length);
		// for(int i = 0 ; i < 16; i++){
		// System.out.println(" "+ i + ":" + mapBoolToInt((observ[0][i] ==
		// model.OBS_BRICK)));
		// }
		// System.out.println("-----------------------------------");

		// System.out.println("x: " + pos[0] + " y: " + pos[1] + " obs: " +
		// (observ[17][8]));

		return actions;
	}

	@Override
	public String getAgentName() {
		return "HumanAgent";
	}

	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	private void toggleKey(int keyCode, boolean isPressed) {
		if (this.actions == null) {
			return;
		}
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			this.actions[MarioActions.LEFT.getValue()] = isPressed;
			break;
		case KeyEvent.VK_RIGHT:
			this.actions[MarioActions.RIGHT.getValue()] = isPressed;
			break;
		case KeyEvent.VK_DOWN:
			this.actions[MarioActions.DOWN.getValue()] = isPressed;
			break;
		case KeyEvent.VK_S:
			this.actions[MarioActions.JUMP.getValue()] = isPressed;
			break;
		case KeyEvent.VK_A:
			this.actions[MarioActions.SPEED.getValue()] = isPressed;
			break;
		}
	}

}
