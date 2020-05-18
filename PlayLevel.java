import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import engine.core.MarioGame;
import engine.core.MarioResult;

public class PlayLevel {

    public static void main(String[] args) {    	
    	GATester gaTester = new GATester(10);
    	
    	gaTester.evolveBTsFromScratch(10);
    	//gaTester.continueEvolveBTs(10, 9);
    	//gaTester.loadAndShowBTAgent(gaTester.fileNameBT, 9, 5);
    	
    	gaTester.cleanUp();
    }
}
