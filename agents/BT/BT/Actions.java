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
		if (ID == 0) {
			task = new Task(null, this::shoot);
			task.description = "Fire";
		}
		if (ID == 1) {
			task = new Task(null, this::duck);
			task.description = "Duck";
		}
		if (ID == 2) {
			task = new Task(null, this::walkLeft);
			task.description = "Left";
		}
		if (ID == 3) {
			task = new Task(null, this::walkRight);
			task.description = "Right";
		}
		if (ID == 4) {
			task = new Task(null, this::jump);
			task.description = "Jump";
		}
		if (ID == 5) {
			task = new Task(null, this::speed);
			task.description = "Speed";
		}
		if (ID == 6) {
			task = new Task(null, this::noOperation);
			task.description = "NOP";
		}

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
		if (model.getMarioMode() == 0)
			return FAILURE;
		blackboard.actions[MarioActions.DOWN.getValue()] = true;
		if (model.isMarioOnGround())
			return SUCCESS;
		return RUNNING;
	}

	STATE walkLeft() {
		blackboard.actions[MarioActions.LEFT.getValue()] = true;
		blackboard.actions[MarioActions.RIGHT.getValue()] = false;
		return SUCCESS;
	}

	STATE walkRight() {
		blackboard.actions[MarioActions.LEFT.getValue()] = false;
		blackboard.actions[MarioActions.RIGHT.getValue()] = true;
		return SUCCESS;
	}

	STATE speed() {
		blackboard.actions[MarioActions.SPEED.getValue()] = true;
		return SUCCESS;
	}

	STATE noOperation() {
		for (int i = 0; i < 5; i++)
			blackboard.actions[i] = false;
		return SUCCESS;
	}
	
	
	STATE jump(){
		blackboard.actions[MarioActions.JUMP.getValue()] = true;
		if (canJump()){
			blackboard.hasPausedJump = false;
			return SUCCESS;
		}else if(!blackboard.hasPausedJump){
			blackboard.actions[MarioActions.JUMP.getValue()] = false;
			return FAILURE;
		}		
		return RUNNING;
	}

	private boolean canJump() {
		return (model.mayMarioJump() || !model.isMarioOnGround()) && model.getMarioCanJumpHigher();
	}
	
}
