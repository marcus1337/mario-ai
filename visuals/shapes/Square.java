package visuals.shapes;

import java.awt.Graphics2D;

import agents.BT.BT.Node;
import visuals.TextInBox;

public class Square extends BTShape {

	public Square(int x, int y) {
		super(x,y);
	}

	@Override
	public void paint(Graphics2D g, Node box) {
		box.height = box.width;
	    paintBoxBackground(g, box);
		paintBoxMargin(g, box);
		paintText(g, box);
	}

}
