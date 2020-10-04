

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import engine.core.MarioAgentEvent;
import engine.core.MarioEvent;
import engine.core.MarioForwardModel;
import engine.core.MarioGame;
import engine.core.MarioResult;
import engine.helper.GameStatus;

public class LevelHandler {
	private int marioStartState = 2;
	
	public static final int gameTimeSeconds = 40;
	
	public static MarioGame game = new MarioGame();

	public LevelHandler(String mapType) {
		this.mapType = mapType;
	}

	
	public void runGameWithVisuals(NEATAgent agent, int fps) {
		MarioGame game = new MarioGame(); // 21 fps is normal
		game.initRenderWindow2(2);
		String testMap = LevelHandler.getRandomTestLevel(mapType);
		game.initGameAndVisuals(testMap, LevelHandler.gameTimeSeconds, 2);
		
		while(!game.isGameDone()){
			MarioForwardModel model = game.getModel();
			agent.updateFields(model);
			agent.calculateInput();
			agent.setActions(model);
			game.stepWorldWithVisuals(agent.action.actions, agent.action.shoot);
		}
		
	}


	private int addGameEndFitness(MarioResult result) {
		int bonusScore = 0;
		if (result.getGameStatus() == GameStatus.LOSE)
			bonusScore -= 10;
		if (result.getGameStatus() == GameStatus.WIN)
			bonusScore += 10;
		if(result.getMarioMode() < 2)
			bonusScore -= 10;
		if(result.getMarioMode() < 1)
			bonusScore -= 10;
		return bonusScore;
	}

	public int addCompletionRateFitness(MarioResult result) {
		float maxX = 0;
		for (MarioAgentEvent ev : result.getAgentEvents())
			if (ev.getMarioX() > maxX)
				maxX = ev.getMarioX();
		return (int) ((maxX / result.getMaxXTile()) * 1000.0f);
	}

	private int getNumAirTimeFrames(MarioResult result) {
		int numAirTimeFrames = 0;
		for (MarioAgentEvent ev : result.getAgentEvents())
			if (!ev.getMarioOnGround())
				numAirTimeFrames++;
		return numAirTimeFrames;
	}

	private int getJumpFrequency(MarioResult result) {
		float numAirTimeFrames = (float) getNumAirTimeFrames(result);
		float totalFrames = (float) result.getAgentEvents().size();
		if (totalFrames == 0)
			return 0;
		return (int) ((numAirTimeFrames / totalFrames) * 100.f);
	}

	public void setBehaviors(MarioResult result) {
		result.gameCompletion = (int) (result.getCompletionPercentage() * 100.0f);
		result.jumpFrequency = getJumpFrequency(result);
		result.numKills = Math.min(100, result.getKillsTotal());
	}
	
	public void addBehaviors(MarioResult result) {
		result.gameCompletion += (int) (result.getCompletionPercentage() * 100.0f);
		result.jumpFrequency += getJumpFrequency(result);
		result.numKills += Math.min(100, result.getKillsTotal());
	}

	
	private MarioResult runGameAndGetResult(NEATAgent agent) {
		game.initGame(trainingLvl, LevelHandler.gameTimeSeconds, 2);
		while(!game.isGameDone()){
			MarioForwardModel model = game.getModel();
			agent.updateFields(model);
			agent.calculateInput();
			agent.setActions(model);

			game.stepWorld(agent.action.actions, agent.action.shoot);
		}
		return game.getResult();
	}
	
	public MarioResult simulateAndEvaluate(NEATAgent agent) {
		MarioResult result = runGameAndGetResult(agent);
		result.fitness += addGameEndFitness(result);
		result.fitness += addCompletionRateFitness(result);
		result.fitness = Math.max(0, result.fitness);
		setBehaviors(result);
		return result;
	}
	
	private MarioResult simulateAndEvaluateElite(NEATAgent agent, int lvlNumber) {
		trainingLvl = LevelHandler.getLevel(mapType, lvlNumber);
		MarioResult result = runGameAndGetResult(agent);
		result.fitness += addGameEndFitness(result);
		result.fitness += addCompletionRateFitness(result);
		result.fitness = Math.max(0, result.fitness);
		setBehaviors(result);
		return result;
	}
	
	private MarioResult getMeanMarioResult(ArrayList<MarioResult> results){
		MarioResult result = results.get(0);
		for(int i = 1; i < results.size(); i++)
			result.add(results.get(i));
		result.divide(results.size());
		return result;
	}
	
	public MarioResult simulateAndEvaluateElite(NEATAgent agent){
		ArrayList<MarioResult> results = new ArrayList<MarioResult>();
		for(int i = 0 ; i < 5; i++)
			results.add(simulateAndEvaluateElite(agent, i));
		return getMeanMarioResult(results);
	}
	
	//////////////////////////////
	
	private String trainingLvl;
	private static String[] notchParamMaps;
	private static String[] notchMediumMaps;
	
	public static void initMaps(){
		notchParamMaps = new String[1001];
		notchMediumMaps = new String[1001];
		for(int i = 1; i < 1000; i++){
			notchParamMaps[i] = getLevel("notchParam", i);
			notchMediumMaps[i] = getLevel("notchMedium", i);
		}
	}
	
	public static String getRandomTrainingLevel(String lvlName) {
		if(lvlName.equals("notchParam"))
			return notchParamMaps[getRandomTrainingLvlNumber()];
		return notchMediumMaps[getRandomTrainingLvlNumber()];
	}
	
	public static String getTestLevel(String lvlName, int levelNumber) { //1 to 100 levels
		if(lvlName.equals("notchParam"))
			return notchParamMaps[levelNumber + 899];
		return notchMediumMaps[levelNumber + 899];
	}
	
	public static String getRandomTestLevel(String lvlName) {
		if(lvlName.equals("notchParam"))
			return notchParamMaps[getRandomTestLvlNumber()];
		return notchMediumMaps[getRandomTestLvlNumber()];
	}
	
	public static String getLevel(String lvlName, int lvl){
		if(lvlName.equals("notchMedium"))
			return getExactLevel("levels/notchMedium/" + "lvl-" + lvl + ".txt");
		return getExactLevel("levels/notchParam/" + "lvl-" + lvl + ".txt");
	}
	
	public static int getRandomTrainingLvlNumber(){
		return ThreadLocalRandom.current().nextInt(1, 899);
	}
	
	public static int getRandomTestLvlNumber(){
		return ThreadLocalRandom.current().nextInt(900, 999);
	}

	public static String getRandomTrainingLvl() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 899);
		return "lvl-" + randomNum + ".txt";
	}
	public static String getRandomTestLvl() {
		int randomNum = ThreadLocalRandom.current().nextInt(900, 999);
		return "lvl-" + randomNum + ".txt";
	}
	
    private static String getExactLevel(String filepath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {}
        return content;
    }
    
    public String mapType;

	public void pickTrainingLevel() {
		trainingLvl = getRandomTrainingLevel(mapType);
	}

}
