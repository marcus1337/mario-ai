package visuals;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;

import agents.BT.BT.Node;
import agents.BT.BT.Tree;

public class TreeVisualizer {

	private static void showInDialog(JComponent panel) {
		JDialog dialog = new JDialog();
		Container contentPane = dialog.getContentPane();
		((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPane.add(panel);

		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	private static TreeForTreeLayout<TextInBox> getGraphicsTree() {
		TreeForTreeLayout<TextInBox> tree;
		tree = SampleTreeFactory.createSampleTree();
		return tree;
	}
	
	private static TreeForTreeLayout<Node> getRealGraphicsTree(Tree tree) {
		TreeForTreeLayout<Node> treeLay;
		treeLay = SampleTreeFactory.createSampleTree(tree);
		return treeLay;
	}
	
	public static void visualizeBT(Tree tree){
		TreeForTreeLayout<Node> treeForTreeLayout = getRealGraphicsTree(tree);
		System.out.println("working...");
		double gapBetweenLevels = 50;
		double gapBetweenNodes = 10;
		DefaultConfiguration<Node> configuration = new DefaultConfiguration<Node>(gapBetweenLevels,
				gapBetweenNodes);
		NodeNodeExtentProvider nodeExtentProvider = new NodeNodeExtentProvider();
		TreeLayout<Node> treeLayout = new TreeLayout<Node>(treeForTreeLayout, nodeExtentProvider, configuration);
		TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
		showInDialog(panel);
	}

	public static void main(String[] args) {

		/*TreeForTreeLayout<TextInBox> tree = getGraphicsTree();

		double gapBetweenLevels = 50;
		double gapBetweenNodes = 10;
		DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(gapBetweenLevels,
				gapBetweenNodes);
		TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
		TreeLayout<Node> treeLayout = new TreeLayout<Node>(tree, nodeExtentProvider, configuration);

		TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
		showInDialog(panel);*/
	}

}
