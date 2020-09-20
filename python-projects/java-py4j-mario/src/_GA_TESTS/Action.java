package _GA_TESTS;

import engine.core.MarioForwardModel;
import engine.helper.MarioActions;

public class Action {

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
	public boolean shoot;
	public boolean[] actions;
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

}
