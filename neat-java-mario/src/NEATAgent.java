
import _GA_TESTS.Action;
import _GA_TESTS.Observation;
import _GA_TESTS.ReceptiveField;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;

public class NEATAgent implements MarioAgent {
	
	public Observation observation;
	public Action action;
	private JavaPorts evolver;
	
	public boolean isElite;
	public int AIIndex;

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		updateFields(model);
		calculateInput();
		boolean[] tmpActions = getNetworkOutput();
		return tmpActions;
	}

	
	///////////////////
	public void updateFields(MarioForwardModel model) {
		observation.updateModel(model);
		action.updateModel(model);
	}
	
	private FloatVec makeNetworkInput(boolean[] observArr) {
		FloatVec floatVec = new FloatVec();
		for(int i = 0 ; i < observArr.length; i++)
			addInputValue(floatVec, observArr[i]);
		return floatVec;
	}
	
	public void setActions(MarioForwardModel model){
		boolean[] acts = getNetworkOutput();
		action.setActions(acts[0], acts[1], acts[2], acts[3], acts[4]);
	}

	public void calculateInput() {
		FloatVec floatVec = makeNetworkInput(observation.getStateArray());
		if(isElite)
			evolver.calcNEATEliteInput(AIIndex, floatVec);
		else
			evolver.calcNEATInput(AIIndex, floatVec);
		floatVec.delete();
		
		
	}

	private boolean[] getNetworkOutput() {
		boolean[] tmpActions = new boolean[5];
		FloatVec output = getOutput();
		for (int i = 0; i < output.size(); i++) {
			if (output.get(i) > 0.5f)
				tmpActions[i] = true;
		}
		output.delete();
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
	
	private void addInputValue(FloatVec floatVec, boolean _condition){
		if (_condition)
			floatVec.add(1.f);
		else
			floatVec.add(0.f);
	}
	//////////////////
	
	
	

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
		observation = new Observation();
		action = new Action();
	}

}
