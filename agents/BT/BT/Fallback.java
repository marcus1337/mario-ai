package agents.BT.BT;

import engine.core.MarioForwardModel;

public class Fallback extends Interior {

	@Override
	public STATE run(MarioForwardModel model) {
		for (int i = 0; i < children.size(); i++) {
			if (!children.get(i).shouldRun())
				return SUCCESS;
			STATE result = children.get(i).run(model);
			if (result != FAILURE)
				return result;
		}
		return FAILURE;
	}

}
