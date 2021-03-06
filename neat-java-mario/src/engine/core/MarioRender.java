package engine.core;

import javax.swing.*;

import _GA_TESTS.ReceptiveField;
import engine.helper.Assets;
import engine.helper.MarioActions;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.AffineTransform;

public class MarioRender extends JComponent implements FocusListener {
	private static final long serialVersionUID = 790878775993203817L;
	public static final int TICKS_PER_SECOND = 24;

	private float scale;
	private GraphicsConfiguration graphicsConfiguration;

	int frame;
	Thread animator;
	boolean focused;
	public MarioForwardModel model = null;

	private static final int ORIGIN_WIDTH = 400; // 256
	private static final int ORIGIN_HEIGHT = 256; // 240
	private static final int WIDTH = 480; // 256
	private static final int HEIGHT = 270; // 240
	
	public boolean[] takenAIActions = new boolean[5];

	public MarioRender(float scale) {
		this.setFocusable(true);
		this.setEnabled(true);
		this.scale = scale;

		//Dimension size = new Dimension(1280, 580); //720
		Dimension size = new Dimension(1280, 720);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		setFocusable(true);
	}

	private int treeWidth;
	private int treeHeight;

	public void init(MarioAgent agent) {
		graphicsConfiguration = getGraphicsConfiguration();
		Assets.init(graphicsConfiguration);

	}

	MarioWorld world = null;
	
	public void renderActionRectangle(int x, int y, Graphics og, boolean active){
		og.setColor(Color.BLACK);
		og.fillRect(x-2, y-2, 19, 19);
		if(active)
			og.setColor(Color.GREEN);
		else
			og.setColor(Color.RED);
		og.fillRect(x, y, 15, 15);
	}

	public void renderWorld(MarioWorld world, Image image, Graphics g, Graphics og) {
		this.world = world;
		renderBackGround(g, og);
		renderMarioWorld(world, og);
		
		renderAIActions(og);
		
		renderSeparatorBar(og);
		renderMarioImage(image, g);
		
	
	}

	private void renderAIActions(Graphics og) {
		for(int i = 0 ; i < 5; i++){
			renderActionRectangle(915+65*i,40,og, false);	
		}
		for(int i = 0 ; i < 5; i++){
			if(i == 0 && takenAIActions[MarioActions.LEFT.getValue()])
				renderActionRectangle(915+65*i,40,og, true);
			if(i == 1 && takenAIActions[MarioActions.RIGHT.getValue()])
				renderActionRectangle(915+65*i,40,og, true);	
			if(i == 2 && takenAIActions[MarioActions.JUMP.getValue()])
				renderActionRectangle(915+65*i,40,og, true);	
			if(i == 3 && takenAIActions[MarioActions.SPEED.getValue()])
				renderActionRectangle(915+65*i,40,og, true);	
			if(i == 4 && takenAIActions[MarioActions.DOWN.getValue()])
				renderActionRectangle(915+65*i,40,og, true);	
		}
	}

	public void renderSeparatorBar(Graphics og) {
		og.setColor(Color.black);
		og.fillRect(801, 0, 80, 900); // 881
	}

	public void renderMarioWorld(MarioWorld world, Graphics og) {
		Graphics2D g2 = (Graphics2D) og;
		AffineTransform oldAT = g2.getTransform();
		g2.translate(0, 70);
		g2.scale(2, 2);
		world.render(og);
		renderRectangles(og);

		g2.translate(0, -35);
		renderTextInfo(world, og);
		g2.setTransform(oldAT);
	}

	public void renderTextInfo(MarioWorld world, Graphics og) {
		drawStringDropShadow(og, "Coins: " + world.coins, 0, 2, 4);
		drawStringDropShadow(og,
				"Time: " + (world.currentTimer == -1 ? "Inf" : (int) Math.ceil(world.currentTimer / 1000f)), 0, 0, 4);
		
		
		drawStringDropShadow(og, "Left", 55, 0, 3);
		drawStringDropShadow(og, "Right", 59, 4, 5);
		drawStringDropShadow(og, "Jump", 63, 0, 2);
		drawStringDropShadow(og, "Speed", 67, 4, 1);
		drawStringDropShadow(og, "Duck", 71, 0, 0);
		/*if (MarioGame.verbose) {
			String pressedButtons = "";
			for (int i = 0; i < world.mario.actions.length; i++) {
				if (world.mario.actions[i]) {
					pressedButtons += MarioActions.getAction(i).getString() + " ";
				}
			}
			drawStringDropShadow(og, "Buttons: " + pressedButtons, 40, 1, 4);
		}*/
	}

