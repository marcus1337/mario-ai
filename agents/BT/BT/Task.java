package agents.BT.BT;

import java.util.function.Function;
import java.util.function.Supplier;

import engine.core.MarioForwardModel;

public class Task extends Node {

	Supplier<STATE> action = null;
	Supplier<STATE> condition = null;

	public Task(Supplier<STATE> action,
			Supplier<STATE> condition) {
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
			return condition.get();
		if(conditionFail())
			return FAILURE;
		return action.get();
	}

	@Override
	public void reset() {
		status = FRESH;
	}
	
	@Override
	public NodeTypes getType(){
		if(isCondition())
			return NodeTypes.CONDITION;
		return NodeTypes.ACTION;
	}

}
