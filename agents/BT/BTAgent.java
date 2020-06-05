package agents.BT;

import java.util.Arrays;

import agents.BT.BT.Interior;
import agents.BT.BT.STATE;
import agents.BT.BT.Tree;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;


public class BTAgent implements MarioAgent {
	
	public Tree tree;
	public String treeStr;
	
	public BTAgent(String treeStr){
		this.treeStr = treeStr;
	}

	boolean[] processReturnedActions(MarioForwardModel model) {
		tree.blackboard.prevActions = tree.blackboard.actions;
		return tree.blackboard.actions;
	}

	void prepareData(MarioForwardModel model) {
		tree.blackboard.actions = new boolean[5];
		tree.actions.model = model;
		tree.conditions.updateConditionParameters(model);
	}

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		prepareData(model);
		tree.root.resetLastReturnedStatuses();
		tree.root.run();
		boolean[] tmpActions = processReturnedActions(model);
		if(!tmpActions[MarioActions.JUMP.getValue()])
			tree.blackboard.hasPausedJump = true;
		return tmpActions;
	}

	@Override
	public String getAgentName() {
		return "BTAgent";
	}
	
	public void partiallyInitialize() {
		tree = new Tree(treeStr);
	}

	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {
		tree = new Tree(treeStr);
		tree.actions.model = model;
		tree.conditions.updateConditionParameters(model);
	}
}
