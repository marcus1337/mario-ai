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
	public MarioForwardModel model = null;

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

	ArrayList<Integer> obstacleValues = getObstacleValues();

	STATE isObstacleAhead() {
		for (int i = 0; i < obstacleValues.size(); i++)
			if (recField.tunneledFieldContains(field, obstacleValues.get(i)))
				return STATE.SUCCESS;

		return STATE.FAILURE;
	}

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		this.model = model;
		field = recField.getReceptiveField(model);
		recField.printReceptiveField(field);
		System.out.println("OBSTACLE: " + isObstacleAhead());
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
