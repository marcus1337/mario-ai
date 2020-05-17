package _GA_TESTS;

import engine.core.MarioGame;
import engine.core.MarioResult;

public class LevelHandler {

	
	public MarioResult runGame(agents.BT.BTAgent agent){
		MarioGame game = new MarioGame();
		String level = Utils.getLevel("levels/original/lvl-1.txt");
		
		return game.runGame(agent, level, 120, 2, true, 21);
	}
}
