
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
	private boolean canJumpHigher;
	private JavaPorts evolver;
	boolean isElite;

	public int AIIndex;

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		updateFields(model);
		calculateInput();
		boolean[] tmpActions = getNetworkOutput();

		return tmpActions;
	}

	private void updateFields(MarioForwardModel model) {
		field = recField.getBlockReceptiveField(model);
		enemyField = recField.getEnemyReceptiveField(model);
		isFacingRight = model.isFacingRight();
		canJumpHigher = model.getMarioCanJumpHigher();
	}

	private void calculateInput() {
		FloatVec floatVec = makeNetworkInput();
		if(isElite)
			evolver.calcNEATEliteInput(AIIndex, floatVec);
		else
			evolver.calcNEATInput(AIIndex, floatVec);
	}

	private boolean[] getNetworkOutput() {
		boolean[] tmpActions = new boolean[5];
		FloatVec output = getOutput();

		for (int i = 0; i < output.size(); i++) {
			if (output.get(i) > 0.5f)
				tmpActions[i] = true;
		}
		return tmpActions;
	}

	private FloatVec getOutput() {
		FloatVec output;
		if (isElite)
			output = evolver.getNEATEliteOutput(AIIndex);
		else
			output = evolver.getNEATOutput(AIIndex);
		return output;
	}

	private FloatVec makeNetworkInput() {
		FloatVec floatVec = new FloatVec();
		addFieldInput(floatVec, field);
		addFieldInput(floatVec, enemyField);
		addInputValue(floatVec, isFacingRight);
		addInputValue(floatVec, canJumpHigher);
		return floatVec;
	}
	
	private void addInputValue(FloatVec floatVec, boolean _condition){
		if (_condition)
			floatVec.add(1.f);
		else
			floatVec.add(0.f);
	}

	private void addFieldInput(FloatVec floatVec, int[][] _field) {
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 6; j++) 
				addInputValue(floatVec, _field[i][j] != 0);
	}

	@Override
	public String getAgentName() {
		return "NEATAgent";
	}

	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {

	}

	public NEATAgent(JavaPorts evolver, int AIIndex) {
		preInitialize(evolver, AIIndex);
	}

	public NEATAgent(JavaPorts evolver, int AIIndex, boolean isElite) {
		this.isElite = isElite;
		preInitialize(evolver, AIIndex);
	}

	public void preInitialize(JavaPorts evolver, int AIIndex) {
		this.evolver = evolver;
		this.AIIndex = AIIndex;
	}

}
