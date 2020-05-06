package agents.BT.BT;

import java.util.ArrayList;

public abstract class Interior extends Node {

	protected ArrayList<Task> children;
	protected int lastUnfinishedChildIndex;
	
	public Interior(){
		children = new ArrayList<Task>();
		lastUnfinishedChildIndex = 0;
	}
	
	@Override
	void reset(){
		status = FRESH;
		for(Node child : children){
			child.reset();
		}
	}
	
	

}
