package agents.BT.BT;

import engine.core.MarioForwardModel;

public class Conditions {
	Blackboard blackboard;
	public MarioForwardModel model;
	
	public Conditions(Blackboard blackboard){
		this.blackboard = blackboard;
	}
	
	STATE isBigMario(){
		if(model.getMarioMode() > 0)
			return Node.SUCCESS;
		return Node.FAILURE;
	}
	
	STATE isFireMario(){
		if(model.getMarioMode() == 2)
			return Node.SUCCESS;
		return Node.FAILURE;
	}
	
	private class Point{
		public int x; 
		public int y;
		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	STATE isObstacleAhead(){
		int[][] observ = model.getMarioCompleteObservation(2, 2);
		System.out.println(observ[0][0]);
		
		return Node.FAILURE;
	}
	
	public Task makeCondition(int ID){
		Task task = null;

		if(ID == 0)
			task = new Task(this::isBigMario, null);
		
		return task;
	}

}
