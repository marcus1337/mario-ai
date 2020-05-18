package _GA_TESTS;

import engine.core.MarioAgentEvent;
import engine.core.MarioGame;
import engine.core.MarioResult;
import engine.helper.GameStatus;

public class LevelHandler {
	private String level;
	private int marioStartState = 0;
	
	public LevelHandler(){
		prepareEasyLevel();
	}
	
	public void prepareEasyLevel(){
		level = Utils.getLevel("levels/original/lvl-1.txt");
	}
	
	public void runGameWithVisuals(agents.BT.BTAgent agent){
		MarioGame game = new MarioGame(); //21 fps is normal
		game.runGame(agent, level, 50, marioStartState, true, 300);
	}

	public MarioResult runEasyGame(agents.BT.BTAgent agent){
		MarioGame game = new MarioGame();
		return game.runGame(agent, level, 50, marioStartState, false);
	}
	
	public MarioResult simulateAndCheckFitness(agents.BT.BTAgent agent){
		MarioResult result = runEasyGame(agent);
		if(result.getGameStatus() == GameStatus.LOSE)
			result.fitness -= 10;
		if(result.getGameStatus() == GameStatus.WIN)
			result.fitness += (5000 + result.getRemainingTime());
		
		float maxX = 0;
		for(MarioAgentEvent ev : result.getAgentEvents()){
			if(ev.getMarioX() > maxX)
			{
				maxX = ev.getMarioX();
			}
		}
		result.fitness += (int)(result.getCompletionPercentage()*5000.0f);
		result.fitness += maxX/10;
		result.fitness  = Math.max(0, result.fitness);
		
		return result;
	}
	
}
