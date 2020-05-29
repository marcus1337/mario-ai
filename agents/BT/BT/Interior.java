package agents.BT.BT;

import java.util.ArrayList;

public abstract class Interior extends Node {

	protected ArrayList<Node> children;
	public boolean isMemoryNode = false;
	
	@Override
	public ArrayList<Node> getChildren(){
		return new ArrayList<Node>(children);
	}
	
	public void addChild(Node node){
		children.add(node);
	}
	
	public Interior(){
		children = new ArrayList<Node>();
	}
	
	@Override
	public void reset(){
		status = FRESH;
		for(Node child : children){
			child.reset();
		}
	}
	
	public void copy(Interior other){
		children = other.children;
		status = other.status;
	}
	
	@Override
	public STATE run() {
		if(isMemoryNode)
			lastReturnedStatus = runMemory();
		else
			lastReturnedStatus = runNormal();
		return lastReturnedStatus;
	}

	protected abstract STATE runNormal();
	protected abstract STATE runMemory();

}
