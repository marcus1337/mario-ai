package agents.QLearning;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import _GA_TESTS.ReceptiveField;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioResult;
import engine.core.MarioTimer;
import engine.helper.GameStatus;
import engine.helper.MarioActions;

public class QAgent implements MarioAgent {

	boolean[] prevActions = null;
	Random random;
	MarioForwardModel model;

	// STATES, EnemyClose, enemyFar, NearEdge, RunButtonPrevPressed?,
	// isFireMario, inAir
	// 1 1 1 1 1 1
	/// Actions, RIGHT, LEFT, JUMP, SHOOT, RUN
	// ground blocks 5
	// other blocks 13

	/***
	 * shoot-run-neither * 2 options...add prev action to state instead 12
	 * actions in total right-left-neither * 3 options jump-neither * 2 options
	 * //Extra finesse, will only press jump if possible
	 *************************/
	// ACTION_INFO_STATES: PREV-TURN-RUNNING?

	private ReceptiveField recField = new ReceptiveField(true);
	private int[][] field = null;
	private int[][] enemyField = null;

	HashMap<Integer, double[]> qMappings; // States to array of Q-action-values
	int prevStateNumber;
	public boolean learning = true;
	int frameCounter = 0;
	int explorationActionNumber;
	int previousXLocation;
	int numFramesPassed;
	int prevMarioState = 2;

	int getBestActionNumberForState(int stateNumber) {
		double[] qvalues = getQValuesMappedToState(stateNumber);
		return getHighestQIndex(qvalues) + 1;
	}

	private double[] getQValuesMappedToState(int stateNumber) {
		double[] qvalues = null;
		if (!qMappings.containsKey(stateNumber)) {
			qvalues = getRandomizedQValues();
			qMappings.put(stateNumber, qvalues);
		} else {
			qvalues = qMappings.get(stateNumber);
		}
		return qvalues;
	}

	private double[] getRandomizedQValues() {
		double[] qvalues = new double[12];
		for (int i = 0; i < qvalues.length; i++)
			qvalues[i] = 0 + (1 - 0) * random.nextDouble();
		return qvalues;
	}

