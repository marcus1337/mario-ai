package agents.BT.BT;

import java.util.function.Supplier;

import engine.core.MarioForwardModel;

public class Conditions {
	Blackboard blackboard;
	public MarioForwardModel model;
	
	public Conditions(Blackboard blackboard){
		this.blackboard = blackboard;
	}
	
	STATE isBigMario(){
		if(model.getMarioMode() > 0)
			return Node.SUCCESS;
		return Node.FAILURE;
	}
	
	public Task makeCondition(int ID){
		Task task = null;

		if(ID == 0)
			task = new Task(this::isBigMario, null);
		
		return task;
	}

}
