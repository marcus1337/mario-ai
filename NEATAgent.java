
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

	public int AIIndex;

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		field = recField.getBlockReceptiveField(model);
		enemyField = recField.getEnemyReceptiveField(model);
		isFacingRight = model.isFacingRight();

		FloatVec floatVec = makeNetworkInput();
		evolver.calcNEATInput(AIIndex, floatVec);
		
		boolean[] tmpActions = getNetworkOutput();
		

		return tmpActions;
	}

	private boolean[] getNetworkOutput() {
		boolean[] tmpActions = new boolean[5];
		FloatVec output = evolver.getNEATOutput(AIIndex);
		for(int i = 0 ; i < output.size(); i++){
			if(output.get(i) > 0.5f)
				tmpActions[i] = true;
		}
		return tmpActions;
	}

	private FloatVec makeNetworkInput() {
		FloatVec floatVec = new FloatVec();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				if (field[i][j] != 0)
					floatVec.add(1.f);
				else
					floatVec.add(0.f);
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				if (enemyField[i][j] != 0)
					floatVec.add(1.f);
				else
					floatVec.add(0.f);
			}
		}
		if(isFacingRight)
			floatVec.add(1.f);
		else
			floatVec.add(0.f);
		return floatVec;
	}

	@Override
	public String getAgentName() {
		return "NEATAgent";
	}

	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {

	}

	public void preInitialize(JavaPorts evolver, int AIIndex) {
		this.evolver = evolver;
		this.AIIndex = AIIndex;
	}

}
