package visuals.shapes;

import java.awt.Graphics2D;

import visuals.TextInBox;

public class BTRectangle extends BTShape {
	
	public BTRectangle(int x, int y) {
		super(x,y);
	}

	@Override
	public void paint(Graphics2D g, TextInBox box) {
	    paintBoxBackground(g, box);
		paintBoxMargin(g, box);
		paintText(g, box);
	}

}
