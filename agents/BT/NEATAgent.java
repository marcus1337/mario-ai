package agents.BT;

import _GA_TESTS.ReceptiveField;
import agents.BT.BT.Tree;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;

public class NEATAgent implements MarioAgent {

	ReceptiveField recField = new ReceptiveField();
	int[][] field = null;
	int[][] enemyField = null;


	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		field = recField.getBlockReceptiveField(model);
		enemyField = recField.getEnemyReceptiveField(model);

		boolean[] tmpActions = new boolean[5];
		
		return tmpActions;
	}

	@Override
	public String getAgentName() {
		return "NEATAgent";
	}

	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {

	}
}
