package visuals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import agents.BT.BT.Interior;
import agents.BT.BT.Node;
import agents.BT.BT.Tree;

public class SampleTreeFactory {

	public static int interiorWidth = 20;

	public static TreeForTreeLayout<TextInBox> createSampleTree(Tree tree) {

		Stack<Node> nodeStack = new Stack<Node>();
		Queue<TextInBox> textBoxStack = new LinkedList<TextInBox>();
		int counter = 0;
		int numParents = 0;
		
		nodeStack.push(tree.root);
		while (!nodeStack.empty()) {
			Node node = nodeStack.pop();
			counter++;
			if (node.isParent()) {
				numParents++;
				textBoxStack.add(new TextInBox(node));
				ArrayList<Node> children = node.getChildren();
				for (Node n : children) {
					nodeStack.add(n);
					textBoxStack.add(new TextInBox(n));
				}
			}
		}
		System.out.println("TOTAL: " + counter + " parents: " + numParents);
		
		
		TextInBox rootBox = textBoxStack.peek();
		DefaultTreeForTreeLayout<TextInBox> treeLayout = new DefaultTreeForTreeLayout<TextInBox>(rootBox);
		
		
		while (!textBoxStack.isEmpty()) {
			TextInBox txtBox1 = textBoxStack.poll();
			System.out.println("BEFORE A");
			if (txtBox1.node.isParent()) {
				System.out.println("TEST A");
				ArrayList<Node> children = txtBox1.node.getChildren();
				for (Node n : children) {
					TextInBox txtBox2 = textBoxStack.poll();
					treeLayout.addChild(txtBox1, txtBox2);
				}
			}
		}
		

		return treeLayout;
	}

	public static TreeForTreeLayout<TextInBox> createSampleTree() {
		TextInBox root = new TextInBox("root", 40, 20);
		TextInBox n1 = new TextInBox("n1", 30, 20);
		TextInBox n1_1 = new TextInBox("n1.1\n(first node)", 80, 36);
		TextInBox n1_2 = new TextInBox("n1.2", 40, 20);
		TextInBox n1_3 = new TextInBox("n1.3\n(last node)", 80, 36);
		TextInBox n2 = new TextInBox("n2", 30, 20);
		TextInBox n2_1 = new TextInBox("n2", 30, 20);

		DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<TextInBox>(root);
		tree.addChild(root, n1);
		tree.addChild(n1, n1_1);
		tree.addChild(n1, n1_2);
		tree.addChild(n1, n1_3);
		tree.addChild(root, n2);
		tree.addChild(n2, n2_1);
		return tree;
	}

}