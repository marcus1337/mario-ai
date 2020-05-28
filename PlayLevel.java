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
    	
    	//gaTester.evolveBTsFromScratch(50);
  
    	//gaTester.continueEvolveBTs(19, 19);
    	
    	//gaTester.loadAndShowBTAgent(gaTester.fileNameBT, 35, 5, 21);
    	//gaTester.loadAndShowEliteBTAgent(gaTester.eliteFolderName, 411, 21);
    	
    	JavaPorts evolver = gaTester.getEvolver();
    	evolver.loadElites(gaTester.eliteFolderName);
    	agents.BT.BTAgent agent = gaTester.getEliteBTAgent(evolver, 414);
    	agent.partiallyInitialize();
    	TreeVisualizer.visualizeBT(agent.tree);
    	
    	gaTester.cleanUp();
    }
}
