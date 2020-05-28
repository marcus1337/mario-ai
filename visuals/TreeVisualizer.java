package visuals;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;

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
	
	private static TreeForTreeLayout<TextInBox> getRealGraphicsTree(Tree tree) {
		TreeForTreeLayout<TextInBox> treeLay;
		treeLay = SampleTreeFactory.createSampleTree(tree);
		return treeLay;
	}
	
	public static void visualizeBT(Tree tree){
		TreeForTreeLayout<TextInBox> treeForTreeLayout = getRealGraphicsTree(tree);

		double gapBetweenLevels = 50;
		double gapBetweenNodes = 10;
		DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(gapBetweenLevels,
				gapBetweenNodes);
		TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
		TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(treeForTreeLayout, nodeExtentProvider, configuration);

		TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
		showInDialog(panel);
	}

	public static void main(String[] args) {

		TreeForTreeLayout<TextInBox> tree = getGraphicsTree();

		double gapBetweenLevels = 50;
		double gapBetweenNodes = 10;
		DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(gapBetweenLevels,
				gapBetweenNodes);
		TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
		TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tree, nodeExtentProvider, configuration);

		TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
		showInDialog(panel);
	}

}
