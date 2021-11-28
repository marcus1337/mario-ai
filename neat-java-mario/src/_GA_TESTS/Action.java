package _GA_TESTS;

import engine.core.MarioForwardModel;
import engine.helper.MarioActions;

public class Action {
	
	public boolean shoot;
	public boolean[] actions;

	public Action() {
		actions = new boolean[5];
	}

	private MarioForwardModel model;

	public void updateModel(MarioForwardModel model) {
		this.model = model;
	}

	private boolean canJump() {
		return (model.mayMarioJump() || !model.isMarioOnGround()) || model.getMarioCanJumpHigher();
	}

	// shoot? y/n (only if possible)
	// run? y/n
	// jump? y/n (only if possible)
	// move right y/n (only if not move left)
	// move left y/n (only if not move right)
	public void setActions(boolean shoot, boolean speed, boolean jump, boolean right, boolean left) {
		actions = new boolean[5];
		this.shoot = shoot;
		if (jump && canJump())
			actions[MarioActions.JUMP.getValue()] = true;
		actions[MarioActions.SPEED.getValue()] = speed;
		if (right && !left)
			actions[MarioActions.RIGHT.getValue()] = true;
		if (!right && left)
			actions[MarioActions.LEFT.getValue()] = true;
	}
	
	public boolean isRunning() {
		return actions[MarioActions.SPEED.getValue()];
	}

	public boolean isJumping() {
		return actions[MarioActions.JUMP.getValue()];
	}

	public boolean isMovingRight() {
		return actions[MarioActions.RIGHT.getValue()];
	}

	public boolean isMovingLeft() {
		return actions[MarioActions.LEFT.getValue()];
	}

	public boolean isNotMovingLeftOrRight() {
		return !actions[MarioActions.RIGHT.getValue()] && !actions[MarioActions.LEFT.getValue()];
	}

	public Action copyAction() {
		Action action = new Action();
		action.shoot = this.shoot;
		action.actions = this.actions.clone();
		return action;
	}

}
