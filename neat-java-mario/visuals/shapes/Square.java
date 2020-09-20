package visuals.shapes;

import java.awt.Color;
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
		Color myGray = new Color(224,224,224);
	    paintBoxBackground(g, box, myGray);
		paintBoxMargin(g, box);
		paintText(g, box);
	}

}
