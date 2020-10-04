import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import _GA_TESTS.Observation;
import engine.core.MarioResult;

public class NEATTester {

	private int numAI;
	private LevelHandler levelHandler;

	public String fileName;
	public String eliteFolderName;
	
	
	private JavaPorts evolver;
	private IntVec behavior;
	
	private ArrayList<NEATAgent> agents;
	

	public NEATTester(int numAI, String fileName, String mapType) {
		loadDll();
		evolver = new JavaPorts();
		behavior = new IntVec(new int[]{0,0,0});
		this.numAI = numAI;
		levelHandler = new LevelHandler(mapType);
		this.fileName = fileName; 
		eliteFolderName = fileName + "_Elite";
		evolver.init(Observation.numObservations, 5, numAI);
		
		agents = new ArrayList<NEATAgent>();
		for (int i = 0; i < numAI; i++)
			agents.add(new NEATAgent(evolver, i));
		
	}
	
	public void saveGenerationAndElites(){
		evolver.saveGeneration(fileName);
		evolver.saveElites(eliteFolderName);
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
	
	public void clearOldElites(){
		String path = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
		path = path.substring(0, path.length() - 1) + "NEAT_EVO\\"+eliteFolderName;
		Arrays.stream(new File(path).listFiles()).forEach(File::delete);
	}


	public void loadAndShowEliteAgent(String filename, int aiIndex, int fps) {
		evolver.loadElites(filename);
		//agents.get(0).isElite = true;
		levelHandler.runGameWithVisuals(agents.get(0), fps);		
	}

	public void setAIResults(int aiIndex, MarioResult marioResult) {
		int fitness = marioResult.fitness;
		evolver.setFitness(aiIndex, fitness);
		behavior.clear();
		behavior.add(marioResult.gameCompletion);
		behavior.add(marioResult.jumpFrequency);
		behavior.add(marioResult.numKills);		
		evolver.setBehavior(aiIndex, behavior);
	}

	public void cleanUp() {
		evolver.delete();
		behavior.delete();
	}

	private void evolveGeneration() {
		levelHandler.pickTrainingLevel();
		for (int aiIndex = 0; aiIndex < numAI; aiIndex++)
		{
			MarioResult marioResult = levelHandler.simulateAndEvaluate(agents.get(aiIndex));
			setAIResults(aiIndex, marioResult);
		}
		evolver.evolve();
	}
	
	private void testAndStoreElites(){
		for (int aiIndex = 0; aiIndex < numAI; aiIndex++)
		{
			MarioResult marioResult = levelHandler.simulateAndEvaluateElite(agents.get(aiIndex));
			setAIResults(aiIndex, marioResult);
		}
		evolver.storeElites();
	}

	private void evolveNEATs(int numGenerations) {
		for (int gen = 0; gen < numGenerations; gen++) {
			evolveGeneration();
			populationElitism(gen);
			System.out.println("Generations complete: " + (gen + 1));
		}
	}

	private void populationElitism(int gen) {
		if (gen > 0 && gen % 20 == 0){
			testAndStoreElites();
			evolver.randomizePopulationFromElites();
		}
	}

	public void evolveNEATsFromScratch(int numGenerations) {
		System.out.println("Starting up...");
		evolver.setSurpriseEffect(0.2f);
		System.out.println("Surprise effect configured...");
		evolveNEATs(numGenerations);
	}
}
