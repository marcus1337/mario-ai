package agents.BT;

import agents.BT.BT.STATE;
import engine.core.MarioForwardModel;
import engine.helper.MarioActions;

public class Nodes {
	static STATE SUCCESS = STATE.SUCCESS;
	static STATE RUNNING = STATE.RUNNING;
	static STATE FAILURE = STATE.FAILURE;
	
	public boolean prevActions[] = new boolean[5];
	public boolean actions[] = new boolean[5];
	public MarioForwardModel model = null;
	public int ticksSinceStartJump;
	
	STATE stopRun(){
		actions[MarioActions.SPEED.getValue()] = false;
		return SUCCESS;
	}
	
	STATE run(){
		actions[MarioActions.SPEED.getValue()] = true;
		return SUCCESS;
	}
	
	STATE shoot(){
		if(model.getMarioMode() == 2){
			if(prevActions[MarioActions.SPEED.getValue()]){
				actions[MarioActions.SPEED.getValue()] = false;
				return RUNNING;
			}
			actions[MarioActions.SPEED.getValue()] = true;
			return SUCCESS;
		}
		return FAILURE;
	}
	
	STATE duck() {
		if(model.isMarioOnGround() || prevActions[MarioActions.DOWN.getValue()]){
			actions[MarioActions.DOWN.getValue()] = true;
			return SUCCESS;
		}
		return FAILURE;
	}
	
	STATE stopDuck() {
		actions[MarioActions.DOWN.getValue()] = false;
		return SUCCESS;
	}
	
	STATE releaseLeft() {
		actions[MarioActions.LEFT.getValue()] = false;
		return SUCCESS;
	}

	STATE pressLeft() {
		actions[MarioActions.LEFT.getValue()] = true;
		return SUCCESS;
	}
	
	STATE releaseRight() {
		actions[MarioActions.RIGHT.getValue()] = false;
		return SUCCESS;
	}

	STATE pressRight() {
		actions[MarioActions.RIGHT.getValue()] = true;
		return SUCCESS;
	}
	
	boolean canJump(){
		return (model.mayMarioJump() || !model.isMarioOnGround()) && (model.getMarioCanJumpHigher() || ticksSinceStartJump == 0);
	}
	
	STATE highJump(){
		return jumpTowardLimit(7);
	}
	
	STATE mediumJump(){
		return jumpTowardLimit(4);
	}
	
	STATE smallJump(){
		return jumpTowardLimit(1);
	}
	
	private STATE jumpingTowardLimit(int limit){ //7 is max
		if(ticksSinceStartJump < limit){
			actions[MarioActions.JUMP.getValue()] = true;
			return RUNNING;
		}
		return SUCCESS;
	}
	
	private boolean reachedJumpLimit(int limit){
		return limit == ticksSinceStartJump && prevActions[MarioActions.JUMP.getValue()] == true;
	}
	
	private STATE jumpTowardLimit(int limit){
		if (canJump())
			return jumpingTowardLimit(limit);
		if(reachedJumpLimit(limit))
			return SUCCESS;
		return FAILURE;
	}

}
