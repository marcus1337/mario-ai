package _GA_TESTS;

import engine.core.MarioGame;
import engine.core.MarioResult;

public class LevelHandler {
	private String level;
	
	public LevelHandler(){
		prepareEasyLevel();
	}
	
	public void prepareEasyLevel(){
		level = Utils.getLevel("levels/original/lvl-1.txt");
	}
	
	public void runGameWithVisuals(agents.BT.BTAgent agent){
		MarioGame game = new MarioGame(); //21 fps is normal
		game.runGame(agent, level, 20, 2, true, 300);
	}

	public MarioResult runEasyGame(agents.BT.BTAgent agent){
		MarioGame game = new MarioGame();
		return game.runGame(agent, level, 50, 2, false);
	}
	
	public int simulateAndCheckFitness(agents.BT.BTAgent agent){
		MarioResult result = runEasyGame(agent);
		int fitnessVal = 0;
		fitnessVal = (int)(result.getCompletionPercentage()*1000.0f);
		return Math.max(0, fitnessVal);
	}
	
}
