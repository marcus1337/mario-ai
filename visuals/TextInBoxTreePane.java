package visuals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;

import javax.swing.JComponent;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

import agents.BT.BT.Node;
import agents.BT.BT.STATE;
import visuals.shapes.BTRectangle;
import visuals.shapes.BTShape;
import visuals.shapes.Diamond;
import visuals.shapes.Oval;
import visuals.shapes.Square;

public class TextInBoxTreePane extends JComponent {
	
	private static final long serialVersionUID = 136443252L;
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

	private void paintEdges(Graphics2D g, Node parent) {
		if (!getTree().isLeaf(parent)) {
			Rectangle2D.Double b1 = getBoundsOfNode(parent);
			double x1 = b1.getCenterX();
			double y1 = b1.getCenterY();
			for (Node child : getChildren(parent)) {
				Rectangle2D.Double b2 = getBoundsOfNode(child);
				decideEdgeColor(g, child);
				g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(), (int) b2.getCenterY() - (int) (b2.height/2));
				paintEdges(g, child);
			}
		}
	}

	public void decideEdgeColor(Graphics2D g, Node child) {
		g.setColor(Color.LIGHT_GRAY);
		if(child.lastReturnedStatus == STATE.FAILURE)
			g.setColor(Color.RED);
		if(child.lastReturnedStatus == STATE.SUCCESS)
			g.setColor(Color.GREEN);
		if(child.lastReturnedStatus == STATE.RUNNING)
			g.setColor(Color.YELLOW);
	}

	public BTShape makeInteriorShape(Node node){
		BTShape shape = null;
		Rectangle2D.Double bounds = getBoundsOfNode(node);
		
		if(node.text.equals("?")){
			shape = new Square((int)bounds.x,(int)bounds.y);
			shape.text = "?";
			shape.trig = symbolAttribFallback;
			shape.font = symbolFontFallback;
		}
		
		if(node.text.equals("?*")){
			shape = new Square((int)bounds.x,(int)bounds.y);
			shape.text = "?*";
			shape.trig = symbolAttribFallback;
			shape.font = symbolFontFallback;
		}
		
		if(node.text.equals("→")){
			shape = new Square((int)bounds.x,(int)bounds.y);
			shape.text = "→";
			shape.trig = symbolAttribSequence;
			shape.font = symbolFontSequence;
		}
		
		if(node.text.equals("→*")){
			shape = new Square((int)bounds.x,(int)bounds.y);
			shape.text = "→*";
			shape.trig = symbolAttribSequence;
			shape.font = symbolFontSequence;
		}
		
		if(node.text.equals("⇉")){
			shape = new Square((int)bounds.x,(int)bounds.y);
			shape.text = "⇉";
			shape.font = symbolFontParallel;
		}
		
		if(node.text.equals("δ")){
			shape = new Diamond((int)bounds.x,(int)bounds.y);
			shape.text = "δ";
			shape.font = symbolFontDecorator;
		}
		
		if(node.text.equals("Action")){
			shape = new BTRectangle((int)bounds.x,(int)bounds.y);
			shape.text = node.description;
			shape.font = symbolFontDecorator;
		}
		
		if(node.text.equals("Condition")){
			shape = new Oval((int)bounds.x,(int)bounds.y);
			shape.text = node.description;
			shape.font = symbolFontDecorator;
		}
		
		return shape;
	}
	
	private void paintBox(Graphics2D g, Node node) {

		BTShape btShape = makeInteriorShape(node);
		btShape.paint(g, node);
		
	}

	void paintSpecializedEdges(Graphics2D g2) {
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.LIGHT_GRAY);
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
		int symbolTextSize = 20;
		symbolFontDecorator = new Font("δ", Font.PLAIN, symbolTextSize);
		symbolFontParallel = new Font("⇉", Font.BOLD, symbolTextSize);
		symbolFontSequence = new Font("→", Font.BOLD, symbolTextSize);
		symbolFontFallback = new Font("?", Font.ROMAN_BASELINE, symbolTextSize);
		g.setFont(new Font("default", Font.ROMAN_BASELINE, symbolTextSize));
		
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