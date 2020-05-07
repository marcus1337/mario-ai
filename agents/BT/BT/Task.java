package agents.BT.BT;

import java.util.function.Function;

import engine.core.MarioForwardModel;

public abstract class Task extends Node {

	Function<MarioForwardModel, STATE> action = null;
	Function<MarioForwardModel, STATE> condition = null;
	Blackboard blackboard;

	public Task(Blackboard blackboard, Function<MarioForwardModel, STATE> action,
			Function<MarioForwardModel, STATE> condition) {
		this.blackboard = blackboard;
		this.action = action;
		this.condition = condition;
	}
	
	public boolean isCondition(){
		return condition != null && action == null;
	}
	
	private boolean conditionFail(MarioForwardModel model){
		return condition != null && condition.apply(model) == FAILURE;
	}

	@Override
	STATE run(MarioForwardModel model) {
		if (isCondition())
			return condition.apply(model);
		if(conditionFail(model))
			return FAILURE;
		return action.apply(model);
	}

	@Override
	void reset() {
		status = FRESH;
	}

}
