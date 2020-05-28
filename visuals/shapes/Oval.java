package visuals.shapes;

import java.awt.Graphics2D;

import agents.BT.BT.Node;
import visuals.TextInBox;

public class Oval extends BTShape {
	
	public Oval(int x, int y) {
		super(x,y);
	}

	@Override
	public void paint(Graphics2D g, Node box) {
		
		g.setColor(BOX_COLOR);
		g.fillOval(x, y, (int) box.width - 1, (int) box.height - 1);
		
		g.setColor(BORDER_COLOR);
		g.drawOval(x, y, (int) box.width - 1, (int) box.height - 1);
		
		paintText(g, box);
	}

}
