package agents.BT.BT;

import java.util.ArrayList;

public abstract class Node {
	
	protected static STATE FRESH = STATE.FRESH;
	protected static STATE SUCCESS = STATE.SUCCESS;
	protected static STATE RUNNING = STATE.RUNNING;
	protected static STATE FAILURE = STATE.FAILURE;
	
	public STATE status = FRESH;
	public STATE lastReturnedStatus = FRESH;
	
	public Node parent;
	public int width;
	public int height;
	public String text = "";
	public String description = "";
	
	public abstract NodeTypes getType();
	public abstract NodeTypeDetailed getDetailedType();
	
	public abstract STATE run();
	public ArrayList<Node> getChildren(){
		return null;
	}
	
	protected STATE runAndUpdate(){
		status = run();
		lastReturnedStatus = status;
		return status;
	}
	
	public abstract void reset();
	public abstract void resetLastReturnedStatuses();
	
	protected boolean shouldRun(){
		return status != SUCCESS && status != FAILURE;
	}
	
	public boolean isParent(){
		return getType() == NodeTypes.OTHER_INTERIOR || 
				getType() == NodeTypes.UNORDERED_INTERIOR || getType() == NodeTypes.DECORATOR;
	}
	

}
