package agents.BT.BT;

import engine.core.MarioForwardModel;

public class Sequence extends Interior {

	@Override
	public STATE run(MarioForwardModel model) {
		for(int i = 0 ; i < children.size(); i++){
			if(children.get(i).shouldRun()){
				STATE result = children.get(i).run(model);
				if(result != SUCCESS)
					return result;
			}
		}
		return SUCCESS;
	}

}
