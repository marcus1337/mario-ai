package visuals;

import agents.BT.BT.Node;
import agents.BT.BT.NodeTypeDetailed;
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
			this.width = this.height = 35;
		}else{
			this.width = 70;
			this.height = 50;
		}
		if(node.getDetailedType() == NodeTypeDetailed.SEQUENCE){
			text = "→";
		}
		if(node.getDetailedType() == NodeTypeDetailed.SEQUENCE_MEMORY){
			text = "→*";
		}
		if(node.getDetailedType() == NodeTypeDetailed.FALLBACK){
			text = "?";
		}
		if(node.getDetailedType() == NodeTypeDetailed.FALLBACK_MEMORY){
			text = "?*";
		}
		if(node.getDetailedType() == NodeTypeDetailed.PARALLEL){
			text = "⇉";
		}
		if(node.getDetailedType() == NodeTypeDetailed.DECORATOR){
			text = "δ";
		}
		if(node.getDetailedType() == NodeTypeDetailed.ACTION){
			text = "Action";
		}
		if(node.getDetailedType() == NodeTypeDetailed.CONDITION){
			text = "Condition";
		}
	}
	
}