	public void renderMarioImage(Image image, Graphics g) {
		int imgLenX = (int) (ORIGIN_WIDTH * scale * 2);
		int imgLenY = (int) (ORIGIN_HEIGHT * scale * 2);
		if (scale > 1) {
			g.drawImage(image, 0, 0, 1280, 720, null);
			//g.drawImage(image, 0, 0, 1280, 600, null);
			// g.drawImage(image, 0, 0, imgLenX, imgLenY, null);
		} else {
			g.drawImage(image, 0, 0, null);
		}
	}

	ReceptiveField receptiveField = null;
	int[][] enemyField = null;
	int[][] enemyNonJumpField = null;
	int[][] blockField = null;
	int[][] platformField = null;

	void renderReceptiveField(Graphics2D g, float topX, float topY, float brickLen) {

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5*2+1; j++) {
				float tmpX = topX + brickLen * j - brickLen*5;
				float tmpY = topY + brickLen * i;
				g.setColor(Color.gray);
				g.drawRect((int) tmpX, (int) tmpY + 1, (int) brickLen, (int) brickLen);
			}
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5*2+1; j++) {
				float tmpX = topX + brickLen * j - brickLen*5;
				float tmpY = topY + brickLen * i;
				if (blockField[j][i] != 0 || platformField[j][i] != 0) {
					int alpha = 90;
					Color myColour = new Color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(),
							alpha);
					g.setColor(myColour);
					g.fillRect((int) tmpX, (int) tmpY + 1, (int) brickLen, (int) brickLen);
					g.setColor(Color.GREEN);
					g.drawRect((int) tmpX, (int) tmpY + 1, (int) brickLen, (int) brickLen);
				}
			}
		}

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5*2+1; j++) {
				float tmpX = topX + brickLen * j - brickLen*5;
				float tmpY = topY + brickLen * i;
				if (enemyField[j][i] != 0 || enemyNonJumpField[j][i] != 0) {
					int alpha = 90;
					Color myColour = new Color(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue(), alpha);
					g.setColor(myColour);
					g.fillRect((int) tmpX, (int) tmpY + 1, (int) brickLen, (int) brickLen);
					g.setColor(Color.RED);
					g.drawRect((int) tmpX, (int) tmpY + 1, (int) brickLen, (int) brickLen);
				}
			}
		}
		
		
		
		boolean floorMarks[] = receptiveField.getFloor(model);
		for(int i = 0 ; i < 11; i++){
			float tmpX = topX + brickLen * i - brickLen*5;
			float tmpY = topY + brickLen * 4;
			g.setColor(Color.DARK_GRAY);
			if(floorMarks[i]){
				g.drawRect((int) tmpX, (int) tmpY + 1, (int) brickLen, (int) brickLen);
				g.drawRect((int) tmpX, (int) tmpY + 2, (int) brickLen, (int) brickLen);
				g.drawRect((int) tmpX, (int) tmpY + 3, (int) brickLen, (int) brickLen);
			}
		}

	}

	void renderRectangles(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		float thickness = 2;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(thickness));

		float brLen = WIDTH / 30.0f;
		float brickStep = brLen / 2;
		float[] marioPos = model.getMarioFloatPos();
		marioPos[0] = world.marioSprite.x;
		marioPos[1] = world.marioSprite.y;
		if (world.marioSprite == null || world.marioSprite.world == null)
			return;

		marioPos[0] -= world.marioSprite.world.cameraX;
		marioPos[1] -= world.marioSprite.world.cameraY;
		float topX = marioPos[0] - brickStep;
		float topY = marioPos[1] - brLen * 4;
		receptiveField = new ReceptiveField();

		enemyField = receptiveField.getEnemyReceptiveFieldStompable(model);
		enemyNonJumpField = receptiveField.getEnemyReceptiveFieldNonStompable(model);
		platformField = receptiveField.getPlatformReceptiveField(model);
		blockField = receptiveField.getBlockReceptiveField(model);

		renderReceptiveField(g2, topX, topY, brLen);
		g2.setStroke(oldStroke);
	}

	boolean doneOnce = false;

	public void renderBackGround(Graphics g, Graphics og) {
		og.setColor(Color.DARK_GRAY);
		og.fillRect(0, 0, 1280, 720);
		if (!doneOnce) {
			g.setColor(Color.MAGENTA);
			g.fillRect(0, 0, WIDTH * 100, HEIGHT * 100);
		}
		doneOnce = true;
	}

	public void drawStringDropShadow(Graphics g, String text, int x, int y, int c) {
		drawString(g, text, x * 8 + 5, y * 8 + 5, 0);
		drawString(g, text, x * 8 + 4, y * 8 + 4, c);
	}

	private void drawString(Graphics g, String text, int x, int y, int c) {
		char[] ch = text.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			g.drawImage(Assets.font[ch[i] - 32][c], x + i * 8, y, null);
		}
	}

	public void focusGained(FocusEvent arg0) {
		focused = true;
	}

	public void focusLost(FocusEvent arg0) {
		focused = false;
	}
}