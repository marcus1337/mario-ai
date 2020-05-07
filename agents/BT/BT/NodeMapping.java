package agents.BT.BT;

public enum NodeMapping {
	INTERIOR(0), DECORATOR(1), ACTION(2), CONDITION(4);

	private int id;
	public static final int maxInteriorID = 1;
	public static final int maxDecoratorID = 0;
	public static final int maxActionID = 0;
	public static final int maxConditionID = 0;

	NodeMapping(int id) {
		this.id = id;
	}

	public int value() {
		return id;
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
	
	public Task getAction(int ID){
		
		return null;
	}
	
	public Task getCondition(int ID){
		
		return null;
	}
}
