package MarioPackage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import _GA_TESTS.Action;
import _GA_TESTS.Observation;
import agents.human.Agent;
import engine.core.MarioGame;
import engine.core.MarioLevelGenerator;
import engine.core.MarioLevelModel;
import engine.core.MarioResult;
import engine.core.MarioTimer;

public class GenerateLevel {
	
	public GenerateLevel(){
		LevelHandler.initMaps();
		observation = new Observation();
		action = new Action();
	}
	
	public boolean[] getDiscreteObservations(){
		observation.updateModel(game.getModel());
		return observation.getStateArray();
	}
	
	public void setActions(boolean[] acts){
		action.updateModel(game.getModel());
		action.setActions(acts[0], acts[1], acts[2], acts[3], acts[4]);
	}
	public void step(){
		game.stepWorld(action.actions, action.shoot);
	}
	public void stepWithVisuals(){
		game.stepWorldWithVisuals(action.actions, action.shoot);
	}
	public float getReward(){
		return game.getReward();
	}
	public boolean isDone(){
		return game.isGameDone();
	}
    
    private MarioGame game;
    private Observation observation;
    private Action action;
    
    public void initTrainingMap(String lvlName){
    	String lvlStr = LevelHandler.getRandomTrainingLevel(lvlName);
    	if(game == null)
    		game = new MarioGame();
    	game.initGame(lvlStr, LevelHandler.gameTimeSeconds, 2, false);
    }
    
    public void initTestMap(String lvlName){
    	String lvlStr = LevelHandler.getRandomTestLevel(lvlName);
    	if(game == null)
        	game = new MarioGame(true);
		game.initGame(lvlStr, LevelHandler.gameTimeSeconds, 2, true);
    }
    
    public void initTestMap(String lvlName, int lvlNumber){
    	String lvlStr = LevelHandler.getTestLevel(lvlName, lvlNumber);
    	if(game == null)
        	game = new MarioGame(true);
		game.initGame(lvlStr, LevelHandler.gameTimeSeconds, 2, true);
    }
    
    public float getMapCompletionPercentage(){
    	return game.getResult().getCompletionPercentage();
    }

    //public static void main(String[] args) {
    	//LevelHandler.initMaps();
    	//new MarioGame().runGame(new Agent(), LevelHandler.getRandomTrainingLevel("notchParam"), 100, 2, true, 21);
    	//new MarioGame().runGame(new Agent(), LevelHandler.getRandomTrainingLevel("notchMedium"), 100, 2, true, 21);
   //}
}
