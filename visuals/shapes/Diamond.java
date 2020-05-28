package visuals.shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import agents.BT.BT.Node;
import visuals.TextInBox;

public class Diamond extends CustomShape {

	public Diamond(int x, int y) {
		super(x,y);
	}

	@Override
	protected void setPath(double width, double height) {
		moveTo(x, y + height / 2);
		lineTo(x + width / 2, y);
		lineTo(x + width, y + height / 2);
		lineTo(x + width / 2, y + height);
		closePath();
	}

	@Override
	protected void customPaint(Graphics2D g, Node box) {
		g.setColor(BOX_COLOR);
		g.fill(this);
		g.setColor(BORDER_COLOR);
		g.draw(this);
	}

}
