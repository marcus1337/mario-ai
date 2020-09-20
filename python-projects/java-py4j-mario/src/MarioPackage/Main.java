package MarioPackage;

import py4j.GatewayServer;

public class Main {
	
	public static GatewayServer gatewayServer;
	
	public static void main(String[] args){
        startGateway();
	}

	private static void startGateway() {
		gatewayServer = new GatewayServer(new GenerateLevel());
        gatewayServer.start();
        System.out.println("Gateway Server Started");
	}
	
	public static void resetGateway(){
		gatewayServer.shutdown();
		gatewayServer.start();
	}

}
