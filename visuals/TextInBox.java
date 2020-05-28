package visuals;

import agents.BT.BT.Node;

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
}