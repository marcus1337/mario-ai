package visuals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

public class TextInBoxTreePane extends JComponent {
	private final TreeLayout<TextInBox> treeLayout;

	private TreeForTreeLayout<TextInBox> getTree() {
		return treeLayout.getTree();
	}

	private Iterable<TextInBox> getChildren(TextInBox parent) {
		return getTree().getChildren(parent);
	}

	private Rectangle2D.Double getBoundsOfNode(TextInBox node) {
		return treeLayout.getNodeBounds().get(node);
	}

	public TextInBoxTreePane(TreeLayout<TextInBox> treeLayout) {
		this.treeLayout = treeLayout;
		Dimension size = treeLayout.getBounds().getBounds().getSize();
		setPreferredSize(size);
	}

	private final int ARC_SIZE = 10;
	private final Color BOX_COLOR = Color.orange;
	private final Color BORDER_COLOR = Color.darkGray;
	private final Color TEXT_COLOR = Color.black;

	private void paintEdges(Graphics2D g, TextInBox parent) {
		if (!getTree().isLeaf(parent)) {
			Rectangle2D.Double b1 = getBoundsOfNode(parent);
			double x1 = b1.getCenterX();
			double y1 = b1.getCenterY();
			for (TextInBox child : getChildren(parent)) {
				Rectangle2D.Double b2 = getBoundsOfNode(child);
				g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(), (int) b2.getCenterY() - (int) (b2.height/2));

				paintEdges(g, child);
			}
		}
	}

	private class Diamond extends Path2D.Double {

		public Diamond() {
		}
		public Diamond(int x, int y) {
			setXY(x,y);
		}

		private int x, y;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void setXY(int x, int y) {
			this.x = x;
			this.y = y;
		}

		private void setPath(double width, double height) {
			moveTo(x, y + height / 2);
			lineTo(x + width / 2, y);
			lineTo(x + width, y + height / 2);
			lineTo(x + width / 2, y + height);
			closePath();
		}

		public void paint(Graphics2D g, TextInBox box) {
			setPath((double) box.width, (double) box.height);
			g.setColor(BOX_COLOR);
			g.fill(this);
			g.setColor(BORDER_COLOR);
			g.draw(this);
		}

	}

	private void paintBox(Graphics2D g, TextInBox textInBox) {

		Rectangle2D.Double box = getBoundsOfNode(textInBox);

		paintBoxBackground(g, box);
		paintBoxMargin(g, box);

		new Diamond((int)box.x,(int)box.y).paint(g, textInBox);

		paintBoxText(g, textInBox, box);
	}

	private void paintBoxBackground(Graphics2D g, Rectangle2D.Double box) {
		g.setColor(BOX_COLOR);
		g.fillRoundRect((int) box.x, (int) box.y, (int) box.width - 1, (int) box.height - 1, ARC_SIZE, ARC_SIZE);
	}

	private void paintBoxMargin(Graphics2D g, Rectangle2D.Double box) {
		g.setColor(BORDER_COLOR);
		g.drawRoundRect((int) box.x, (int) box.y, (int) box.width - 1, (int) box.height - 1, ARC_SIZE, ARC_SIZE);
	}

	private void paintBoxText(Graphics2D g, TextInBox textInBox, Rectangle2D.Double box) {
		g.setColor(TEXT_COLOR);
		String[] lines = textInBox.text.split("\n");
		FontMetrics m = getFontMetrics(getFont());
		int x = (int) box.x + ARC_SIZE / 2;
		int y = (int) box.y + m.getAscent() + m.getLeading() + 1;
		for (int i = 0; i < lines.length; i++) {
			g.drawString(lines[i], x, y);
			y += m.getHeight();
		}
	}

	void paintSpecializedEdges(Graphics2D g2) {
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(3));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintEdges(g2, getTree().getRoot());
		g2.setStroke(oldStroke);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;

		paintSpecializedEdges(g2);
		for (TextInBox textInBox : treeLayout.getNodeBounds().keySet())
			paintBox(g2, textInBox);

	}
}