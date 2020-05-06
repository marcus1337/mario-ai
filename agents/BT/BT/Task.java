package agents.BT.BT;

public abstract class Task extends Node {

	@Override
	void reset(){
		status = FRESH;
	}

}
