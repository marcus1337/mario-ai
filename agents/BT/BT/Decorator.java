package agents.BT.BT;

import engine.core.MarioForwardModel;

public class Decorator extends Interior{
	

	@Override
	STATE run(MarioForwardModel model) {
		if(children.get(0).shouldRun()){
			STATE result = children.get(0).run(model);
			if(result == SUCCESS)
				result = FAILURE;
			else if(result == FAILURE)
				result = SUCCESS;
			return result;
		}
		return children.get(0).status;
	}

}
