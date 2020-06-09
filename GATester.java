import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

	public JavaPorts getEvolver() {
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
	
	private MarioResult getMeanMarioResult(ArrayList<MarioResult> results){
		MarioResult result = results.get(0);
		for(int i = 1; i < results.size(); i++)
			result.add(results.get(i));
		result.divide(results.size());
		return result;
	}

	private void evaluateBTAgent(JavaPorts evolver, agents.BT.BTAgent agent, int aiIndex) {
		ArrayList<MarioResult> results = new ArrayList<MarioResult>();
		for(int i = 0; i < 4; i++){
			MarioResult marioResult = levelHandler.simulateAndEvaluate(agent, i);
			results.add(marioResult);
		}
		MarioResult result = getMeanMarioResult(results);
		setAIResults(evolver, aiIndex, result);
	}

	public void setAIResults(JavaPorts evolver, int aiIndex, MarioResult marioResult) {
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
		gaLoader.cleanup();
	}
	
	public void clearOldElites(){
		String path = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
		path = path.substring(0, path.length() - 1) + "BT_EVO\\"+eliteFolderName;
		Arrays.stream(new File(path).listFiles()).forEach(File::delete);
	}

	public void continueEvolveBTs(int numGenerations, int generationStart) {
		JavaPorts evolver = getAndInitBTEvolver();

		evolver.loadElites(eliteFolderName);
		evolver.loadGeneration(fileNameBT, generationStart);
		evolveBTs(evolver, numGenerations);
	}

	private void evolveBTGeneration(JavaPorts evolver) {
		
		simulateGeneration(evolver);

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
		for (int gen = 0; gen < numIterations; gen++) {
			evolveBTGeneration(evolver);
			if (gen > 0 && gen % 20 == 0){
				evolver.randomizePopulationFromElites();
			}
			System.out.println("Generations complete: " + (gen + 1));
		}
	}

	public void simulateGeneration(JavaPorts evolver) {
		levelHandler.prepareGenerationLevels();
		ArrayList<agents.BT.BTAgent> agents = getBTAgents(evolver, numAI);
		for (int aiIndex = 0; aiIndex < numAI; aiIndex++)
			evaluateBTAgent(evolver, agents.get(aiIndex), aiIndex);
	}

	public void randomizeMapElites(JavaPorts evolver, int numIterations) {
		int totalCounter = 1;
		for (int G = 0; G < numIterations; G++) {
			for (int i = 2; i <= 5; i++) {
				evolver.randomizePopulation(i, i);
				simulateGeneration(evolver);
				evolver.storeElites();
				evolver.saveElites(eliteFolderName);
				System.out.println("Randomizations done: " + totalCounter++);
			}
		}
	}

	public void evolveBTsFromScratch(int numGenerations) {
		JavaPorts evolver = getAndInitBTEvolver();
		clearOldElites();
		
		// evolver.loadElites(eliteFolderName);
		randomizeMapElites(evolver, 5);
		System.out.println("Randomization step done.----------------");

		evolver.setSurpriseEffect(0.2f);
		evolveBTs(evolver, numGenerations);
	}

}

// Instant start = Instant.now();
// Instant end = Instant.now();
// System.out.println(Duration.between(start, end).toMillis());
