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
    	GATester gaTester = new GATester(100, "TEST_DIST");
    	
    	int maxGens = 100;
    	//gaTester.evolveBTsFromScratch(maxGens);
  
    	//gaTester.continueEvolveBTs(maxGens-8, 8);
    	
    	//gaTester.loadAndShowBTAgent(gaTester.fileNameBT, 30, 30, 21);
    	
    	gaTester.loadAndShowEliteBTAgent(gaTester.eliteFolderName, 190, 17);
    	
    	gaTester.cleanUp();
    }
}
