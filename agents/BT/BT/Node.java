package agents.BT.BT;

import engine.core.MarioForwardModel;

public abstract class Node {
	
	protected static STATE FRESH = STATE.FRESH;
	protected static STATE SUCCESS = STATE.SUCCESS;
	protected static STATE RUNNING = STATE.RUNNING;
	protected static STATE FAILURE = STATE.FAILURE;
	
	public STATE status;
	public Node parent;
	public abstract NodeTypes getType();
	
	public abstract STATE run();
	public abstract void reset();
	
	protected boolean shouldRun(){
		return status != SUCCESS;
	}
	
	public boolean isParent(){
		return getType() == NodeTypes.INTERIOR || getType() == NodeTypes.DECORATOR;
	}
	

}
