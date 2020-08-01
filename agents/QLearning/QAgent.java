package agents.QLearning;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import _GA_TESTS.ReceptiveField;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;


public class QAgent implements MarioAgent {
	
	boolean[] prevActions = null;
	Random random;
	MarioForwardModel model;
	
	//STATES, EnemyClose, enemyFar, BLOCKS AHEAD, 5x5, NearEdge, RunButtonPrevPressed?, isFireMario
	//			1			1		 25					1				1					1
	///Actions, RIGHT, LEFT, JUMP, SHOOT, RUN 
	
	/***shoot-run-neither	* 2 options...add prev action to state instead		12 actions in total 
	*	right-left-neither	* 3 options
	*	jump-neither 		* 2 options //Extra finesse, will only press jump if possible
	*************************/
	//ACTION_INFO_STATES: PREV-TURN-RUNNING?
	
	private ReceptiveField recField = new ReceptiveField();
	private int[][] field = null;
	private int[][] enemyField = null;
	
	public double epsilon;
	HashMap<Integer, double[]> qMappings; //States to array of Q-action-values
	
	int prevStateNumber;
	
	int getBestActionNumberForState(int stateNumber){
		double[] qvalues = getQValuesMappedToState(stateNumber);
		return getHighestQIndex(qvalues);
	}

	private double[] getQValuesMappedToState(int stateNumber) {
		double[] qvalues = null;
		if(!qMappings.containsKey(stateNumber)){
			qvalues = getRandomizedQValues();
			qMappings.put(stateNumber, qvalues);
		}else{
			qvalues = qMappings.get(stateNumber);
		}
		return qvalues;
	}

	private double[] getRandomizedQValues() {
		double[] qvalues = new double[12];
		for(int i = 0 ; i < qvalues.length; i++)
			qvalues[i] = 0 + (1 - 0) * random.nextDouble();
		return qvalues;
	}

	private int getHighestQIndex(double[] qvalues) {
		int highestQIndex = 0;
		double highestQValue = qvalues[0];
		for(int i = 1; i < qvalues.length; i++){
			if(qvalues[i] > highestQValue){
				highestQValue = qvalues[i];
				highestQIndex = i;
			}
		}
		return highestQIndex;
	}
	
	private void updateFields() {
		field = recField.getBlockReceptiveField(model);
		enemyField = recField.getEnemyReceptiveField(model);
	}
	
	private boolean[] getStateArray(){
		boolean[] result = new boolean[30];
		result[0] = isNearRightEdge();
		result[1] = isEnemyClose();
		result[2] = isEnemyFarAway();
		result[3] = model.getMarioMode() == 2;
		result[4] = prevActions[MarioActions.SPEED.getValue()];
		boolean[] fieldBools = getFieldBlocks();
		System.arraycopy(fieldBools, 0, result, 5, 25);
		return result;
	}
	
	private boolean[] getFieldBlocks(){
		boolean[] result = new boolean[25];
		int counter = 0;
		for(int i = 0; i < 5; i++){
			for(int j = 1 ; j < 6; j++){
				if(field[i][j] != 0)
					result[counter] = true;
				counter++;
			}
		}
		return result;
	}
	
	private boolean isNearRightEdge(){
		if(field[0][4] != 0 && field[1][4] == 0){
			float tmpX = (model.getMarioFloatPos()[0]%16.0f);
			if(tmpX >= 4.0f){
				return true;
			}
		}
		return false;
	}
	
	private boolean isEnemyClose(){
		for(int i = 0; i < 2; i++){
			for(int j = 0 ; j < 6; j++){
				if(enemyField[i][j] != 0)
					return true;
			}
		}
		return false;
	}
	private boolean isEnemyFarAway(){
		for(int i = 2; i < 5; i++){
			for(int j = 0 ; j < 6; j++){
				if(enemyField[i][j] != 0)
					return true;
			}
		}
		return false;
	}
	private int bitsToNumber(boolean[] allStates){
		String bitStr = new String();
		for(int i = 0 ; i < allStates.length; i++){
			if(allStates[i])
				bitStr += "1";
			else
				bitStr += "0";
		}		
		return Integer.parseInt(bitStr, 2);
	}
	
	private int getStateNumber(){
		return bitsToNumber(getStateArray());
	}
	
	private boolean canJump() {
		return (model.mayMarioJump() || !model.isMarioOnGround()) || model.getMarioCanJumpHigher();
	}
	
	public boolean[] numberToAction(int decision){ //min 1, max 12
		boolean[] res = new boolean[5];
		if(decision > 6){
			decision -= 6;
			if(canJump())
				res[MarioActions.JUMP.getValue()] = true;
		}
		if(decision > 3){
			decision -= 3;
			res[MarioActions.SPEED.getValue()] = true;
		}
		if(decision == 3)
			res[MarioActions.RIGHT.getValue()] = true;
		if(decision == 2)
			res[MarioActions.LEFT.getValue()] = true;
		return res;
	}
	
	boolean isRandomAction(int probability){
		final int max = 100;
		final int min = 0;
		int randomNumber = random.nextInt(max + 1 - min) + min;
		return randomNumber > probability;
	}
	
	public boolean[] getRandomAction(){
		return numberToAction(random.nextInt(12)+1);
	}
	
	public boolean[] getBestAction(){
		
		return null;
	}

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		this.model = model;
		updateFields();
		int stateNumber = getStateNumber();		
		boolean[] tmpActions = numberToAction(getBestActionNumberForState(stateNumber));
		
		
		if(prevStateNumber != stateNumber){
			
			
			
		}
		
		if(isRandomAction(5)){
			tmpActions = getRandomAction();
		}
		
		prevStateNumber = stateNumber;
		prevActions = tmpActions;
		return tmpActions;
	}

	@Override
	public String getAgentName() {
		return "Q-learning-Agent";
	}
	

	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {
		this.model = model;
		prevStateNumber = -1;
		prevActions = new boolean[]{true,true,true,true,true};
		random = new Random();
		qMappings = new HashMap<Integer, double[]>();
	}
}
