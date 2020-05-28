import java.util.ArrayList;

import _GA_TESTS.LevelHandler;
import agents.BT.BT.NodeTypes;
import engine.core.MarioResult;

public class GATester {

	private GALoader gaLoader;
	private int numAI;
	private LevelHandler levelHandler;

	public String fileNameBT;
	public String eliteFolderName;

	public GATester(int numAI, String fileName) {
		this.numAI = numAI;
		gaLoader = new GALoader();
		levelHandler = new LevelHandler();
		fileNameBT = fileName; // "TREE_FIRST";
		eliteFolderName = fileNameBT + "_Elite";
	}

	public agents.BT.BTAgent getBTAgent(JavaPorts evolver, int index) {
		return new agents.BT.BTAgent(evolver.getTreeString(index));
	}
	
	public agents.BT.BTAgent getEliteBTAgent(JavaPorts evolver, int index) {
		return new agents.BT.BTAgent(evolver.getEliteTreeString(index));
	}

	ArrayList<agents.BT.BTAgent> getBTAgents(JavaPorts evolver, int numAI) {
		ArrayList<agents.BT.BTAgent> agents = new ArrayList<agents.BT.BTAgent>();
		for (int i = 0; i < numAI; i++)
			agents.add(getBTAgent(evolver, i));
		return agents;
	}
	
	public JavaPorts getEvolver(){
		return gaLoader.getJavaPort(AIType.BT);
	}

	public void loadAndShowBTAgent(String filename, int generation, int aiIndex, int fps) {
		JavaPorts evolver = gaLoader.getJavaPort(AIType.BT);
		evolver.loadGeneration(filename, generation);
		levelHandler.runGameWithVisuals(getBTAgent(evolver, aiIndex), fps);
	}
	
	public void loadAndShowEliteBTAgent(String filename, int aiIndex, int fps) {
		JavaPorts evolver = gaLoader.getJavaPort(AIType.BT);
		evolver.loadElites(filename);
		levelHandler.runGameWithVisuals(getEliteBTAgent(evolver, aiIndex), fps);
	}

	private void evaluateBTAgent(JavaPorts evolver, agents.BT.BTAgent agent, int aiIndex) {
		MarioResult marioResult = levelHandler.simulateAndEvaluate(agent);
		int fitness = marioResult.fitness;
		evolver.setFitness(aiIndex, fitness);
		IntVec behvaior = new IntVec(
				new int[] { marioResult.gameCompletion, marioResult.jumpFrequency, marioResult.numKills });
		evolver.setBehavior(aiIndex, behvaior);

		//System.out.println("fitness: " + fitness + " Finish: " + marioResult.gameCompletion + " Air-Time: "
		//		+ marioResult.jumpFrequency + " Kills: " + marioResult.numKills);
	}

	public void cleanUp() {
		gaLoader.cleanup();
	}

	public void continueEvolveBTs(int numGenerations, int generationStart) {
		JavaPorts evolver = getAndInitBTEvolver();

		evolver.loadGeneration(fileNameBT, generationStart);
		evolveBTs(evolver, numGenerations);
	}

	private void evolveBTGeneration(JavaPorts evolver) {
		ArrayList<agents.BT.BTAgent> agents = getBTAgents(evolver, numAI);
		for (int aiIndex = 0; aiIndex < numAI; aiIndex++)
			evaluateBTAgent(evolver, agents.get(aiIndex), aiIndex);
		evolver.saveGeneration(fileNameBT);
		evolver.saveElites(eliteFolderName);
		
		evolver.evolve();
	}

	private JavaPorts getAndInitBTEvolver() {
		JavaPorts evolver = gaLoader.getJavaPort(AIType.BT);
		evolver.init(numAI, NodeTypes.maxOtherInteriorID, NodeTypes.maxUnorderedInteriorID, NodeTypes.maxDecoratorID,
				NodeTypes.maxActionID, NodeTypes.maxConditionID);
		return evolver;
	}

	private void evolveBTs(JavaPorts evolver, int numIterations) {
		for (int gen = 0; gen < numIterations; gen++){
			evolveBTGeneration(evolver);
			if(gen % 15 == 0){
				evolver.randomizePopulationFromElites();
			}
			System.out.println("Generations complete: " + (gen+1));
		}
	}

	public void randomizeMapElites(JavaPorts evolver, int numIterations) {
		int totalCounter = 1;
		for (int G = 0; G < numIterations; G++){
			for (int i = 2; i <= 10; i++) {
				evolver.randomizeBTPopulation(i, i);
				ArrayList<agents.BT.BTAgent> agents = getBTAgents(evolver, numAI);
				for (int aiIndex = 0; aiIndex < numAI; aiIndex++)
					evaluateBTAgent(evolver, agents.get(aiIndex), aiIndex);
				System.out.println("Randomizations done: " + totalCounter++);
			}
		}
	}

	public void evolveBTsFromScratch(int numGenerations) {
		 JavaPorts evolver = getAndInitBTEvolver();
		
		 //evolver.loadElites(eliteFolderName);
		 randomizeMapElites(evolver, 2);
		 System.out.println("Randomization step done.----------------");
		
		 evolver.setSurpriseEffect(0.2f);
		 evolveBTs(evolver, numGenerations);
	}

}
