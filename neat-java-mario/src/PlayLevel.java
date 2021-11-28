import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import engine.core.MarioForwardModel;
import engine.core.MarioGame;
import engine.core.MarioResult;
import engine.core.MarioTimer;
import engine.core.MarioWorld;

public class PlayLevel {
	
	public static final int sampleSize = 20;
	public static final int minutesPerSample = 60;
	
	private static final String mapType1 = "notchParam";
	private static final String mapType2 = "notchMedium";
	
	public static ArrayList<File> getContent(){
    	File contentDir = new File("NEAT_EVO//");
    	ArrayList<File> content = new ArrayList<File>();
    	if(contentDir.isDirectory()) {
    	    File[] tmpContent = contentDir.listFiles();
    	    for(int i = 0; i < tmpContent.length; i++) {
    	    	if(tmpContent[i].isFile() || (tmpContent[i].isDirectory() && tmpContent[i].getName().equals("BEST_ELITES")))
    	    		content.add(tmpContent[i]);
    	    }
    	}
    	return content;
	}
	
	public static void moveContentToFile(String fileName){
		
    	ArrayList<File> content = getContent();
		File dir = new File("NEAT_EVO//" + fileName);
		if (!dir.exists())
			dir.mkdirs();
		for(File file : content){
			File targetFile = new File("NEAT_EVO//" + fileName + "//" + file.getName());
			try {
				Files.move(file.toPath(), targetFile.toPath(), StandardCopyOption.ATOMIC_MOVE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    public static void main(String[] args) {      	  	

    	System.out.println("Starting....");
    	String mapType = mapType1;
		LevelHandler.initMaps();
    	boolean isExperimenting = false;
    	
    	logEliteDecisions();
		//if(isExperimenting){
		//	makeElites(mapType1, 1);
		//	scoreElitesAndSaveStatistics(mapType1);
			//makeElites(mapType2, 1);
			//scoreElitesAndSaveStatistics(mapType2);
		//}else
		//	showSingleEliteVisually(5, mapType);
    }
    
    public static void showSingleEliteVisually(int ID, String mapType){
    	
    	NEATTester neatTester = new NEATTester(1, "", mapType);
    	neatTester.loadBestElite(ID);
    	neatTester.levelHandler.runGameWithVisuals(neatTester.agents.get(0), 21);	
    	neatTester.cleanUp();
    	
    }
    
    public static void redirectOutputToFile(String filename) {
    	String foldername = "decisionLogs";
    	String filepath = foldername + "//" +filename;
        PrintStream o = null;
		File file = new File(foldername);
		if (!file.exists())
			file.mkdirs();
		
        try {
			o = new PrintStream(new File(filepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        System.setOut(o);
        
    }
    
    public static String getDecisionFileName(String mapType, int eliteID) {
    	return "neatDecisions_" + mapType + Integer.toString(eliteID) + ".txt";
    }
    
    public static void logEliteDecisions() {
    	LevelHandler.isLogDecisionsMode = true;
    	MarioGame.isTrainingMode = false;
    	for(int i = 1; i <= 20; i++) {
        	redirectOutputToFile(getDecisionFileName(mapType2, i));
        	scoreElite(mapType2, i);
    	}
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
		MarioGame.isTrainingMode = false;
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
    	moveContentToFile("NEAT_Results_"+mapType+"_"+Integer.toString(sampleSize)+"_"+Integer.toString(minutesPerSample));
		
	}

	private static void saveTextToFile(String text, String fileName) {
		try {
			try (PrintStream out = new PrintStream(new FileOutputStream("NEAT_EVO//"+fileName))) {
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

	private static void makeElites(String mapType, int startFrom) {
		MarioGame.isTrainingMode = true;
		for(int testNum = startFrom; testNum < sampleSize + 1; testNum++){
			LevelHandler.initEliteMap(mapType);
			String neatName = "NEATS_" + mapType + "_" + Integer.toString(testNum);
	    	NEATTester neatTester = new NEATTester(200, neatName, mapType);
	    	neatTester.evolveNEATsFromScratch( minutesPerSample * 1 * 1000);
	    	
	    	String eliteInfo = neatTester.getEliteInfo();
	    	System.out.println(eliteInfo);
	    	saveTextToFile(eliteInfo, "ELITE_INFO_" + Integer.toString(testNum));
	    	
	    	saveScoreHistory(mapType, testNum, neatTester);
	    	
	    	neatTester.saveBestElite(testNum);
	    	neatTester.cleanUp();
		}
	}

	private static void saveScoreHistory(String mapType, int testNum, NEATTester neatTester) {
		String generationalScores = "[ ";
		for(int i = 0 ; i < neatTester.maxGenerationReward.size(); i++) {
			generationalScores += Integer.toString(neatTester.maxGenerationReward.get(i));
			if(i != neatTester.maxGenerationReward.size()-1) {
				generationalScores += ",";
			}else {
				generationalScores += " ]";
			}
		}
		
		generationalScores += "\n";
		generationalScores += "[ ";
		
		for(int i = 0 ; i < neatTester.totalNumGameSteps.size(); i++) {
			generationalScores += Long.toString(neatTester.totalNumGameSteps.get(i));
			if(i != neatTester.totalNumGameSteps.size()-1) {
				generationalScores += ",";
			}else {
				generationalScores += " ]";
			}
		}
		
		String generationalScoreHistoryFileName = "NEAT_" + mapType + Integer.toString(testNum) + ".history";
		saveTextToFile(generationalScores, generationalScoreHistoryFileName);
	}
}
