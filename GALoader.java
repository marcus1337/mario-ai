import java.util.ArrayList;

public class GALoader {
	static {
		try {
			System.loadLibrary("GA_Ports");
		} catch (UnsatisfiedLinkError e) {
			System.err.println(
					"Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n"
							+ e);
			System.exit(1);
		}
	}
	private ArrayList<JavaPorts> allGAs;
	public GALoader(){
		allGAs = new ArrayList<JavaPorts>();
	}
	
	public JavaPorts getJavaPort(AIType aiType){
		JavaPorts javaport = new JavaPorts(aiType);
		allGAs.add(javaport);
		return javaport;
	}
	
	public void cleanup(){
		for(int i = 0 ; i < allGAs.size();i++){
			allGAs.get(i).delete();
		}
		allGAs = new ArrayList<JavaPorts>();
	}
	
}
