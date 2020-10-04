import java.util.ArrayList;

public class GALoader {
	static {
		try {
			System.load("C:/Users/Marcus/Documents/swigwin-4.0.1/swigwin-4.0.1/Examples/JavaPorts/JavaPorts/GA_Ports.dll");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("SWIG load fail.\n" + e);
			System.exit(1);
		}
	}
	JavaPorts javaport;
	
	public JavaPorts getJavaPort(){
		javaport = new JavaPorts();
		return javaport;
	}
	
	public void cleanup(){
		javaport.delete();
	}
	
}
