package agents.BT;

import java.util.Arrays;

import agents.BT.BT.STATE;
import agents.BT.BT.Tree;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;


public class Agent implements MarioAgent {
	
	Tree tree;
	String treeStr;
	
	public Agent(String treeStr){
		this.treeStr = treeStr;
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

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		prepareActionData();
		
		if(tree.root.status == STATE.SUCCESS){
			tree.root.reset();
	
		}
		tree.root.run();
		
		return processReturnedActions(model);
	}

	@Override
	public String getAgentName() {
		return "BTAgent";
	}

	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {
		tree = new Tree(treeStr);
		tree.actions.model = model;
		tree.conditions.model = model;
	}
}
