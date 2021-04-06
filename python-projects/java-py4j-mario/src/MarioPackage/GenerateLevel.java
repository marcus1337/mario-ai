package MarioPackage;
import _GA_TESTS.Action;
import _GA_TESTS.Observation;
import agents.human.Agent;
import engine.core.MarioGame;

public class GenerateLevel {
	
	public GenerateLevel(){
		System.out.println("Starting something...");
		LevelHandler.initMaps();
		observation = new Observation();
		action = new Action();
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
	}
	public void stepWithVisuals(){
		game.stepWorldWithVisuals(action.actions, action.shoot);
	}
	public float getReward(){
		return game.getReward2();
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
    	System.out.println("Only video?");
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
    }
    
    public float getMapCompletionPercentage(){
    	return game.getResult().getCompletionPercentage();
    }

    //public static void main(String[] args) {
    //	LevelHandler.initMaps();
    //	new MarioGame().runGame(new Agent(), LevelHandler.getExactLevel("levels/notchMedium/lvl-100.txt"), 100, 2, true, 21); //Disable renderRectangles in MarioRenderer.java
    	//new MarioGame().runGame(new Agent(), LevelHandler.getRandomTrainingLevel("notchParam"), 100, 2, true, 21);
    	//new MarioGame().runGame(new Agent(), LevelHandler.getRandomTrainingLevel("notchMedium"), 100, 2, true, 21);
  // }
}
