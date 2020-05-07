package agents.BT.BT;

public enum NodeTypes {
	INTERIOR(0), DECORATOR(1), ACTION(2), CONDITION(4);

	private int id;
	public static final int maxInteriorID = 1;
	public static final int maxDecoratorID = 0;
	public static final int maxActionID = 9;
	public static final int maxConditionID = 0;

	NodeTypes(int id) {
		this.id = id;
	}

	public int value() {
		return id;
	}

}
