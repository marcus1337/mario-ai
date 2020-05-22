package _GA_TESTS;

import engine.core.MarioAgentEvent;
import engine.core.MarioEvent;
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
	
	private int addGameEndFitness(MarioResult result){
		if(result.getGameStatus() == GameStatus.LOSE)
			return -10;
		if(result.getGameStatus() == GameStatus.WIN)
			return (1000 + result.getRemainingTime());
		return 0;
	}
	
	public int addCompletionRateFitness(MarioResult result){
		int fitness = 0;
		float maxX = 0;
		for(MarioAgentEvent ev : result.getAgentEvents()){
			if(ev.getMarioX() > maxX)
			{
				maxX = ev.getMarioX();
			}
		}
		//fitness += (int)(result.getCompletionPercentage()*1000.0f);
		fitness += (int) (maxX);
		return fitness;
	}
	
	private int getNumAirTimeFrames(MarioResult result){
		int numAirTimeFrames = 0;
		for(MarioAgentEvent ev : result.getAgentEvents())
			if(!ev.getMarioOnGround())
				numAirTimeFrames++;
		return numAirTimeFrames;
	}
	
	private int getJumpFrequency(MarioResult result){
		float numAirTimeFrames = (float) getNumAirTimeFrames(result);
		float totalFrames = (float) result.getAgentEvents().size();
		if(totalFrames == 0)
			return 0;
		return (int)((numAirTimeFrames / totalFrames)*100.f);
	}
	
	public void setBehaviors(MarioResult result){
		result.gameCompletion = (int) (result.getCompletionPercentage()*100.0f);
		result.jumpFrequency = getJumpFrequency(result);
		result.numKills = Math.min(100, result.getKillsTotal());
	}
	
	public MarioResult simulateAndEvaluate(agents.BT.BTAgent agent){
		MarioResult result = runEasyGame(agent);
		
		result.fitness += addGameEndFitness(result);
		result.fitness += addCompletionRateFitness(result);
		result.fitness = Math.max(0, result.fitness);
		
		setBehaviors(result);
		return result;
	}
	
}
