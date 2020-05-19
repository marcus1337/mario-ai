package agents.human;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;

public class Agent extends KeyAdapter implements MarioAgent {
    private boolean[] actions = null;

    @Override
    public void initialize(MarioForwardModel model, MarioTimer timer) {
        actions = new boolean[MarioActions.numberOfActions()];
    }
    
    private int[] getMarioBoundedPos(MarioForwardModel model){
		int[][] observ = model.getMarioSceneObservation();
		int maxXPos = observ.length;
		int maxYPos = observ[0].length;
		int[] pos = model.getMarioScreenTilePos();
		pos[0] = Math.max(0, pos[0]);
		pos[0] = Math.min(pos[0], maxXPos-1);
		pos[1] = Math.max(0, pos[1]);
		pos[1] = Math.min(pos[1], maxYPos-1);
		return pos;
    }
    
    /*int mapBoolToInt(boolean val){
    	if(val)
    		return 1;
    	return 0;
    }*/
    
    int[][] getReceptiveField(MarioForwardModel model){
    	int[][] field = new int[5][6];
    	
    	
    	return field;
    }

    @Override
    public boolean[] getActions(MarioForwardModel model, MarioTimer timer) { //obersvations middle: (16,8)

		int[][] observ = model.getMarioCompleteObservation(0, 0);
		int[] pos = getMarioBoundedPos(model);
		
		//System.out.println(model.isFacingRight());
		
		//System.out.println(observ[0][0] + " x:"+ pos[0] + " y:" + pos[1] + " _ "  + observ.length + "_"+observ[0].length);
    	//for(int i = 0 ; i < 16; i++){
    	//	System.out.println(" "+ i + ":" + mapBoolToInt((observ[0][i] == model.OBS_BRICK)));
    	//}
    	//System.out.println("-----------------------------------");
		
		//System.out.println("x: " + pos[0] + " y: " + pos[1] + "  obs: " + (observ[17][8]));
		
        return actions;
    }

    @Override
    public String getAgentName() {
        return "HumanAgent";
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    private void toggleKey(int keyCode, boolean isPressed) {
        if (this.actions == null) {
            return;
        }
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                this.actions[MarioActions.LEFT.getValue()] = isPressed;
                break;
            case KeyEvent.VK_RIGHT:
                this.actions[MarioActions.RIGHT.getValue()] = isPressed;
                break;
            case KeyEvent.VK_DOWN:
                this.actions[MarioActions.DOWN.getValue()] = isPressed;
                break;
            case KeyEvent.VK_S:
                this.actions[MarioActions.JUMP.getValue()] = isPressed;
                break;
            case KeyEvent.VK_A:
                this.actions[MarioActions.SPEED.getValue()] = isPressed;
                break;
        }
    }

}
