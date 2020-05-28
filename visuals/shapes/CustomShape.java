package visuals.shapes;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import agents.BT.BT.Node;
import visuals.TextInBox;

public abstract class CustomShape extends BTShape {

	public CustomShape(int x, int y) {
		super(x, y);
	}

	@Override
	protected abstract void setPath(double width, double height);

	protected abstract void customPaint(Graphics2D g, Node box);

	@Override
	public void paint(Graphics2D g, Node box) {
		setPath((double) box.width, (double) box.height);
		customPaint(g, box);
		paintText(g, box);
	}

}
