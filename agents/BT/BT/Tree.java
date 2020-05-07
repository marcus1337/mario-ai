package agents.BT.BT;

import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Scanner;

public class Tree {

	public Node root;
	NodeMap nodemap;
	public Actions actions;
	public Conditions conditions;
	public Blackboard blackboard;
	
	private void init(){
		nodemap = new NodeMap();
		blackboard = new Blackboard();
		actions = new Actions(blackboard);
		conditions = new Conditions(blackboard);
	}
	
	void addChildrenToInterior(Interior interior, int numChildren, Scanner fi){
		for(int j = 0 ; j < numChildren; j++){
			int childType = fi.nextInt();
			int childID = fi.nextInt();
			Node child = nodemap.getNode(childType, childID, actions, conditions);
			interior.addChild(child);
		}
	}
	
	void addInterior(ArrayList<Node> interiors, Scanner fi){
		int numChildren = fi.nextInt();
		int interiorType = fi.nextInt();
		int interiorID = fi.nextInt();
		
		Interior interior = (Interior) nodemap.getNode(interiorType, interiorID, actions, conditions);
		addChildrenToInterior(interior, numChildren, fi);
		interiors.add(interior);
	}
	
	public Tree(String treeString){
		init();

		Scanner fi = new Scanner(treeString);
		int numInteriors = fi.nextInt();
		ArrayList<Node> interiors = new ArrayList<Node>();
		
		for(int i = 0 ; i < numInteriors; i++)
			addInterior(interiors, fi);
		
		root = interiors.get(0);
		
	}

}
