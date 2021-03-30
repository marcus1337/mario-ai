package MarioPackage;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

public class LevelHandler {
	
	public static final int gameTimeSeconds = 40, gameTimeTestSeconds = 60;
	private static String[] notchParamMaps;
	private static String[] notchMediumMaps;
	
	public static void initMaps(){
		notchParamMaps = new String[1001];
		notchMediumMaps = new String[1001];
		for(int i = 1; i < 1000; i++){
			notchParamMaps[i] = getLevel("notchParam", i);
			notchMediumMaps[i] = getLevel("notchMedium", i);
		}
	}
	
	public static String getRandomTrainingLevel(String lvlName) {
		if(lvlName.equals("notchParam"))
			return notchParamMaps[getRandomTrainingLvlNumber()];
		return notchMediumMaps[getRandomTrainingLvlNumber()];
	}
	
	public static String getTestLevel(String lvlName, int levelNumber) { //1 to 100 levels
		if(lvlName.equals("notchParam"))
			return notchParamMaps[levelNumber + 899];
		return notchMediumMaps[levelNumber + 899];
	}
	
	public static String getRandomTestLevel(String lvlName) {
		if(lvlName.equals("notchParam"))
			return notchParamMaps[getRandomTestLvlNumber()];
		return notchMediumMaps[getRandomTestLvlNumber()];
	}
	
	public static String getLevel(String lvlName, int lvl){
		if(lvlName.equals("notchMedium"))
			return getExactLevel("levels/notchMedium/" + "lvl-" + lvl + ".txt");
		return getExactLevel("levels/notchParam/" + "lvl-" + lvl + ".txt");
	}
	
	public static int getRandomTrainingLvlNumber(){
		return ThreadLocalRandom.current().nextInt(1, 899);
	}
	
	public static int getRandomTestLvlNumber(){
		return ThreadLocalRandom.current().nextInt(900, 999);
	}

	public static String getRandomTrainingLvl() {
		int randomNum = ThreadLocalRandom.current().nextInt(1, 899);
		return "lvl-" + randomNum + ".txt";
	}
	public static String getRandomTestLvl() {
		int randomNum = ThreadLocalRandom.current().nextInt(900, 999);
		return "lvl-" + randomNum + ".txt";
	}
	
    public static String getExactLevel(String filepath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {}
        return content;
    }

}
