package agents.human;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import _GA_TESTS.ReceptiveField;
import agents.BT.BT.STATE;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;

public class Agent extends KeyAdapter implements MarioAgent {
	private boolean[] actions = null;

	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {
		actions = new boolean[MarioActions.numberOfActions()];
	}

	ReceptiveField recField = new ReceptiveField();
	int[][] field = null;
	int[][] enemyField = null;
	public MarioForwardModel model = null;
	
	private int maxXPosReached = 0;
	private int framesAtMaxXPos = 0;
	private int frameCounter = 0;

	STATE isObstacleAhead() {
		for (int i = 0; i < recField.obstacleValues.size(); i++)
			if (recField.tunneledFieldContains(field, recField.obstacleValues.get(i)))
				return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	STATE isEnemyAhead() {
		if (recField.fieldContainsAnything(enemyField))
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}
	
	STATE isGapAhead() {
		if (recField.isGapAhead(field))
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}
	
	STATE isStuck(){
		if(getFramesSinceMaxXPos() > 150)
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}
	
	STATE upIsPushable(){
		if(recField.upIsPushable(field))
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}
	
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
		enemyField = recField.getEnemyReceptiveField(model);
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

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		updateConditionParameters(model);
		

		//System.out.println(model.getMarioFloatVelocity()[0]); //9.7 max
		
		//actions[MarioActions.RIGHT.getValue()] = true;
		
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
