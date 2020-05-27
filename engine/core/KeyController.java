package engine.core;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.core.MarioGame;

public class KeyController implements KeyListener {
	
	public MarioGame marioGame = null;


	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_P){
			System.out.println("PAUSING");
			marioGame.pause = !marioGame.pause;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	
	
}
