package agents.BT.BT;

import java.util.ArrayList;
import java.util.Scanner;

public class Tree {

	Node root;
	
	public Tree(String treeString, Actions actions, Conditions conditions){
		Scanner fi = new Scanner(treeString);
		int numInteriors = fi.nextInt();
		NodeMap nodemap = new NodeMap();
		
		ArrayList<Node> interiors = new ArrayList<Node>();
		
		for(int i = 0 ; i < numInteriors; i++){
			int numChildren = fi.nextInt();
			int interiorType = fi.nextInt();
			int interiorID = fi.nextInt();
			Interior interior = (Interior) nodemap.getNode(interiorType, interiorID, actions, conditions);
			for(int j = 0 ; j < numChildren; j++){
				int childType = fi.nextInt();
				int childID = fi.nextInt();
				Node child = nodemap.getNode(childType, childID, actions, conditions);
				interior.addChild(child);
			}
			
			interiors.add(interior);
		}
	}

}
