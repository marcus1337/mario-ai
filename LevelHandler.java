

import java.util.concurrent.ThreadLocalRandom;

import _GA_TESTS.Utils;
import engine.core.MarioAgentEvent;
import engine.core.MarioEvent;
import engine.core.MarioGame;
import engine.core.MarioResult;
import engine.helper.GameStatus;

public class LevelHandler {
	private int marioStartState = 2;
	
	public static final int gameTimeSeconds = 40;

	public LevelHandler() {
	}

	public String getRandomLevel(int difficulty) {
		if (difficulty == 0)
			return Utils.getLevel("levels/notchEasy/" + getRandomLvl());
		if (difficulty == 1)
			return Utils.getLevel("levels/notchMedium/" + getRandomLvl());
		if (difficulty == 2)
			return Utils.getLevel("levels/notchParam/" + getRandomLvl());
		return Utils.getLevel("levels/hopper/" + getRandomLvl());
	}

	public void runGameWithVisuals(agents.BT.BTAgent agent, int fps) {
		MarioGame game = new MarioGame(); // 21 fps is normal
		game.runGame(agent, Utils.getLevel("levels/original/lvl-1.txt"), gameTimeSeconds, marioStartState, true, fps);
	}
	
	public void runGameWithVisuals(NEATAgent agent, int fps) {
		MarioGame game = new MarioGame(); // 21 fps is normal
		game.runGame(agent, Utils.getLevel("levels/original/lvl-3.txt"), gameTimeSeconds, marioStartState, true, fps);
	}

	public String getRandomLvl() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 998 + 1);
		return "lvl-" + randomNum + ".txt";
	}

	private int addGameEndFitness(MarioResult result) {
		if (result.getGameStatus() == GameStatus.LOSE)
			return -10;
		if (result.getGameStatus() == GameStatus.WIN)
			return 10 + result.getRemainingTime();
		return 0;
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

	String level1, level2, level3, level4;

	public void prepareGenerationLevels() {
		level1 = getRandomLevel(0);
		level2 = getRandomLevel(1);
		level3 = getRandomLevel(2);
		level4 = getRandomLevel(3);
	}

	private MarioResult runGameAndGetResult(agents.BT.BTAgent agent, int levelType) {
		MarioGame game = new MarioGame();
		if (levelType == 0)
			return game.runGame(agent, level1, gameTimeSeconds, marioStartState, false);
		if (levelType == 1)
			return game.runGame(agent, level2, gameTimeSeconds, marioStartState, false);
		if (levelType == 2)
			return game.runGame(agent, level3, gameTimeSeconds, marioStartState, false);
		return game.runGame(agent, level4, gameTimeSeconds, marioStartState, false);
	}
	
	private MarioResult runGameAndGetResult(NEATAgent agent, int levelType) {
		MarioGame game = new MarioGame();
		if (levelType == 0)
			return game.runGame(agent, level1, gameTimeSeconds, marioStartState, false);
		if (levelType == 1)
			return game.runGame(agent, level2, gameTimeSeconds, marioStartState, false);
		if (levelType == 2)
			return game.runGame(agent, level3, gameTimeSeconds, marioStartState, false);
		return game.runGame(agent, level4, gameTimeSeconds, marioStartState, false);
	}

	public MarioResult simulateAndEvaluate(agents.BT.BTAgent agent, int levelType) {
		MarioResult result = runGameAndGetResult(agent, levelType);
		result.fitness += addGameEndFitness(result);
		result.fitness += addCompletionRateFitness(result);
		result.fitness = Math.max(0, result.fitness);
		setBehaviors(result);
		return result;
	}
	
	public MarioResult simulateAndEvaluate(NEATAgent agent, int levelType) {
		MarioResult result = runGameAndGetResult(agent, levelType);
		result.fitness += addGameEndFitness(result);
		result.fitness += addCompletionRateFitness(result);
		result.fitness = Math.max(0, result.fitness);
		setBehaviors(result);
		return result;
	}

}
