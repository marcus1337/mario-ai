package visuals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;

import javax.swing.JComponent;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

import agents.BT.BT.Node;
import visuals.shapes.BTRectangle;
import visuals.shapes.BTShape;
import visuals.shapes.Diamond;
import visuals.shapes.Oval;
import visuals.shapes.Square;

public class TextInBoxTreePane extends JComponent {
	private final TreeLayout<Node> treeLayout;

	private TreeForTreeLayout<Node> getTree() {
		return treeLayout.getTree();
	}

	private Iterable<Node> getChildren(Node parent) {
		return getTree().getChildren(parent);
	}

	private Rectangle2D.Double getBoundsOfNode(Node node) {
		return treeLayout.getNodeBounds().get(node);
	}

	public TextInBoxTreePane(TreeLayout<Node> treeLayout) {
		this.treeLayout = treeLayout;
		Dimension size = treeLayout.getBounds().getBounds().getSize();
		setPreferredSize(size);
	}

	private final int ARC_SIZE = 10;
	private final Color BOX_COLOR = Color.orange;
	private final Color BORDER_COLOR = Color.darkGray;
	private final Color TEXT_COLOR = Color.black;

	private void paintEdges(Graphics2D g, Node parent) {
		if (!getTree().isLeaf(parent)) {
			Rectangle2D.Double b1 = getBoundsOfNode(parent);
			double x1 = b1.getCenterX();
			double y1 = b1.getCenterY();
			for (Node child : getChildren(parent)) {
				Rectangle2D.Double b2 = getBoundsOfNode(child);
				g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(), (int) b2.getCenterY() - (int) (b2.height/2));

				paintEdges(g, child);
			}
		}
	}

	public BTShape makeInteriorShape(Node textInBox){
		BTShape shape = null;
		Rectangle2D.Double bounds = getBoundsOfNode(textInBox);
		
		if(textInBox.text.equals("?")){
			shape = new Square((int)bounds.x,(int)bounds.y);
			shape.text = "?";
			shape.trig = symbolAttribFallback;
			shape.font = symbolFontFallback;
		}
		
		if(textInBox.text.equals("?*")){
			shape = new Square((int)bounds.x,(int)bounds.y);
			shape.text = "?*";
			shape.trig = symbolAttribFallback;
			shape.font = symbolFontFallback;
		}
		
		if(textInBox.text.equals("→")){
			shape = new Square((int)bounds.x,(int)bounds.y);
			shape.text = "→";
			shape.trig = symbolAttribSequence;
			shape.font = symbolFontSequence;
		}
		
		if(textInBox.text.equals("→*")){
			shape = new Square((int)bounds.x,(int)bounds.y);
			shape.text = "→*";
			shape.trig = symbolAttribSequence;
			shape.font = symbolFontSequence;
		}
		
		if(textInBox.text.equals("⇉")){
			shape = new Square((int)bounds.x,(int)bounds.y);
			shape.text = "⇉";
			shape.font = symbolFontParallel;
		}
		
		if(textInBox.text.equals("δ")){
			shape = new Diamond((int)bounds.x,(int)bounds.y);
			shape.text = "δ";
			shape.font = symbolFontDecorator;
		}
		
		if(textInBox.text.equals("Action")){
			shape = new BTRectangle((int)bounds.x,(int)bounds.y);
			shape.text = "Action";
			shape.font = symbolFontDecorator;
		}
		
		if(textInBox.text.equals("Condition")){
			shape = new Oval((int)bounds.x,(int)bounds.y);
			shape.text = "Condition";
			shape.font = symbolFontDecorator;
		}
		
		return shape;
	}
	
	private void paintBox(Graphics2D g, Node textInBox) {

		Rectangle2D.Double box = getBoundsOfNode(textInBox);

		paintBoxBackground(g, box);
		paintBoxMargin(g, box);
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

	private void paintBoxText(Graphics2D g, Node textInBox, Rectangle2D.Double box) {
		g.setColor(TEXT_COLOR);
		String[] lines = textInBox.text.split("\n");
		FontMetrics m = g.getFontMetrics(g.getFont());
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
	
	public static Font symbolFontDecorator = null;
	public static Font symbolFontParallel = null;
	public static Font symbolFontSequence = null;
	public static Font symbolFontFallback = null;
	
	public static AttributedString symbolAttribParallel = null;
	public static AttributedString symbolAttribSequence = null;
	public static AttributedString symbolAttribFallback = null;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		init(g);
		
		Graphics2D g2 = (Graphics2D) g;

		paintSpecializedEdges(g2);
		for (Node textInBox : treeLayout.getNodeBounds().keySet())
			paintBox(g2, textInBox);

	}

	public void init(Graphics g) {
		int symbolTextSize = ARC_SIZE + ARC_SIZE/2;
		symbolFontDecorator = new Font("δ", Font.BOLD, symbolTextSize);
		symbolFontParallel = new Font("⇉", Font.BOLD, symbolTextSize);
		symbolFontSequence = new Font("→", Font.BOLD, symbolTextSize);
		symbolFontFallback = new Font("?", Font.BOLD, symbolTextSize);
		g.setFont(new Font("default", Font.ROMAN_BASELINE, ARC_SIZE+ARC_SIZE/5));
		
        symbolAttribParallel = getAttributedString("⇉*");
        symbolAttribSequence = getAttributedString("→*");
        symbolAttribFallback = getAttributedString("?*");
	}

	public AttributedString getAttributedString(String txt) {
		AttributedString trig = new AttributedString(txt);
        trig.addAttribute(TextAttribute.FAMILY, symbolFontParallel.getFamily());
        trig.addAttribute(TextAttribute.SIZE, symbolFontParallel.getSize());
        trig.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, 1, 2);
		return trig;
	}
}