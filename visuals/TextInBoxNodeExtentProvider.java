package visuals;

import org.abego.treelayout.NodeExtentProvider;

public class TextInBoxNodeExtentProvider implements NodeExtentProvider<TextInBox> {

	@Override
	public double getWidth(TextInBox treeNode) {
		return treeNode.width;
	}

	@Override
	public double getHeight(TextInBox treeNode) {
		return treeNode.height;
	}
}