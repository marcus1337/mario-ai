package agents.BT.BT;

public abstract class Node {
	
	protected static STATE FRESH = STATE.FRESH;
	protected static STATE SUCCESS = STATE.SUCCESS;
	protected static STATE RUNNING = STATE.RUNNING;
	protected static STATE FAILURE = STATE.FAILURE;
	
	public STATE status = FRESH;
	public Node parent;
	public abstract NodeTypes getType();
	
	public abstract STATE run();
	
	protected STATE runAndUpdate(){
		status = run();
		return status;
	}
	
	public abstract void reset();
	
	protected boolean shouldRun(){
		return status != SUCCESS && status != FAILURE;
	}
	
	public boolean isParent(){
		return getType() == NodeTypes.INTERIOR || getType() == NodeTypes.DECORATOR;
	}
	

}
