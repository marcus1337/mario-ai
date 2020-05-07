package agents.BT;

import java.util.Arrays;

import agents.BT.BT.Actions;
import agents.BT.BT.Blackboard;
import agents.BT.BT.Conditions;
import agents.BT.BT.STATE;
import agents.BT.BT.Task;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;
import engine.sprites.Mario;


public class Agent implements MarioAgent {
	
	Blackboard blackboard;
	Actions actions;
	Conditions conditions;
	
	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {
		blackboard = new Blackboard();
		actions = new Actions(blackboard);
		conditions = new Conditions(blackboard);
	}

	boolean[] processReturnedActions(MarioForwardModel model) {
		blackboard.ticksSinceStartJump++;
		if (model.isMarioOnGround())
			blackboard.ticksSinceStartJump = 0;
		blackboard.prevActions = blackboard.actions;
		return blackboard.actions;
	}

	void prepareActionData() {
		blackboard.actions = new boolean[5];
	}
	
	float smallest = 9999;

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {

		prepareActionData();
		
		Task test = actions.makeAction(0);
		actions.model = model;
		test.run();
		
		return processReturnedActions(model);
	}

	@Override
	public String getAgentName() {
		return "DoNothingAgent";
	}
}
