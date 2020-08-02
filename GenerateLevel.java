import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import _GA_TESTS.Utils;
import agents.QLearning.QAgent;
import agents.human.Agent;
import engine.core.MarioGame;
import engine.core.MarioLevelGenerator;
import engine.core.MarioLevelModel;
import engine.core.MarioResult;
import engine.core.MarioTimer;

public class GenerateLevel {
    public static void printResults(MarioResult result) {
        System.out.println("****************************************************************");
        System.out.println("Game Status: " + result.getGameStatus().toString() +
                " Percentage Completion: " + result.getCompletionPercentage());
        System.out.println("Lives: " + result.getCurrentLives() + " Coins: " + result.getCurrentCoins() +
                " Remaining Time: " + (int) Math.ceil(result.getRemainingTime() / 1000f));
        System.out.println("Mario State: " + result.getMarioMode() +
                " (Mushrooms: " + result.getNumCollectedMushrooms() + " Fire Flowers: " + result.getNumCollectedFireflower() + ")");
        System.out.println("Total Kills: " + result.getKillsTotal() + " (Stomps: " + result.getKillsByStomp() +
                " Fireballs: " + result.getKillsByFire() + " Shells: " + result.getKillsByShell() +
                " Falls: " + result.getKillsByFall() + ")");
        System.out.println("Bricks: " + result.getNumDestroyedBricks() + " Jumps: " + result.getNumJumps() +
                " Max X Jump: " + result.getMaxXJump() + " Max Air Time: " + result.getMaxJumpAirTime());
        System.out.println("****************************************************************");
    }
    
    public static void generateAndSaveThousandLevels(MarioLevelGenerator generator, String fileName){
    	String path = "levels/" + fileName;
    	try {
			Files.createDirectories(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	for(int i = 1 ; i  <= 1000; i++){
        	String filePath = path + "/lvl-" + i + ".txt";
        	String levelString = generator.getGeneratedLevel(new MarioLevelModel(200, 20), new MarioTimer(5 * 60 * 60 * 1000));
        	try {
    			PrintWriter out = new PrintWriter(filePath);
    			out.println(levelString);
    			out.close();
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
    	}
    }

    public static void main(String[] args) {
        
        String level = Utils.getLevel("levels/notchParam/lvl-20.txt");
    	MarioGame game = new MarioGame();
       // Agent agent = new agents.human.Agent();
        
        QAgent agent = new agents.QLearning.QAgent();
        
        for(int i = 0 ; i < 1000; i++){
            game.runGame(agent, level, 30, 2, false);
            System.out.println("Done... " + i);
        }
        agent.learning = false;

        printResults(game.runGame(agent, level, 100, 2, true, 21));
    }
}