	private int getHighestQIndex(double[] qvalues) {
		int highestQIndex = 0;
		double highestQValue = qvalues[0];
		for (int i = 1; i < qvalues.length; i++) {
			if (qvalues[i] > highestQValue) {
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

	private boolean[] getStateArray() {
		boolean[] result = new boolean[24];
		result[0] = isNearRightEdge();
		result[1] = isEnemyClose();
		result[2] = isEnemyFarAway();
		result[3] = model.getMarioMode() == 2;
		result[4] = prevActions[MarioActions.SPEED.getValue()];
		result[5] = model.isMarioOnGround();
		boolean[] fieldBools = getFieldBlocks();
		System.arraycopy(fieldBools, 0, result, 6, 18);
		return result;
	}

	private boolean[] getFieldBlocks() {
		boolean[] result = new boolean[18];
		int counter = 0;

		for (int i = 0; i < 5; i++) {
			if (field[i][4] != 0 || field[i][5] != 0)
				result[counter] = true;
			counter++;
		}

		if (field[0][1] != 0) {
			result[counter] = true;
		}
		counter++;

		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 4; j++) {
				if (field[i][j] != 0)
					result[counter] = true;
				counter++;
			}
		}
		return result;
	}

	private boolean isNearRightEdge() {
		if (field[0][4] != 0 && field[1][4] == 0) {
			float tmpX = (model.getMarioFloatPos()[0] % 16.0f);
			if (tmpX >= 4.0f) {
				return true;
			}
		}
		return false;
	}

	private boolean isEnemyClose() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++) {
				if (enemyField[i][j] != 0)
					return true;
			}
		}
		return false;
	}

	private boolean isEnemyFarAway() {
		for (int i = 2; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				if (enemyField[i][j] != 0)
					return true;
			}
		}
		return false;
	}

	private int bitsToNumber(boolean[] allStates) {
		String bitStr = new String();
		for (int i = 0; i < allStates.length; i++) {
			if (allStates[i])
				bitStr += "1";
			else
				bitStr += "0";
		}
		return Integer.parseInt(bitStr, 2);
	}

	private int getStateNumber() {
		return bitsToNumber(getStateArray());
	}

	private boolean canJump() {
		return (model.mayMarioJump() || !model.isMarioOnGround()) || model.getMarioCanJumpHigher();
	}

	public boolean[] numberToAction(int decision) { // min 1, max 12
		boolean[] res = new boolean[5];
		if (decision > 6) {
			decision -= 6;
			if (canJump())
				res[MarioActions.JUMP.getValue()] = true;
		}
		if (decision > 3) {
			decision -= 3;
			res[MarioActions.SPEED.getValue()] = true;
		}
		if (decision == 3)
			res[MarioActions.RIGHT.getValue()] = true;
		if (decision == 2)
			res[MarioActions.LEFT.getValue()] = true;
		return res;
	}

	boolean isRandomAction(int probability) {
		final int max = 100;
		final int min = 0;
		int randomNumber = random.nextInt(max + 1 - min) + min;
		return randomNumber > probability;
	}

	public boolean[] getRandomAction() {
		explorationActionNumber = random.nextInt(12) + 1;
		return numberToAction(explorationActionNumber);
	}

	public boolean[] getBestAction(int stateNumber) {
		return numberToAction(getBestActionNumberForState(stateNumber));
	}

	public double gamma = 0.05;
	public double alpha = 0.1;
	public double epsilon = 0.1;

	private double getQValue(int stateNumber, int actionNumber) {
		return qMappings.get(stateNumber)[actionNumber - 1];
	}

	private void setQValue(int stateNumber, int actionNumber, double value) {
		double[] qValues = qMappings.get(stateNumber);
		qValues[actionNumber - 1] = value;
	}



	private double getReward() {
		int res = 0;
		int nowXLocation = ((int) model.getMarioFloatPos()[0]) / 16;

		//res -= numFramesPassed*5;
		res += (nowXLocation-previousXLocation) * 100;
		if (prevMarioState < model.getMarioMode()) {
			prevMarioState = model.getMarioMode();
			res -= 10;
		}

		previousXLocation = nowXLocation;
		numFramesPassed = 0;
		return (double) res;
	}

	@Override
	public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
		this.model = model;
		updateFields();
		
		if(firstFrame){
			firstFrame = false;
			prevStateNumber = getStateNumber();
			getAction(prevStateNumber);
			return prevActions;
		}
		
		int stateNumber = getStateNumber();
		boolean[] actions = getAction(stateNumber);

		if (learning) {
			numFramesPassed++;
			if (isNewState(stateNumber)) {
				frameCounter = 0;
				updateQValue(stateNumber);
				explorationActionNumber = -1;
				if (isRandomAction((int) (epsilon * 100.0)))
					actions = getRandomAction();
				prevStateNumber = stateNumber;
			}
			prevActions = actions;
		}

		return actions;
	}

	private void updateQValue(int stateNumber) {
		int bestActionIndex = getBestActionNumberForState(stateNumber);
		double oldQValue = getOldQValue();
		double nextMaxQ = getQValue(stateNumber, bestActionIndex);
		double reward = getReward();

		double newValue = (1.0 - alpha) * oldQValue + alpha * (reward + gamma * nextMaxQ);
		setQValue(stateNumber, bestActionIndex, newValue);
	}

	private double getOldQValue() {
		double oldQValue = 0;
		if (explorationActionNumber != -1)
			oldQValue = getQValue(prevStateNumber, explorationActionNumber);
		else
			oldQValue = getQValue(prevStateNumber, getBestActionNumberForState(prevStateNumber));
		return oldQValue;
	}

	private boolean[] getAction(int stateNumber) {
		boolean[] tmpActions = getBestAction(stateNumber);
		if (explorationActionNumber != -1) {
			tmpActions = numberToAction(explorationActionNumber);
		}
		return tmpActions;
	}

	private boolean isNewState(int stateNumber) {
		frameCounter = (frameCounter + 1) % 11;
		return frameCounter == 10 || prevStateNumber != stateNumber;
	}

	@Override
	public String getAgentName() {
		return "Q-learning-Agent";
	}
	
	public QAgent(){
		random = new Random();
		qMappings = new HashMap<Integer, double[]>();
		prevStateNumber = -1;
		explorationActionNumber = -1;
	}

	private boolean firstFrame;
	@Override
	public void initialize(MarioForwardModel model, MarioTimer timer) {
		firstFrame = true;
		this.model = model;
		prevMarioState = model.getMarioMode();
		prevStateNumber = -1;
		explorationActionNumber = -1;
		prevActions = new boolean[] { false, false, false, false, false };
	}
}
