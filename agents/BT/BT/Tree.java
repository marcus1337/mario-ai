package agents.BT.BT;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Tree {

	public Node root;
	NodeMap nodemap;
	public Actions actions;
	//public Conditions conditions;
	public ConditionsDetailed conditions;
	public Blackboard blackboard;
	
	private void init(){
		nodemap = new NodeMap();
		blackboard = new Blackboard();
		actions = new Actions(blackboard);
		conditions = new ConditionsDetailed(blackboard);
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

		ArrayList<Node> interiors = new ArrayList<Node>();
		Scanner fi = new Scanner(treeString);
		int numInteriors = fi.nextInt();		
		for(int i = 0 ; i < numInteriors; i++)
			addInterior(interiors, fi);
		
		root = interiors.get(0);
		addNodesToTree(interiors);
	}
	

	void addNodesToTree(ArrayList<Node> interiors){
		Stack<Node> nodeStack = new Stack<Node>();
		nodeStack.push(root);
		int interiorIndex = 0;
		while(!nodeStack.empty()){
			addNodeToTree(interiorIndex, interiors, nodeStack);
			interiorIndex++;
		}
	}
	
	void addNodeToTree(int interiorIndex, ArrayList<Node> interiors, Stack<Node> nodeStack){
		Interior node = (Interior) nodeStack.pop();
		if(interiorIndex > 0){
			node.copy((Interior) interiors.get(interiorIndex));
		}
		
	    for (Node n : node.children)
	        if (n.isParent())
	            nodeStack.push(n);
	}

}
