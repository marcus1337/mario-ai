package agents.BT.BT;

import engine.core.MarioForwardModel;

public class Conditions {
	Blackboard blackboard;
	
	public Conditions(Blackboard blackboard){
		this.blackboard = blackboard;
	}
	
	STATE isBigMario(MarioForwardModel model){
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
