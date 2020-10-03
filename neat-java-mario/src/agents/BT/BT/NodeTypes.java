package agents.BT.BT;

public enum NodeTypes {
	OTHER_INTERIOR(0), UNORDERED_INTERIOR(1), DECORATOR(2), ACTION(3), CONDITION(4);

	private int id;
	public static final int maxOtherInteriorID = 3;
	public static final int maxUnorderedInteriorID = 0;
	public static final int maxDecoratorID = 0;
	public static final int maxActionID = 6;
	public static final int maxConditionID = new ConditionsDetailed(null).getNumConditions()-1;

	NodeTypes(int id) {
		this.id = id;
	}

	public int value() {
		return id;
	}

}
