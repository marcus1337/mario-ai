package engine.core;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.image.VolatileImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import agents.human.Agent;
import engine.helper.GameStatus;
import engine.helper.MarioActions;

public class MarioGame {
    public static final long maxTime = 40;
    public static final long graceTime = 10;
   // public static final int width = 256*2;
    public static final int width = 400;
    public static final int height = 256;
    
    public static final int tileWidth = (width / 16);
    public static final int tileHeight = (height / 16);
    public static final boolean verbose = false;
    public boolean pause = false;
    private MarioEvent[] killEvents;

    public JFrame window = null;
    private MarioRender render = null;
    private MarioAgent agent = null;
    private MarioWorld world = null;

    public MarioGame() {}
    public MarioGame(boolean visuals) {  if(visuals) initRenderWindow2(2);}

    public MarioGame(MarioEvent[] killEvents) {
        this.killEvents = killEvents;
    }

    private int getDelay(int fps) {
        if (fps <= 0) {
            return 0;
        }
        return 1000 / fps;
    }

    private void setAgent(MarioAgent agent) {
        this.agent = agent;
        if (agent instanceof KeyAdapter) {
        	if(render != null)
        		this.render.addKeyListener((KeyAdapter) this.agent);
        }
    }

    public MarioResult playGame(String level, int timer) {
        return this.runGame(new Agent(), level, timer, 0, true, 30, 2);
    }
    public MarioResult playGame(String level, int timer, int marioState) {
        return this.runGame(new Agent(), level, timer, marioState, true, 30, 2);
    }
    public MarioResult playGame(String level, int timer, int marioState, int fps) {
        return this.runGame(new Agent(), level, timer, marioState, true, fps, 2);
    }
    public MarioResult playGame(String level, int timer, int marioState, int fps, float scale) {
        return this.runGame(new Agent(), level, timer, marioState, true, fps, scale);
    }
    public MarioResult runGame(MarioAgent agent, String level, int timer) {
        return this.runGame(agent, level, timer, 0, false, 0, 2);
    }
    public MarioResult runGame(MarioAgent agent, String level, int timer, int marioState) {
        return this.runGame(agent, level, timer, marioState, false, 0, 2);
    }
    public MarioResult runGame(MarioAgent agent, String level, int timer, int marioState, boolean visuals) {
        return this.runGame(agent, level, timer, marioState, visuals, visuals ? 30 : 0, 2);
    }

    public MarioResult runGame(MarioAgent agent, String level, int timer, int marioState, boolean visuals, int fps) {
        return this.runGame(agent, level, timer, marioState, visuals, fps, 2);
    }


    KeyController keyController = null;
    
    public MarioResult runGame(MarioAgent agent, String level, int timer, int marioState, boolean visuals, int fps, float scale) {
        if (visuals) {
            initRenderWindow(scale);
            
        }
        this.setAgent(agent);
        return gameLoop(level, timer, marioState, visuals, fps);
    }
    
	private void initRenderWindow2(float scale){
		window = new JFrame("Mario AI Framework");
		window.setUndecorated(true);
		render = new MarioRender(scale);

		window.setContentPane(render);
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		render.init(null);
		window.setVisible(true);
	}

	private void initRenderWindow(float scale) {
		
		window = new JFrame("Mario AI Framework");
		keyController = new KeyController();
		keyController.marioGame = this;
		
		window.setUndecorated(true);
		render = new MarioRender(scale);
		render.addKeyListener(keyController);
		
		window.setContentPane(render);
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		render.init(null);
		window.setVisible(true);
	}
    
    void delay(int fps){
        if (this.getDelay(fps) > 0) {
            try {
                currentTime += this.getDelay(fps);
                Thread.sleep(Math.max(0, currentTime - System.currentTimeMillis()));
            } catch (InterruptedException e) {
               
            }
        }
    }
    
    long currentTime;
    VolatileImage renderTarget = null;
    
    Graphics backBuffer = null;
    Graphics currentBuffer = null;
    
