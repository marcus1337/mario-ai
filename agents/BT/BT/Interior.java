package agents.BT.BT;

import java.util.ArrayList;

public abstract class Interior extends Node {

	protected ArrayList<Node> children;
	protected int lastUnfinishedChildIndex;
	
	public void addChild(Node node){
		children.add(node);
	}
	
	public Interior(){
		children = new ArrayList<Node>();
		lastUnfinishedChildIndex = 0;
	}
	
	@Override
	public void reset(){
		status = FRESH;
		for(Node child : children){
			child.reset();
		}
	}
	
	

}
