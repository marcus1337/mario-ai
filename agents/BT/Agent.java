package agents.BT;

import java.util.Arrays;

import agents.BT.BT.Actions;
import agents.BT.BT.Blackboard;
import agents.BT.BT.Conditions;
import agents.BT.BT.STATE;
import agents.BT.BT.Task;
import agents.BT.BT.Tree;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;
import engine.sprites.Mario;


public class Agent implements MarioAgent {
	
	Tree tree;
	
	public Agent(String treeStr){
		tree = new Tree(treeStr);
	}

	boolean[] processReturnedActions(MarioForwardModel model) {
		tree.blackboard.ticksSinceStartJump++;
		if (model.isMarioOnGround())
			tree.blackboard.ticksSinceStartJump = 0;
		tree.blackboard.prevActions = tree.blackboard.actions;
		return tree.blackboard.actions;
	}

	void prepareActionData() {
		tree.blackboard.actions = new boolean[5];
	}
	
	float smallest = 9999;

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {

		prepareActionData();
		
		
		
		return processReturnedActions(model);
	}

	@Override
	public String getAgentName() {
		return "BTAgent";
	}

	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {

	}
}
