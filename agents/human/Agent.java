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

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		this.model = model;

		field = recField.getReceptiveField(model);
		enemyField = recField.getEnemyReceptiveField(model);
		// recField.printReceptiveField(field);
		System.out.println("ENEMY: " + isEnemyAhead() + " obstacle: " + isObstacleAhead());
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
