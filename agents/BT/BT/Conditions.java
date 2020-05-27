package agents.BT.BT;

import _GA_TESTS.ReceptiveField;
import engine.core.MarioForwardModel;

public class Conditions {
	private Blackboard blackboard;
	private MarioForwardModel model;

	public Conditions(Blackboard blackboard) {
		this.blackboard = blackboard;
	}

	ReceptiveField recField = new ReceptiveField();
	int[][] field = null;
	int[][] enemyField = null;

	private int maxXPosReached = 0;
	private int framesAtMaxXPos = 0;
	private int frameCounter = 0;

	private int getFramesSinceMaxXPos() {
		return frameCounter - framesAtMaxXPos;
	}

	private void updateMaxXPos() {
		int nowXPos = (int) model.getMarioFloatPos()[0];
		if (nowXPos > maxXPosReached) {
			maxXPosReached = nowXPos;
			framesAtMaxXPos = frameCounter;
		}
	}

	private void updateIdleChecker() {
		frameCounter++;
		updateMaxXPos();
	}

	public void updateConditionParameters(MarioForwardModel model) {
		this.model = model;
		updateIdleChecker();
		field = recField.getBlockReceptiveField(model);
		enemyField = recField.getEnemyReceptiveField(model);
	}

	STATE isBigMario() {
		if (model.getMarioMode() > 0)
			return Node.SUCCESS;
		return Node.FAILURE;
	}

	STATE isFireMario() {
		if (model.getMarioMode() == 2)
			return Node.SUCCESS;
		return Node.FAILURE;
	}

	STATE isObstacleAhead() {
		for (int i = 0; i < recField.obstacleValues.size(); i++)
			if (recField.tunneledFieldContains(field, recField.obstacleValues.get(i)))
				return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	STATE isEnemyAhead() {
		if (recField.fieldContainsAnything(enemyField))
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	STATE isGapAhead() {
		if (recField.isGapAhead(field))
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	STATE isStuck() {
		if (getFramesSinceMaxXPos() > 150)
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	STATE upIsPushable() {
		if (recField.upIsPushable(field))
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	public Task makeCondition(int ID) {
		Task task = null;

		if (ID == 0)
			task = new Task(this::isBigMario, null);
		if (ID == 1)
			task = new Task(this::isFireMario, null);
		if (ID == 2)
			task = new Task(this::isObstacleAhead, null);
		if (ID == 3)
			task = new Task(this::isEnemyAhead, null);
		if (ID == 4)
			task = new Task(this::isGapAhead, null);
		if (ID == 5)
			task = new Task(this::isStuck, null);
		if (ID == 6)
			task = new Task(this::upIsPushable, null);

		return task;
	}

}
