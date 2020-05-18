import java.util.ArrayList;

import _GA_TESTS.LevelHandler;
import agents.BT.BT.NodeTypes;
import engine.core.MarioResult;

public class GATester {
	
	private GALoader gaLoader;
	private int numAI;
	private LevelHandler levelHandler;
	
	public GATester(int numAI){
		this.numAI = numAI;
		gaLoader = new GALoader();
		levelHandler = new LevelHandler();
	}
	
	
	ArrayList<agents.BT.BTAgent> getBTAgents(JavaPorts evolver, int numAI){
		ArrayList<agents.BT.BTAgent> agents = new ArrayList<agents.BT.BTAgent>();
		for(int i = 0; i < numAI; i++){
			agents.BT.BTAgent agent = new agents.BT.BTAgent(evolver.getTreeString(i));
			agents.add(agent);
		}
		return agents;
	}
	
	public void loadAndShowBTAgent(agents.BT.BTAgent agent){
		levelHandler.runGameWithVisuals(agent);
	}
	
	private void evaluateBTAgent(JavaPorts evolver, agents.BT.BTAgent agent, int aiIndex){
		int fitness = levelHandler.simulateAndCheckFitness(agent);
		evolver.setFitness(aiIndex, fitness);
		System.out.println("fitness: " + fitness);
		
		if(fitness > 139)
			loadAndShowBTAgent(agent);
	}
	
	public void testBTGA(int numGenerations){
		JavaPorts evolver = gaLoader.getJavaPort(AIType.BT);
		evolver.init(numAI, NodeTypes.maxOtherInteriorID, NodeTypes.maxUnorderedInteriorID, 
				NodeTypes.maxDecoratorID, NodeTypes.maxActionID,NodeTypes.maxConditionID);
		
		for(int gen = 0; gen < numGenerations; gen++){
			ArrayList<agents.BT.BTAgent> agents = getBTAgents(evolver, numAI);
			for(int aiIndex = 0; aiIndex < numAI; aiIndex++)
				evaluateBTAgent(evolver, agents.get(aiIndex),aiIndex);
			
			evolver.saveGeneration("TREE_FIRST");
			evolver.evolve();
		}
		
		System.out.println("Done evolving BTs");
		gaLoader.cleanup();
	}

}
