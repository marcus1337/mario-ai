import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import engine.core.MarioForwardModel;
import engine.core.MarioGame;
import engine.core.MarioResult;
import engine.core.MarioTimer;
import engine.core.MarioWorld;

public class PlayLevel {
	
	public static int sampleSize = 2;

    public static void main(String[] args) {   

    	String mapType = "notchParam";
		LevelHandler.initMaps();
    	
		makeElites(mapType);
		scoreElitesAndSaveStatistics(mapType);
		//showSingleEliteVisually(2, mapType);
    }
    
    public static void showSingleEliteVisually(int ID, String mapType){
    	
    	NEATTester neatTester = new NEATTester(1, "", mapType);
    	neatTester.loadBestElite(ID);
    
    	neatTester.levelHandler.runGameWithVisuals(neatTester.agents.get(0), 21);	
    	
    	neatTester.cleanUp();
    	
    }
    
	//neatTester.evolveNEATsFromScratch(maxGens);
	//neatTester.saveGenerationAndElites();
	//neatTester.loadAndShowEliteAgent(neatTester.eliteFolderName, 63, 21);
    
    public static double calcStandardDeviation(ArrayList<Double> table, double mean)
    {
        double temp = 0;
        for (int i = 0; i < table.size(); i++)
        {
            double val = table.get(i);
            double squrDiffToMean = Math.pow(val - mean, 2);
            temp += squrDiffToMean;
        }
        double meanOfDiffs = temp / (double) (table.size());
        return Math.sqrt(meanOfDiffs);
    }

	private static void scoreElitesAndSaveStatistics(String mapType) {
		ArrayList<Double> scores = new ArrayList<Double>();
		for(int testNum = 1; testNum < sampleSize + 1; testNum++){
			scores.add((double)scoreElite(mapType, testNum));
		}
		double mean = 0;
		
		for(int i = 0; i < scores.size(); i++){
			System.out.println(Integer.toString(i+1) + ": " + Double.toString(scores.get(i)));
			mean += scores.get(i);
		}
		mean /= scores.size();
		double standardDeviation = calcStandardDeviation(scores, mean);
		
		String scoreText = "";
		scoreText += "NUMBER OF SAMPLES: " + Integer.toString(scores.size()) + "\n";
		scoreText += "MEAN: " + Double.toString(mean) + "\n";
		scoreText += "STANDARD DEVIATION: " + Double.toString(standardDeviation) + "\n";
		scoreText += "\n---RAW SCORE DATA BELOW---\n\n";
		for(int i = 0 ; i < scores.size(); i++){
			scoreText += Double.toString(scores.get(i)) + "\n";
		}
		
		System.out.println(scoreText);
		
		try {
			try (PrintStream out = new PrintStream(new FileOutputStream("NEAT_Statistics.txt"))) {
			    out.print(scoreText);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
    
    private static float scoreElite(String mapType, int eliteID){
    	String neatName = "NEATS_" + mapType + "_" + Integer.toString(eliteID);
    	NEATTester neatTester = new NEATTester(1, neatName, mapType);
    	neatTester.loadBestElite(eliteID);
    	float result = neatTester.scoreElite();
    	neatTester.cleanUp();
    	return result;
    }

	private static void makeElites(String mapType) {
		for(int testNum = 1; testNum < sampleSize + 1; testNum++){
			LevelHandler.initEliteMap(mapType);
			String neatName = "NEATS_" + mapType + "_" + Integer.toString(testNum);
	    	NEATTester neatTester = new NEATTester(200, neatName, mapType);
	    	neatTester.evolveNEATsFromScratch( 10 * 60 * 1000);
	    	neatTester.saveBestElite(testNum);
	    	neatTester.cleanUp();
		}
	}
}
