package visuals;

import agents.BT.BT.Node;
import agents.BT.BT.NodeTypes;

public class TextInBox {

	public String text;
	public int height;
	public int width;
	public Node node;

	public TextInBox(String text, int width, int height) {
		this.text = text;
		this.width = width;
		this.height = height;
	}
	
	public TextInBox(Node node) {
		this.node = node;
		
		if(node.isParent()){
			this.width = this.height = 20;
		}
		
	}
	
}