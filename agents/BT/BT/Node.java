package agents.BT.BT;

public abstract class Node {
	
	protected static STATE FRESH = STATE.FRESH;
	protected static STATE SUCCESS = STATE.SUCCESS;
	protected static STATE RUNNING = STATE.RUNNING;
	protected static STATE FAILURE = STATE.FAILURE;
	
	public STATE status;
	public Node parent;
	
	abstract STATE run();
	abstract void reset();
	

}
