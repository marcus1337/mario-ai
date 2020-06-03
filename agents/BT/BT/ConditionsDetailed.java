package agents.BT.BT;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

import _GA_TESTS.ReceptiveField;
import engine.core.MarioForwardModel;

public class ConditionsDetailed {
	private Blackboard blackboard;
	private MarioForwardModel model;
	
	private ArrayList<Task> allTasks;
	
	public int getNumConditions(){
		return getAllTasks().size();
	}
	
	public ArrayList<Task> getAllTasks(){
		ArrayList<Task> tasks = getBlockConditions();
		tasks.addAll(getEnemyConditions());
		Task checkIfFacingRight = new Task(this::isFacingRight, null);
		checkIfFacingRight.description = "Facing R";
		tasks.add(checkIfFacingRight);
		return tasks;
	}

	public ConditionsDetailed(Blackboard blackboard) {
		this.blackboard = blackboard;
		allTasks = getAllTasks();
	}

	ReceptiveField recField = new ReceptiveField();
	int[][] field = null;
	int[][] enemyField = null;

	public void updateConditionParameters(MarioForwardModel model) {
		this.model = model;
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

	STATE isBlocked(int x, int y) {
		if (field[x][y] != 0)
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	STATE isEnemy(int x, int y) {
		if (enemyField[x][y] != 0)
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}
	
	STATE isFacingRight(){
		if(model.isFacingRight())
			return STATE.SUCCESS;
		return STATE.FAILURE;
	}

	ArrayList<Task> getBlockConditions() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 6; y++) {
				final int tmpX = x;
				final int tmpY = y;
				Supplier<STATE> condition = () -> {
					return isBlocked(tmpX, tmpY);
				};
				Task task = new Task(condition, null);
				task.description = "B<" + (tmpX+1) + "," + (tmpY+1) + ">";
				tasks.add(task);
			}
		}
		return tasks;
	}
	
	ArrayList<Task> getEnemyConditions() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 6; y++) {
				final int tmpX = x;
				final int tmpY = y;
				Supplier<STATE> condition = () -> {
					return isEnemy(tmpX, tmpY);
				};
				Task task = new Task(condition, null);
				task.description = "E<" + (tmpX+1) + "," + (tmpY+1) + ">";
				tasks.add(task);
			}
		}
		return tasks;
	}

	public static <T, R> Supplier<R> bind(Function<T, R> fn, T val) {
		return () -> fn.apply(val);
	}

	public Task makeCondition(int ID) {
		return allTasks.get(ID);
	}
}
