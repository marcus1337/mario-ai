package agents.human;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import _GA_TESTS.ReceptiveField;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;

public class Agent extends KeyAdapter implements MarioAgent {
	private boolean[] actions = null;

	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {
		actions = new boolean[5];
	}

	ReceptiveField recField = new ReceptiveField();
	int[][] field = null;
	int[][] enemyField = null;
	public MarioForwardModel model = null;
	
	private int maxXPosReached = 0;
	private int framesAtMaxXPos = 0;
	private int frameCounter = 0;

	private int getFramesSinceMaxXPos(){
		return frameCounter - framesAtMaxXPos;
	}
	private void updateMaxXPos(){
		int nowXPos = (int)model.getMarioFloatPos()[0];
		if(nowXPos > maxXPosReached){
			maxXPosReached = nowXPos;
			framesAtMaxXPos = frameCounter;
		}
	}
	private void updateIdleChecker(){
		frameCounter++;
		updateMaxXPos();
	}
	
	public void updateConditionParameters(MarioForwardModel model){
		this.model = model;
		updateIdleChecker();
		field = recField.getBlockReceptiveField(model);
		enemyField = recField.getEnemyReceptiveFieldStompable(model);
		//recField.printReceptiveField(field);
	}
	
	private boolean isNearRightEdge(){
		if(field[0][4] != 0 && field[1][4] == 0){
			float tmpX = (model.getMarioFloatPos()[0]%16.0f);
			if(tmpX >= 4.0f){
				return true;
			}
		}
		return false;
	}
	//just have isNearEdge
	
	int counter = 0;

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		//updateConditionParameters(model);
		
		//System.out.println((counter++)%21);
		///////System.out.println(model.getMarioFloatVelocity()[0]); //9.7 max
		//System.out.println("TEST: " + ((int)model.getMarioFloatPos()[0])/16);
		
		//actions[MarioActions.RIGHT.getValue()] = true;
		
		boolean[] dest = new boolean[5];
		System.arraycopy( actions, 0, dest, 0, actions.length );
		return dest;
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
