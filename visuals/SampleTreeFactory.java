package visuals;

import java.util.ArrayList;
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
		Stack<TextInBox> textBoxStack = new Stack<TextInBox>();
		
		nodeStack.push(tree.root);
		while (!nodeStack.empty()) {
			Node node = nodeStack.pop();
			textBoxStack.add(new TextInBox(node));
			if (node.isParent()) {
				ArrayList<Node> children = node.getChildren();
				for (Node n : children) {
					nodeStack.add(n);
					textBoxStack.add(new TextInBox(n));
				}
			}
		}
		
		
		DefaultTreeForTreeLayout<TextInBox> treeLayout = new DefaultTreeForTreeLayout<TextInBox>(new TextInBox(tree.root));
		nodeStack.push(tree.root);
		
		while (!nodeStack.empty()) {
			Node node = nodeStack.pop();
			TextInBox txtBox1 = textBoxStack.pop();

			if (node.isParent()) {
				ArrayList<Node> children = node.getChildren();
				for (Node n : children) {
					nodeStack.add(n);
					TextInBox txtBox2 = textBoxStack.pop();
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