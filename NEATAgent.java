

import _GA_TESTS.ReceptiveField;
import agents.BT.BT.Tree;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;

public class NEATAgent implements MarioAgent {

	private ReceptiveField recField = new ReceptiveField();
	private int[][] field = null;
	private int[][] enemyField = null;
	private boolean isFacingRight;
	private JavaPorts evolver;


	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		field = recField.getBlockReceptiveField(model);
		enemyField = recField.getEnemyReceptiveField(model);
		isFacingRight = model.isFacingRight();
		
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
	
	public void preInitialize(JavaPorts evolver) {
		this.evolver = evolver;
	}
	
}
