package agents.BT.BT;

public class Fallback extends Interior {

	@Override
	STATE run() {
		for (int i = 0; i < children.size(); i++) {
			if (!children.get(i).shouldRun())
				return SUCCESS;
			STATE result = children.get(i).run();
			if (result != FAILURE)
				return result;
		}
		return FAILURE;
	}

}
