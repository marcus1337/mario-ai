package agents.BT.BT;

public class Decorator extends Interior{
	

	@Override
	STATE run() {
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

}
