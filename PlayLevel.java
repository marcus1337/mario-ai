import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import engine.core.MarioForwardModel;
import engine.core.MarioGame;
import engine.core.MarioResult;
import engine.core.MarioTimer;
import engine.core.MarioWorld;
import visuals.TreeVisualizer;

public class PlayLevel {

    public static void main(String[] args) {   
    	int maxGens = 100;
    	
    	NEATTester neatTester = new NEATTester(100, "NEAT_DIST");
    	//neatTester.evolveNEATsFromScratch(maxGens);
    	
    	//neatTester.loadAndShowNEATAgent(neatTester.fileNameBT, 5, 5, 21);
    	neatTester.loadAndShowEliteNEATAgent(neatTester.eliteFolderName, 0, 21);
    	neatTester.cleanUp();
    	
    	
    	
    	//GATester gaTester = new GATester(100, "TEST_DIST");
    	//gaTester.evolveBTsFromScratch(maxGens);
    	//gaTester.continueEvolveBTs(maxGens-8, 8);
    	//gaTester.loadAndShowBTAgent(gaTester.fileNameBT, 30, 30, 21);
    	//gaTester.loadAndShowEliteBTAgent(gaTester.eliteFolderName, 161, 17);
    	//gaTester.cleanUp();
    }
}
