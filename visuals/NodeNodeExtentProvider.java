package visuals;
import org.abego.treelayout.NodeExtentProvider;

import agents.BT.BT.Node;

public class NodeNodeExtentProvider implements NodeExtentProvider<Node> {

	@Override
	public double getHeight(Node nod) {
		return nod.width;
	}

	@Override
	public double getWidth(Node nod) {
		return nod.height;
	}

}
