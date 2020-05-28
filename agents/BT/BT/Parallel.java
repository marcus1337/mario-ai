package agents.BT.BT;

import java.util.ArrayList;
import java.util.Collections;

public class Parallel extends Interior {
	
	@Override
	public NodeTypes getType(){
		return NodeTypes.UNORDERED_INTERIOR;
	}

	ArrayList<Integer> getChildOrdering() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < children.size(); i++)
			result.add(i);
		Collections.shuffle(result);
		return result;
	}
	
	int successCounter = 0;
	int failureCounter = 0;
	int runningCounter = 0;
	
	private STATE getResult(){
		if(failureCounter > 0)
			return FAILURE;
		if(successCounter == children.size())
			return SUCCESS;
		return RUNNING;
	}

	@Override
	protected STATE runNormal() {
		successCounter = 0;
		failureCounter = 0;
		runningCounter = 0;

		ArrayList<Integer> ordering = getChildOrdering();
		for (int i = 0; i < ordering.size(); i++) {
			STATE taskResult = children.get(ordering.get(i)).run();
			if (taskResult == FAILURE)
				failureCounter++;
			if (taskResult == SUCCESS)
				successCounter++;
			if (taskResult == RUNNING)
				runningCounter++;
		}

		return getResult();
	}

	@Override
	protected STATE runMemory() {
		return runNormal();
	}

	@Override
	public NodeTypeDetailed getDetailedType() {
		return NodeTypeDetailed.PARALLEL;
	}

}
