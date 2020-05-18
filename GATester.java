import java.util.ArrayList;

import _GA_TESTS.LevelHandler;
import agents.BT.BT.NodeTypes;
import engine.core.MarioResult;

public class GATester {
	
	private GALoader gaLoader;
	private int numAI;
	private LevelHandler levelHandler;
	
	public String fileNameBT = "TREE_FIRST";
	
	public GATester(int numAI){
		this.numAI = numAI;
		gaLoader = new GALoader();
		levelHandler = new LevelHandler();
	}
	
	agents.BT.BTAgent getBTAgent(JavaPorts evolver, int index){
		return new agents.BT.BTAgent(evolver.getTreeString(index));
	}
	
	ArrayList<agents.BT.BTAgent> getBTAgents(JavaPorts evolver, int numAI){
		ArrayList<agents.BT.BTAgent> agents = new ArrayList<agents.BT.BTAgent>();
		for(int i = 0; i < numAI; i++)
			agents.add(getBTAgent(evolver,i));
		return agents;
	}
	
	public void loadAndShowBTAgent(String filename, int generation, int aiIndex){
		JavaPorts evolver = gaLoader.getJavaPort(AIType.BT);
		evolver.loadGeneration(filename, generation);
		showBTAgent(getBTAgent(evolver,aiIndex));
	}
	
	public void showBTAgent(agents.BT.BTAgent agent){
		levelHandler.runGameWithVisuals(agent);
	}
	
	private void evaluateBTAgent(JavaPorts evolver, agents.BT.BTAgent agent, int aiIndex){
		MarioResult marioResult = levelHandler.simulateAndCheckFitness(agent);
		int fitness = marioResult.fitness;
		evolver.setFitness(aiIndex, fitness);
		
		System.out.println("fitness: " + fitness);
	}
	
	public void cleanUp(){
		gaLoader.cleanup();
	}
	
	public void continueEvolveBTs(int numGenerations, int generationStart){
		JavaPorts evolver = getAndInitBTEvolver();
		evolver.loadGeneration(fileNameBT, generationStart);
		evolveBTs(evolver, numGenerations);
	}
	
	private void evolveBTGeneration(JavaPorts evolver){
		ArrayList<agents.BT.BTAgent> agents = getBTAgents(evolver, numAI);
		for(int aiIndex = 0; aiIndex < numAI; aiIndex++)
			evaluateBTAgent(evolver, agents.get(aiIndex),aiIndex);
		evolver.saveGeneration(fileNameBT);
		evolver.evolve();
	}
	
	private JavaPorts getAndInitBTEvolver(){
		JavaPorts evolver = gaLoader.getJavaPort(AIType.BT);
		evolver.init(numAI, NodeTypes.maxOtherInteriorID, NodeTypes.maxUnorderedInteriorID, 
				NodeTypes.maxDecoratorID, NodeTypes.maxActionID,NodeTypes.maxConditionID);
		return evolver;
	}
	
	private void evolveBTs(JavaPorts evolver, int numIterations){
		for(int gen = 0; gen < numIterations; gen++)
			evolveBTGeneration(evolver);
		System.out.println("Done evolving BTs");
	}
	
	public void evolveBTsFromScratch(int numGenerations){
		JavaPorts evolver = getAndInitBTEvolver();
		evolveBTs(evolver, numGenerations);
	}

}
