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
	
	public static final int sampleSize = 5;
	public static final int minutesPerSample = 1;
	
	private static final String mapType1 = "notchParam";
	private static final String mapType2 = "notchMedium";

    public static void main(String[] args) {   

    	String mapType = mapType2;
		LevelHandler.initMaps();
    	boolean isExperimenting = false;
    	
		if(isExperimenting){
			//makeElites(mapType);
			scoreElitesAndSaveStatistics(mapType);
		}else
			showSingleEliteVisually(5, mapType);
    }
    
    public static void showSingleEliteVisually(int ID, String mapType){
    	
    	NEATTester neatTester = new NEATTester(1, "", mapType);
    	neatTester.loadBestElite(ID);
    
    	neatTester.levelHandler.runGameWithVisuals(neatTester.agents.get(0), 21);	
    	
    	neatTester.cleanUp();
    	
    }
    
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
			mean += scores.get(i);
		}
		mean /= scores.size();
		double standardDeviation = calcStandardDeviation(scores, mean);
		
		String scoreText = "";
		scoreText += "NUMBER OF SAMPLES: " + Integer.toString(scores.size()) + "\n";
		scoreText += "MEAN: " + Double.toString(mean) + "\n";
		scoreText += "STANDARD DEVIATION: " + Double.toString(standardDeviation) + "\n";
		scoreText += "\n---RAW SCORE DATA---\n\n";
		for(int i = 0 ; i < scores.size(); i++){
			scoreText += Double.toString(scores.get(i)) + "\n";
		}		
		
		System.out.println(scoreText);
		
		saveTextToFile(scoreText, "NEAT_Statistics.txt");
		
	}

	private static void saveTextToFile(String text, String fileName) {
		try {
			try (PrintStream out = new PrintStream(new FileOutputStream(fileName))) {
			    out.print(text);
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
	    	neatTester.evolveNEATsFromScratch( minutesPerSample * 60 * 1000);
	    	
	    	String eliteInfo = neatTester.getEliteInfo();
	    	System.out.println(eliteInfo);
	    	saveTextToFile(eliteInfo, "ELITE_INFO_" + Integer.toString(testNum));
	    	
	    	neatTester.saveBestElite(testNum);
	    	neatTester.cleanUp();
		}
	}
}
