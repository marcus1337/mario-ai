package MarioPackage;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import _GA_TESTS.Action;
import _GA_TESTS.DecisionLogger;
import _GA_TESTS.Observation;
import agents.human.Agent;
import engine.core.MarioGame;

public class GenerateLevel {
	
	public boolean isLoggingDecisions = true;
    private MarioGame game;
    private Observation observation;
    private Action action;
    private DecisionLogger decisionLogger;
	
	
	public GenerateLevel(){
		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

		if(path.getFileName().toString().equals("saves")) {
			String path2 = path.getParent().toAbsolutePath().toString() + "\\";
			LevelHandler.lvlsFolder = path2;
			System.out.println("Current path: (remember to put levels and images here) -- " + path2);
		}else {
			System.out.println("Folder path unchanged.");
			System.out.println("Current path: (remember to put levels and images here) -- " + path.toString());
		}
		
		LevelHandler.initMaps();
		observation = new Observation();
		action = new Action();
		decisionLogger = new DecisionLogger();
		
	}

	
	public void logDecisions() {
		observation.updateModel(game.getModel());
		decisionLogger.logDecision(action, observation);
	}
	
	public boolean[] getDiscreteObservations(){
		observation.updateModel(game.getModel());
		//System.out.println(observation.getStateArray().length);
		return observation.getStateArray();
	}
	
	public void setActions(boolean[] acts){
		action.updateModel(game.getModel());
		action.setActions(acts[0], acts[1], acts[2], acts[3], acts[4]);
	}
	public void step(){
		game.stepWorld(action.actions, action.shoot);
		
		if(isLoggingDecisions)
			logDecisions();
	}
	public void stepWithVisuals(){
		game.stepWorldWithVisuals(action.actions, action.shoot);
		
		if(isLoggingDecisions)
			logDecisions();
	}
	public float getReward(){
		return game.getReward3();
	}
	public boolean isDone(){
		return game.isGameDone();
	}
    
    public void initTrainingMap(String lvlName){
    	String lvlStr = LevelHandler.getRandomTrainingLevel(lvlName);
    	if(game == null)
    		game = new MarioGame();
    	game.initGame(lvlStr, LevelHandler.gameTimeSeconds, 2, false);
    }
    
    public void initTestMap(String lvlName){
    	String lvlStr = LevelHandler.getRandomTestLevel(lvlName);
    	System.out.println("Initializing visual feedback... (remember to put the img-folder in the saves folder to allow Java have the right path when running the python-script)");
    	if(game == null)
        	game = new MarioGame(true);
		game.initGame(lvlStr, LevelHandler.gameTimeSeconds, 2, true);
    }
    
    public void initTestMap(String lvlName, int lvlNumber){
    	String lvlStr = LevelHandler.getTestLevel(lvlName, lvlNumber);
    	if(game == null)
        	game = new MarioGame(); //Bool for visuals...
    	//System.out.println("NUM " + Integer.toString(lvlNumber) + ": " + lvlName);
		game.initGame(lvlStr, LevelHandler.gameTimeTestSeconds, 2, false);  //Bool for visuals...
		decisionLogger = new DecisionLogger();
    }
    
    public float getMapCompletionPercentage(){
    	return game.getResult().getCompletionPercentage();
    }

    //public static void main(String[] args) {
   // 	LevelHandler.initMaps();
    //	new MarioGame().runGame(new Agent(), LevelHandler.getExactLevel("levels/notchParam/lvl-100.txt"), 100, 2, true, 21); //Disable renderRectangles in MarioRenderer.java
    	//new MarioGame().runGame(new Agent(), LevelHandler.getRandomTrainingLevel("notchParam"), 100, 2, true, 21);
    	//new MarioGame().runGame(new Agent(), LevelHandler.getRandomTrainingLevel("notchMedium"), 100, 2, true, 21);
   //}
}
