package agents.BT;

import java.util.Arrays;

import agents.BT.BT.STATE;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;
import engine.sprites.Mario;


public class Agent implements MarioAgent {
	
	Nodes nodes;
	
	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {
		nodes = new Nodes();
		nodes.model = model;
	}

	boolean[] processReturnedActions() {
		nodes.ticksSinceStartJump++;
		if (nodes.model.isMarioOnGround())
			nodes.ticksSinceStartJump = 0;
		nodes.prevActions = nodes.actions;
		return nodes.actions;
	}

	void prepareActionData() {
		nodes.actions = new boolean[5];
	}
	
	float smallest = 9999;

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		nodes.model = model;

		prepareActionData();
		nodes.pressRight();
		nodes.highJump();
		nodes.shoot();
		//nodes.duck();
		
		return processReturnedActions();
	}

	@Override
	public String getAgentName() {
		return "DoNothingAgent";
	}
}
