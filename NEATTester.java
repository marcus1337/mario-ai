import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import agents.BT.BT.NodeTypes;
import engine.core.MarioResult;

public class NEATTester {

	private int numAI;
	private LevelHandler levelHandler;

	public String fileNameBT;
	public String eliteFolderName;
	private JavaPorts evolver;
	
	

	public NEATTester(int numAI, String fileName) {
		loadDll();
		evolver = new JavaPorts(AIType.NEAT);
		this.numAI = numAI;
		levelHandler = new LevelHandler();
		fileNameBT = fileName; 
		eliteFolderName = fileNameBT + "_Elite";
		evolver.init(61, 5, numAI);
	}

	private void loadDll() {
		try {
			System.load("C:/Users/Marcus/Documents/swigwin-4.0.1/swigwin-4.0.1/Examples/JavaPorts/JavaPorts/GA_Ports.dll");
		} catch (UnsatisfiedLinkError e) {
			System.err.println(
					"Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n"
							+ e);
			System.exit(1);
		}
	}

	public NEATAgent getNEATAgent(int index) {
		return new NEATAgent(evolver, index);
	}

	public NEATAgent getEliteNEATAgent(int index) {
		return new NEATAgent(evolver, index, true);
	}

	ArrayList<NEATAgent> getNEATAgents(int numAI) {
		ArrayList<NEATAgent> agents = new ArrayList<NEATAgent>();
		for (int i = 0; i < numAI; i++)
			agents.add(getNEATAgent(i));
		return agents;
	}

	public void loadAndShowNEATAgent(String filename, int generation, int aiIndex, int fps) {
	
		evolver.loadGeneration(filename, generation);
		ArrayList<NEATAgent> agents = getNEATAgents(numAI);
		levelHandler.runGameWithVisuals(agents.get(aiIndex), fps);
	}

	public void loadAndShowEliteNEATAgent(String filename, int aiIndex, int fps) {

		evolver.loadElites(filename);
		levelHandler.runGameWithVisuals(getEliteNEATAgent(aiIndex), fps);
	}
	
	private MarioResult getMeanMarioResult(ArrayList<MarioResult> results){
		MarioResult result = results.get(0);
		for(int i = 1; i < results.size(); i++)
			result.add(results.get(i));
		result.divide(results.size());
		return result;
	}

	private void evaluateNEATAgent(NEATAgent agent, int aiIndex) {
		ArrayList<MarioResult> results = new ArrayList<MarioResult>();
		for(int i = 0; i < 4; i++){
			MarioResult marioResult = levelHandler.simulateAndEvaluate(agent, i);
			results.add(marioResult);
		}
		MarioResult result = getMeanMarioResult(results);
		setAIResults(aiIndex, result);
	}

	public void setAIResults(int aiIndex, MarioResult marioResult) {
		int fitness = marioResult.fitness;
		evolver.setFitness(aiIndex, fitness);
		IntVec behvaior = new IntVec(
				new int[] { marioResult.gameCompletion, marioResult.jumpFrequency, marioResult.numKills });
		evolver.setBehavior(aiIndex, behvaior);
		// printAIResult(marioResult, fitness);
	}

	public void printAIResult(MarioResult marioResult, int fitness) {
		System.out.println("fitness: " + fitness + " Finish: " + marioResult.gameCompletion + " Air-Time: "
				+ marioResult.jumpFrequency + " Kills: " + marioResult.numKills);
	}

	public void cleanUp() {
		evolver.delete();
	}
	
	public void clearOldElites(){
		String path = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
		path = path.substring(0, path.length() - 1) + "NEAT_EVO\\"+eliteFolderName;
		Arrays.stream(new File(path).listFiles()).forEach(File::delete);
	}

	public void continueEvolveBTs(int numGenerations, int generationStart) {

		evolver.loadElites(eliteFolderName);
		evolver.loadGeneration(fileNameBT, generationStart);
		evolveNEATs(numGenerations);
	}

	private void evolveBTGeneration() {
		simulateGeneration();
		System.out.println("before save");
		evolver.saveGeneration(fileNameBT);
		evolver.saveElites(eliteFolderName);
		evolver.evolve();
		System.out.println("after save");
	}

	private void evolveNEATs(int numIterations) {
		for (int gen = 0; gen < numIterations; gen++) {
			evolveBTGeneration();
			if (gen > 0 && gen % 20 == 0){
				evolver.randomizePopulationFromElites();
			}
			System.out.println("Generations complete: " + (gen + 1));
		}
	}

	public void simulateGeneration() {
		levelHandler.prepareGenerationLevels();
		ArrayList<NEATAgent> agents = getNEATAgents(numAI);
		for (int aiIndex = 0; aiIndex < numAI; aiIndex++)
			evaluateNEATAgent(agents.get(aiIndex), aiIndex);
	}

	public void randomizeMapElites(int numIterations) {
		int totalCounter = 1;
		for (int G = 0; G < numIterations; G++) {
			for (int i = 2; i <= 5; i++) {
				
				evolver.randomizePopulation(i, i);
				simulateGeneration();
				evolver.storeElites();
				evolver.saveElites(eliteFolderName);
				System.out.println("Randomizations done: " + totalCounter++);
			}
		}
	}

	public void evolveNEATsFromScratch(int numGenerations) {
		//clearOldElites();
		
		// evolver.loadElites(eliteFolderName);
		//randomizeMapElites(evolver, 5);
		System.out.println("Randomization step done.----------------");

		evolver.setSurpriseEffect(0.2f);
		System.out.println("surprise...");
		evolveNEATs(numGenerations);
	}
}
