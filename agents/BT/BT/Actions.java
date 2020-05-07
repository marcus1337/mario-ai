package agents.BT.BT;

import engine.core.MarioForwardModel;
import engine.helper.MarioActions;

public class Actions {
	static STATE SUCCESS = STATE.SUCCESS;
	static STATE RUNNING = STATE.RUNNING;
	static STATE FAILURE = STATE.FAILURE;
	Blackboard blackboard;
	public MarioForwardModel model;

	public Actions(Blackboard blackboard) {
		this.blackboard = blackboard;
	}

	public Task makeAction(int ID) {
		Task task = null;
		
		if(ID == 0)
			task = new Task(null, this::shoot);

		return task;
	}

	STATE shoot(MarioForwardModel model) {
		if (model.getMarioMode() == 2) {
			if (blackboard.prevActions[MarioActions.SPEED.getValue()]) {
				blackboard.actions[MarioActions.SPEED.getValue()] = false;
				return RUNNING;
			}
			blackboard.actions[MarioActions.SPEED.getValue()] = true;
			return SUCCESS;
		}
		return FAILURE;
	}

	STATE duck(MarioForwardModel model) {
		if (model.isMarioOnGround() || blackboard.prevActions[MarioActions.DOWN.getValue()]) {
			blackboard.actions[MarioActions.DOWN.getValue()] = true;
			return SUCCESS;
		}
		return FAILURE;
	}

	STATE stopDuck(MarioForwardModel model) {
		blackboard.actions[MarioActions.DOWN.getValue()] = false;
		return SUCCESS;
	}

	STATE releaseLeft(MarioForwardModel model) {
		blackboard.actions[MarioActions.LEFT.getValue()] = false;
		return SUCCESS;
	}

	STATE pressLeft(MarioForwardModel model) {
		blackboard.actions[MarioActions.LEFT.getValue()] = true;
		return SUCCESS;
	}

	STATE releaseRight(MarioForwardModel model) {
		blackboard.actions[MarioActions.RIGHT.getValue()] = false;
		return SUCCESS;
	}

	STATE pressRight(MarioForwardModel model) {
		blackboard.actions[MarioActions.RIGHT.getValue()] = true;
		return SUCCESS;
	}

	boolean canJump(MarioForwardModel model) {
		return (model.mayMarioJump() || !model.isMarioOnGround())
				&& (model.getMarioCanJumpHigher() || blackboard.ticksSinceStartJump == 0);
	}

	STATE highJump(MarioForwardModel model) {
		return jumpTowardLimit(model, 7);
	}

	STATE mediumJump(MarioForwardModel model) {
		return jumpTowardLimit(model, 4);
	}

	STATE smallJump(MarioForwardModel model) {
		return jumpTowardLimit(model, 1);
	}

	private STATE jumpingTowardLimit(int limit) { // 7 is max
		if (blackboard.ticksSinceStartJump < limit) {
			blackboard.actions[MarioActions.JUMP.getValue()] = true;
			return RUNNING;
		}
		return SUCCESS;
	}

	private boolean reachedJumpLimit(int limit) {
		return limit == blackboard.ticksSinceStartJump && blackboard.prevActions[MarioActions.JUMP.getValue()] == true;
	}

	private STATE jumpTowardLimit(MarioForwardModel model, int limit) {
		if (canJump(model))
			return jumpingTowardLimit(limit);
		if (reachedJumpLimit(limit))
			return SUCCESS;
		return FAILURE;
	}
}
