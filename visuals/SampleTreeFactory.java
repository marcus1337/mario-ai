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

	public static TreeForTreeLayout<Node> createSampleTree(Tree tree) {

		Stack<Node> nodeStack = new Stack<Node>();
		Queue<TextInBox> textBoxStack = new LinkedList<TextInBox>();
		int counter = 0;
		int numParents = 0;
		
		nodeStack.push(tree.root);
		//textBoxStack.add(new TextInBox(tree.root));
		while (!nodeStack.empty()) {
			Node node = nodeStack.pop();
			counter++;
			if (node.isParent()) {
				numParents++;
				System.out.println("A1 " + node.getDetailedType());
				 //intermediate nodes are added twice == error
				textBoxStack.add(new TextInBox(node));
				ArrayList<Node> children = node.getChildren();
				for (Node n : children) {
					nodeStack.add(n);
					textBoxStack.add(new TextInBox(n));
					System.out.println("B1 " + n.isParent() + " _ " + n.isParent() + " _ " + n.getDetailedType());
				}
			}
		}
		System.out.println("TOTAL: " + counter + " parents: " + numParents);
		System.out.println("\n");
		
		TextInBox rootBox = textBoxStack.peek();
		DefaultTreeForTreeLayout<Node> treeLayout = new DefaultTreeForTreeLayout<Node>(rootBox.node);
		
		
		while (!textBoxStack.isEmpty()) {
			TextInBox txtBox1 = textBoxStack.poll();
			if (txtBox1.node.isParent()) {
				System.out.println("A " + txtBox1.node.getDetailedType());
				ArrayList<Node> children = txtBox1.node.getChildren();
				for (Node n : children) {
					TextInBox txtBox2 = textBoxStack.poll();
					System.out.println("B " + n.isParent() + " _ " + txtBox2.node.isParent() + " _ " + n.getDetailedType());
					treeLayout.addChild(txtBox1.node, txtBox2.node);
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