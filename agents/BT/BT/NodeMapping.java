package agents.BT.BT;

public enum NodeMapping {
	INTERIOR(0), DECORATOR(1), ACTION(2), CONDITION(4);

	private int id;
	public static final int maxInteriorID = 1;
	public static final int maxDecoratorID = 0;
	public static final int maxActionID = 9;
	public static final int maxConditionID = 0;

	NodeMapping(int id) {
		this.id = id;
	}

	public int value() {
		return id;
	}
	
	public Node getNode(int typeID, int ID, Actions actions, Conditions conditions){
		if(typeID == INTERIOR.value())
			return getInterior(ID);
		if(typeID == DECORATOR.value())
			return getDectorator(ID);
		if(typeID == ACTION.value())
			return getAction(ID, actions);
		if(typeID == CONDITION.value())
			return getCondition(ID, conditions);
		return null;
	}
	
	public Interior getInterior(int ID){
		if(ID == 0)
			return new Sequence();
		if(ID == 1)
			return new Fallback();
		return null;
	}
	
	public Decorator getDectorator(int ID){
		return new Decorator();
	}
	
	public Task getAction(int ID, Actions actions){
		return actions.makeAction(ID);
	}
	
	public Task getCondition(int ID, Conditions conditions){
		return conditions.makeCondition(ID);
	}
}
