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
		if(ID == 1)
			task = new Task(null, this::duck);
		if(ID == 2)
			task = new Task(null, this::pressLeft);
		if(ID == 3)
			task = new Task(null, this::pressRight);
		if(ID == 4)
			task = new Task(null, this::highJump);
		if(ID == 5)
			task = new Task(null, this::mediumJump);
		if(ID == 6)
			task = new Task(null, this::smallJump);
		if(ID == 7)
			task = new Task(null, this::stopDuck);
		if(ID == 8)
			task = new Task(null, this::releaseLeft);
		if(ID == 9)
			task = new Task(null, this::releaseRight);
		if(ID == 10)
			task = new Task(null, this::pressSpeed);
		if(ID == 11)
			task = new Task(null, this::releaseSpeed);
		
		return task;
	}

	STATE shoot() {
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

	STATE duck() {
		if (model.isMarioOnGround() || blackboard.prevActions[MarioActions.DOWN.getValue()]) {
			blackboard.actions[MarioActions.DOWN.getValue()] = true;
			return SUCCESS;
		}
		return FAILURE;
	}

	STATE stopDuck() {
		blackboard.actions[MarioActions.DOWN.getValue()] = false;
		return SUCCESS;
	}

	STATE releaseLeft() {
		blackboard.actions[MarioActions.LEFT.getValue()] = false;
		return SUCCESS;
	}

	STATE pressLeft() {
		blackboard.actions[MarioActions.LEFT.getValue()] = true;
		return SUCCESS;
	}

	STATE releaseRight() {
		blackboard.actions[MarioActions.RIGHT.getValue()] = false;
		return SUCCESS;
	}

	STATE pressRight() {
		blackboard.actions[MarioActions.RIGHT.getValue()] = true;
		return SUCCESS;
	}
	
	STATE pressSpeed() {
		blackboard.actions[MarioActions.SPEED.getValue()] = true;
		return SUCCESS;
	}
	
	STATE releaseSpeed() {
		blackboard.actions[MarioActions.SPEED.getValue()] = false;
		return SUCCESS;
	}
	
	STATE highJump() {
		return jumpTowardLimit(7);
	}

	STATE mediumJump() {
		return jumpTowardLimit(4);
	}

	STATE smallJump() {
		return jumpTowardLimit(1);
	}

	private boolean canJump() {
		return (model.mayMarioJump() || !model.isMarioOnGround())
				&& (model.getMarioCanJumpHigher() || blackboard.ticksSinceStartJump == 0);
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

	private STATE jumpTowardLimit(int limit) {
		if (canJump())
			return jumpingTowardLimit(limit);
		if (reachedJumpLimit(limit))
			return SUCCESS;
		return FAILURE;
	}
}
