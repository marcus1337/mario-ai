package visuals;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

/**
 * Creates "Sample" trees, e.g. to be used in demonstrations.
 * 
 * @author Udo Borkowski (ub@abego.org)
 */
public class SampleTreeFactory {

	public static TreeForTreeLayout<TextInBox> createSampleTree() {
		TextInBox root = new TextInBox("root", 40, 20);
		TextInBox n1 = new TextInBox("n1", 30, 20);
		TextInBox n1_1 = new TextInBox("n1.1\n(first node)", 80, 36);
		TextInBox n1_2 = new TextInBox("n1.2", 40, 20);
		TextInBox n1_3 = new TextInBox("n1.3\n(last node)", 80, 36);
		TextInBox n2 = new TextInBox("n2", 30, 20);
		TextInBox n2_1 = new TextInBox("n2", 30, 20);

		DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<TextInBox>(
				root);
		tree.addChild(root, n1);
		tree.addChild(n1, n1_1);
		tree.addChild(n1, n1_2);
		tree.addChild(n1, n1_3);
		tree.addChild(root, n2);
		tree.addChild(n2, n2_1);
		return tree;
	}
	
	public static TreeForTreeLayout<TextInBox> createSampleTree2() {
		TextInBox root = new TextInBox("prog", 40, 20);
		TextInBox n1 = new TextInBox("classDef", 65, 20);
		TextInBox n1_1 = new TextInBox("class", 50, 20);
		TextInBox n1_2 = new TextInBox("T", 20, 20);
		TextInBox n1_3 = new TextInBox("{", 20, 20);
		TextInBox n1_4 = new TextInBox("member", 60, 20);
		TextInBox n1_5 = new TextInBox("member", 60, 20);
		TextInBox n1_5_1 = new TextInBox("<ERROR:int>", 90, 20);
		TextInBox n1_6 = new TextInBox("member", 60, 20);
		TextInBox n1_6_1 = new TextInBox("int", 30, 20);
		TextInBox n1_6_2 = new TextInBox("i", 20, 20);
		TextInBox n1_6_3 = new TextInBox(";", 20, 20);
		TextInBox n1_7 = new TextInBox("}", 20, 20);
		         
		         
		DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<TextInBox>(root);
		tree.addChild(root, n1);
		tree.addChild(n1, n1_1);
		tree.addChild(n1, n1_2);
		tree.addChild(n1, n1_3);
		tree.addChild(n1, n1_4);
		tree.addChild(n1, n1_5);
		tree.addChild(n1_5, n1_5_1);
		tree.addChild(n1, n1_6);
		tree.addChild(n1_6,n1_6_1);
		tree.addChild(n1_6,n1_6_2);
		tree.addChild(n1_6,n1_6_3);
		tree.addChild(n1, n1_7);
		return tree;
	}
}