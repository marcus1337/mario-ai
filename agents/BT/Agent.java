package agents.BT;

import java.util.Arrays;

import agents.BT.BT.Actions;
import agents.BT.BT.Blackboard;
import agents.BT.BT.STATE;
import agents.BT.BT.Task;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;
import engine.sprites.Mario;


public class Agent implements MarioAgent {
	
	Nodes nodes;
	Blackboard blackboard;
	
	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {
		nodes = new Nodes();
		nodes.model = model;
		blackboard = new Blackboard();
	}

	boolean[] processReturnedActions() {
		blackboard.ticksSinceStartJump++;
		if (nodes.model.isMarioOnGround())
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
		nodes.model = model;

		prepareActionData();
		
		Actions actions = new Actions(blackboard);
		Task test = actions.makeAction(0);
		test.run(model);
		//nodes.duck();
		
		return processReturnedActions();
	}

	@Override
	public String getAgentName() {
		return "DoNothingAgent";
	}
}
