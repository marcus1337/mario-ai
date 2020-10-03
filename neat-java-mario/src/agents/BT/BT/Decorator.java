package agents.BT.BT;

public class Decorator extends Interior{
	
	@Override
	public NodeTypes getType(){
		return NodeTypes.DECORATOR;
	}
	
	@Override
	public NodeTypeDetailed getDetailedType() {
		return NodeTypeDetailed.DECORATOR;
	}

	@Override
	protected STATE runMemory() {
		return runNormal();
	}

	@Override
	protected STATE runNormal() {
		STATE result = children.get(0).run();
		if(result == SUCCESS)
			result = FAILURE;
		else if(result == FAILURE)
			result = SUCCESS;
		return result;
	}

}
