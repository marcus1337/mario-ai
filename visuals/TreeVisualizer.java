package visuals;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;

public class TreeVisualizer {
	
	private static void showInDialog(JComponent panel) {
		JDialog dialog = new JDialog();
		Container contentPane = dialog.getContentPane();
		((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(
				10, 10, 10, 10));
		contentPane.add(panel);
		
		
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	private static TreeForTreeLayout<TextInBox> getSampleTree(String treeName) {
		TreeForTreeLayout<TextInBox> tree;
		if (treeName.equals("2")) {
			tree = SampleTreeFactory.createSampleTree2();
		} else if (treeName.equals("")) {
			tree = SampleTreeFactory.createSampleTree();
		} else {
			throw new RuntimeException(String.format("Invalid tree name: '%s'",
					treeName));
		}
		return tree;
	}

	/**
	 * Shows a dialog with a tree in a layout created by {@link TreeLayout},
	 * using the Swing component {@link TextInBoxTreePane}.
	 * 
	 * @param args args[0]: treeName (default="")
	 */
	public static void main(String[] args) {
		
		String treeName = (args.length > 0) ? args[0] : "";
		TreeForTreeLayout<TextInBox> tree = getSampleTree(treeName);
				
		// setup the tree layout configuration
		double gapBetweenLevels = 50;
		double gapBetweenNodes = 10;
		DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(
				gapBetweenLevels, gapBetweenNodes);
		

		// create the NodeExtentProvider for TextInBox nodes
		TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();

		// create the layout
		TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tree,
				nodeExtentProvider, configuration);


		TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
	
		showInDialog(panel);
	}

}
