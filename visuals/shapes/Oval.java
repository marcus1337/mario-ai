package visuals.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import agents.BT.BT.Node;
import visuals.TextInBox;

public class Oval extends BTShape {

	public Oval(int x, int y) {
		super(x, y);
	}

	@Override
	public void paint(Graphics2D g, Node box) {

		Color myYellow = new Color(255, 153, 255);
		g.setColor(myYellow);
		g.fillOval(x, y, (int) box.width - 1, (int) box.height - 1);

		decideEdgeColor(g, box);
		g.drawOval(x, y, (int) box.width - 1, (int) box.height - 1);

		paintText(g, box);
	}

}
