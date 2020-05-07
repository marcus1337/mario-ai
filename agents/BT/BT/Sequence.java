package agents.BT.BT;

public class Sequence extends Interior {

	@Override
	STATE run() {
		for(int i = 0 ; i < children.size(); i++){
			if(children.get(i).shouldRun()){
				STATE result = children.get(i).run();
				if(result != SUCCESS)
					return result;
			}
		}
		return SUCCESS;
	}

}