    ArrayList<MarioEvent> gameEvents = new ArrayList<>();
    ArrayList<MarioAgentEvent> agentEvents = new ArrayList<>();
    MarioTimer agentTimer;
    
    
    /*public void initGame(String level, int timer, int marioState, boolean visual){
    	prevMarioState = marioState;
        world = new MarioWorld(killEvents);
        world.visuals = visual;
        world.initializeLevel(level, 1000 * timer);
        if (visual) {
            world.initializeVisuals(render.getGraphicsConfiguration());
        }
        world.mario.isLarge = marioState > 0;
        world.mario.isFire = marioState > 1;
        world.update(new boolean[MarioActions.numberOfActions()]);
        currentTime = System.currentTimeMillis();

        if (visual) {
            renderTarget = render.createVolatileImage(1280, 720);
            
            backBuffer = render.getGraphics();
            currentBuffer = renderTarget.getGraphics();
            
            render.addFocusListener(render);
        }
        gameEvents = new ArrayList<>();
        agentEvents = new ArrayList<>();
        agentTimer = new MarioTimer(MarioGame.maxTime);
        agent.initialize(new MarioForwardModel(this.world.clone()), agentTimer);
    }*/
    
    
    public void initGame(String level, int timer, int marioState, boolean visual){
    	prevMarioState = marioState;
        world = new MarioWorld(killEvents);
        world.visuals = visual;
        world.initializeLevel(level, 1000 * timer);
        if (visual) {
            world.initializeVisuals(render.getGraphicsConfiguration());
        }
        world.mario.isLarge = marioState > 0;
        world.mario.isFire = marioState > 1;
        world.update(new boolean[MarioActions.numberOfActions()]);
        currentTime = System.currentTimeMillis();

        if (visual) {
            renderTarget = render.createVolatileImage(1280, 720);
            
            backBuffer = render.getGraphics();
            currentBuffer = renderTarget.getGraphics();
            
            render.addFocusListener(render);
        }
        gameEvents = new ArrayList<>();
        agentEvents = new ArrayList<>();
        agentTimer = new MarioTimer(MarioGame.maxTime);
        if(agent != null)
        	agent.initialize(new MarioForwardModel(this.world.clone()), agentTimer);
        updateWorld(new boolean[5]);
        previousXLocation = ((int) new MarioForwardModel(world.clone()).getMarioFloatPos()[0]) / 16;
    }
    
    
    void renderWorld(MarioForwardModel model){
        if (world.visuals) {
        	
        	render.model = model;
        	render.world = world;
        	
            render.renderWorld(world, renderTarget, backBuffer, currentBuffer);

            //System.out.println("MARIO: " + (int)(world.mario.x%16) + ", " + (int)(world.mario.y%16));
        }
    }
    
    
    void updateWorld(boolean[] actions){
        this.world.update(actions);
        gameEvents.addAll(this.world.lastFrameEvents);
        agentEvents.add(new MarioAgentEvent(actions, this.world.mario.x,
                this.world.mario.y, (this.world.mario.isLarge ? 1 : 0) + (this.world.mario.isFire ? 1 : 0),
                this.world.mario.onGround, this.world.currentTick));
    }
    
    void warnAITimeout(){
        if (MarioGame.verbose) {
            if (agentTimer.getRemainingTime() < 0 && Math.abs(agentTimer.getRemainingTime()) > MarioGame.graceTime) {
                System.out.println("The Agent is slowing down the game by: "
                        + Math.abs(agentTimer.getRemainingTime()) + " msec.");
            }
        }
    }
    
    boolean[] getAIActions(MarioForwardModel model){
        agentTimer = new MarioTimer(MarioGame.maxTime);
        boolean[] actions = this.agent.getActions(model, agentTimer);
        warnAITimeout();
        return actions;
    }

    //Observation-klass
    //Action-klass -getNumPossibleActions --3 nodes between 0...1 --All possible actions nodes between 0...1
    //get Observation at step --> requires observation-klass
    //initTrainingmap -Done
    //initGame -Done
    //bool isGameOver? -Done
    //Get Reward at step -Done
    
	int previousXLocation = 0;
	int prevMarioState = 2;
	
	public MarioForwardModel getModel(){
		return new MarioForwardModel(world.clone());
	}
	
    public float getReward(){
		float res = -0.01f;
		MarioForwardModel model = new MarioForwardModel(world.clone());
		int nowXLocation = ((int) model.getMarioFloatPos()[0]) / 16;
		float locationReward = ((float)(nowXLocation-previousXLocation))/4.0f;
		previousXLocation = nowXLocation;

		res += locationReward;
		if (prevMarioState < model.getMarioMode()) {
			prevMarioState = model.getMarioMode();
			res -= 0.25f;
		}
		if(model.getGameStatus() == GameStatus.LOSE)
			res = -1.f;
		
		if(res > 1.0f)
			res = 1.0f;
		if(res < -1.0f)
			res = -1.0f;
		
		return res;
    }
    
    public boolean isGameDone(){
    	return world.gameStatus != GameStatus.RUNNING;
    }
    
    
    public void stepWorld(boolean[] actions, boolean shootFire){
    	boolean wasRunning = actions[MarioActions.SPEED.getValue()];
    	for(int i = 0 ; i < 5; i++){ //update world 5 frames each step
            updateWorld(actions);
            if(i == 1 && shootFire){
            	actions[MarioActions.SPEED.getValue()] = false;
            }
            if(i == 2 && shootFire){
            	actions[MarioActions.SPEED.getValue()] = wasRunning;
            }
    	}
    }
    
    public void stepWorldWithVisuals(boolean[] actions, boolean shootFire){
    	boolean wasRunning = actions[MarioActions.SPEED.getValue()];
    	for(int i = 0 ; i < 5; i++){ //update world 5 frames each step
            updateWorld(actions);
            if(i == 1 && shootFire){
            	actions[MarioActions.SPEED.getValue()] = false;
            }
            if(i == 2 && shootFire){
            	actions[MarioActions.SPEED.getValue()] = wasRunning;
            }
        	MarioForwardModel model = new MarioForwardModel(world.clone());
            renderWorld(model);
            delay(21);
    	}
    }
    
    public MarioResult getResult(){
    	return new MarioResult(this.world, gameEvents, agentEvents);
    }

    

    public MarioResult gameLoop(String level, int timer, int marioState, boolean visual, int fps) {
    	initGame(level, timer, marioState, visual);

        while (world.gameStatus == GameStatus.RUNNING) {
        	MarioForwardModel model = new MarioForwardModel(world.clone());
            if (!pause) {
                boolean[] actions = getAIActions(model);
                updateWorld(actions);
            }

            renderWorld(model);
            delay(fps);
        }
        return new MarioResult(this.world, gameEvents, agentEvents);
    }
}
