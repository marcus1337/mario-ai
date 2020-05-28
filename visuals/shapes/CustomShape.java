package visuals.shapes;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import visuals.TextInBox;

public abstract class CustomShape extends Shape {
	
	public CustomShape(int x, int y) {
		super(x,y);
	}

	@Override
	protected abstract void setPath(double width, double height);
	
	protected abstract void customPaint(Graphics2D g, TextInBox box);

	@Override
	public void paint(Graphics2D g, TextInBox box) {
		setPath((double) box.width, (double) box.height);
		customPaint(g, box);
	}


}
