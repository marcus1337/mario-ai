import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import engine.core.MarioForwardModel;
import engine.core.MarioGame;
import engine.core.MarioResult;
import engine.core.MarioTimer;
import engine.core.MarioWorld;

public class PlayLevel {

    public static void main(String[] args) {   
    	int maxGens = 50;
    	String mapType = "notchParam";
		LevelHandler.initMaps();
    	
    	NEATTester neatTester = new NEATTester(100, "NEAT_DIST1", mapType);
    	//neatTester.evolveNEATsFromScratch(maxGens);
    	
    	
    	
    	//neatTester.loadAndShowNEATAgent(neatTester.fileNameBT, 5, 5, 21);
    	neatTester.loadAndShowEliteAgent(neatTester.eliteFolderName, 0, 21);
    	
    	//neatTester.saveGenerationAndElites();
    	neatTester.cleanUp();
    	
    }
}
