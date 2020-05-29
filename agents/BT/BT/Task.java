package agents.BT.BT;

import java.util.function.Supplier;

public class Task extends Node {

	Supplier<STATE> action = null;
	Supplier<STATE> condition = null;

	public Task(Supplier<STATE> condition,
			Supplier<STATE> action) {
		this.action = action;
		this.condition = condition;
	}
	
	public boolean isCondition(){
		return condition != null && action == null;
	}
	
	private boolean conditionFail(){
		return condition != null && condition.get() == FAILURE;
	}

	@Override
	public STATE run() {
		if (isCondition())
			lastReturnedStatus = condition.get();
		else if(conditionFail())
			lastReturnedStatus = FAILURE;
		else lastReturnedStatus = action.get();
		return lastReturnedStatus;
	}

	@Override
	public void reset() {
		status = FRESH;
	}
	
	@Override
	public void resetLastReturnedStatuses(){
		lastReturnedStatus = FRESH;
	}
	
	@Override
	public NodeTypeDetailed getDetailedType() {
		if(isCondition())
			return NodeTypeDetailed.CONDITION;
		return NodeTypeDetailed.ACTION;
	}
	
	@Override
	public NodeTypes getType(){
		if(isCondition())
			return NodeTypes.CONDITION;
		return NodeTypes.ACTION;
	}

}
