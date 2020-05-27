package engine.core;

import javax.swing.*;

import engine.helper.Assets;
import engine.helper.MarioActions;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class MarioRender extends JComponent implements FocusListener {
    private static final long serialVersionUID = 790878775993203817L;
    public static final int TICKS_PER_SECOND = 24;

    private float scale;
    private GraphicsConfiguration graphicsConfiguration;

    int frame;
    Thread animator;
    boolean focused;
    
    private static final int WIDTH = 256*2; //256
    private static final int HEIGHT = 240; //240

    public MarioRender(float scale) {
        this.setFocusable(true);
        this.setEnabled(true);
        this.scale = scale;

        Dimension size = new Dimension((int) (WIDTH * scale), (int) (HEIGHT * scale));

        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        setFocusable(true);
    }

    public void init() {
        graphicsConfiguration = getGraphicsConfiguration();
        Assets.init(graphicsConfiguration);
    }

    public void renderWorld(MarioWorld world, Image image, Graphics g, Graphics og) {
        og.fillRect(0, 0, WIDTH, HEIGHT);
        world.render(og);
        drawStringDropShadow(og, "Coins: " + world.coins, 0, 2, 4);
        drawStringDropShadow(og, "Time: " + (world.currentTimer == -1 ? "Inf" : (int) Math.ceil(world.currentTimer / 1000f)), 0, 0, 4);
        if (MarioGame.verbose) {
            String pressedButtons = "";
            for (int i = 0; i < world.mario.actions.length; i++) {
                if (world.mario.actions[i]) {
                    pressedButtons += MarioActions.getAction(i).getString() + " ";
                }
            }
            drawStringDropShadow(og, "Buttons: " + pressedButtons, 0, 4, 4);
        }
        if (scale > 1) {
            g.drawImage(image, 0, 0, (int) (WIDTH * scale), (int) (HEIGHT * scale), null);
        } else {
            g.drawImage(image, 0, 0, null);
        }
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