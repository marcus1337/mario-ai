package visuals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;
import agents.BT.BT.Node;
import agents.BT.BT.Tree;

public class SampleTreeFactory {

	public static TreeForTreeLayout<Node> createSampleTree(Tree tree) {
		Stack<Node> nodeStack = new Stack<Node>();
		Queue<TextInBox> textBoxStack = new LinkedList<TextInBox>();
		addNodesToQueue(tree, nodeStack, textBoxStack);
		TextInBox rootBox = textBoxStack.peek();
		DefaultTreeForTreeLayout<Node> treeLayout = new DefaultTreeForTreeLayout<Node>(rootBox.node);
		connectVisualGraphNodes(textBoxStack, treeLayout);
		return treeLayout;
	}

	public static void connectVisualGraphNodes(Queue<TextInBox> textBoxStack,
			DefaultTreeForTreeLayout<Node> treeLayout) {
		while (!textBoxStack.isEmpty()) {
			TextInBox txtBox1 = textBoxStack.poll();
			if (txtBox1.node.isParent()) {
				ArrayList<Node> children = txtBox1.node.getChildren();
				for (Node n : children) {
					TextInBox txtBox2 = textBoxStack.poll();
					treeLayout.addChild(txtBox1.node, txtBox2.node);
				}
			}
		}
	}

	public static void addNodesToQueue(Tree tree, Stack<Node> nodeStack, Queue<TextInBox> textBoxStack) {
		nodeStack.push(tree.root);
		while (!nodeStack.empty()) {
			Node node = nodeStack.pop();
			if (node.isParent()) {
				TextInBox tmpParentTxtBox = new TextInBox(node);
				node.width = tmpParentTxtBox.width;
				node.height = tmpParentTxtBox.height;
				node.text = tmpParentTxtBox.text;
				textBoxStack.add(tmpParentTxtBox);
				ArrayList<Node> children = node.getChildren();
				for (Node n : children) {
					nodeStack.add(n);
					TextInBox tmpTxtBox = new TextInBox(n);
					n.width = tmpTxtBox.width;
					n.height = tmpTxtBox.height;
					n.text = tmpTxtBox.text;
					textBoxStack.add(tmpTxtBox);
				}
			}
		}
	}


}