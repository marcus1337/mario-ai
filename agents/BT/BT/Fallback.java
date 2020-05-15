package agents.BT.BT;

public class Fallback extends Interior {
	
	@Override
	public NodeTypes getType(){
		return NodeTypes.OTHER_INTERIOR;
	}

	@Override
	protected STATE runNormal(){
		for (int i = 0; i < children.size(); i++) {
			STATE result = children.get(i).run();
			if (result != FAILURE)
				return result;
		}
		return FAILURE;
	}

	@Override
	protected STATE runMemory() {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).shouldRun()) {
				STATE result = children.get(i).runAndUpdate();
				if (result != FAILURE)
					return result;
			}
		}
		reset();
		return FAILURE;
	}

}
