import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import engine.core.MarioGame;
import engine.core.MarioResult;

public class PlayLevel {

    public static void main(String[] args) {    	
    	GATester gaTester = new GATester(100);
    	
    	//gaTester.evolveBTsFromScratch(20);
    	gaTester.continueEvolveBTs(17, 10);
    	//gaTester.loadAndShowBTAgent(gaTester.fileNameBT, 17, 93);
    	
    	gaTester.cleanUp();
    }
}
