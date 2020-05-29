package visuals.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import agents.BT.BT.Node;
import visuals.TextInBox;

public class BTRectangle extends BTShape {
	
	public BTRectangle(int x, int y) {
		super(x,y);
	}

	@Override
	public void paint(Graphics2D g, Node box) {
		Color myRed = new Color(255, 176, 176);
	    paintBoxBackground(g, box, myRed);
		paintBoxMargin(g, box);
		paintText(g, box);
	}

}
