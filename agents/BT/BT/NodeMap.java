package agents.BT.BT;

public class NodeMap {
	static NodeTypes INTERIOR = NodeTypes.INTERIOR;
	static NodeTypes DECORATOR = NodeTypes.DECORATOR;
	static NodeTypes ACTION = NodeTypes.ACTION;
	static NodeTypes CONDITION = NodeTypes.CONDITION;
	
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
		Interior tmp = null;
		if(ID == 0)
			tmp = new Sequence();
		if(ID == 1)
			tmp = new Fallback();
		
		if(ID == 2)
			tmp = new Sequence();
		if(ID == 3)
			tmp = new Fallback();

		if(ID > 1)
			tmp.isMemoryNode = true;
		return tmp;
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
