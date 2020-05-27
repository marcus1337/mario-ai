import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import engine.core.MarioGame;
import engine.core.MarioResult;

public class PlayLevel {

    public static void main(String[] args) {    	
    	GATester gaTester = new GATester(100, "TEST_DIST");
    	
    	//gaTester.evolveBTsFromScratch(50);
  
    	//gaTester.continueEvolveBTs(19, 19);
    	
    	//gaTester.loadAndShowBTAgent(gaTester.fileNameBT, 35, 5, 21);
    	gaTester.loadAndShowEliteBTAgent(gaTester.eliteFolderName, 397, 21);
    	
    	gaTester.cleanUp();
    }
}
