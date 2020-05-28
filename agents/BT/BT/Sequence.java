package agents.BT.BT;

public class Sequence extends Interior {
	
	@Override
	public NodeTypeDetailed getDetailedType() {
		if(this.isMemoryNode)
			return NodeTypeDetailed.SEQUENCE_MEMORY;
		return NodeTypeDetailed.SEQUENCE;
	}
	
	@Override
	public NodeTypes getType(){
		return NodeTypes.OTHER_INTERIOR;
	}

	@Override
	protected STATE runNormal(){
		for (int i = 0; i < children.size(); i++) {
			STATE result = children.get(i).run();
			if (result != SUCCESS)
				return result;
		}
		return SUCCESS;
	}

	@Override
	protected STATE runMemory() {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).shouldRun()) {
				STATE result = children.get(i).runAndUpdate();
				if (result != SUCCESS)
					return result;
			}
		}
		reset();
		return SUCCESS;
	}

}
