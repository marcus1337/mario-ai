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
			System.out.println("PAUSE");
			marioGame.pause = !marioGame.pause;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.out.println("EXIT");
			System.exit(0); 
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	
	
}
