package agents.BT.BT;

import engine.core.MarioForwardModel;

public class Decorator extends Interior{
	

	@Override
	public STATE run() {
		if(children.get(0).shouldRun()){
			STATE result = children.get(0).run();
			if(result == SUCCESS)
				result = FAILURE;
			else if(result == FAILURE)
				result = SUCCESS;
			return result;
		}
		return children.get(0).status;
	}
	
	@Override
	public NodeTypes getType(){
		return NodeTypes.DECORATOR;
	}

}
