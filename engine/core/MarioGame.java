package engine.core;

import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.KeyAdapter;

import javax.swing.JFrame;

import agents.BT.BTAgent;
import agents.human.Agent;
import engine.helper.GameStatus;
import engine.helper.MarioActions;

public class MarioGame {
    public static final long maxTime = 40;
    public static final long graceTime = 10;
   // public static final int width = 256*2;
    public static final int width = 400;
    /**
     * Screen height
     */
    //public static final int height = 256;
    public static final int height = 256;
    public static final int tileWidth = (width / 16);
    public static final int tileHeight = height / 16;
    public static final boolean verbose = false;
    public boolean pause = false;
    private MarioEvent[] killEvents;

    public JFrame window = null;
    private MarioRender render = null;
    private MarioAgent agent = null;
    private MarioWorld world = null;

    public MarioGame() {}

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

    /**
     * Play a certain mario level
     *
     * @param level a string that constitutes the mario level, it uses the same representation as the VGLC but with more details. for more details about each symbol check the json file in the levels folder.
     * @param timer number of ticks for that level to be played. Setting timer to anything <=0 will make the time infinite
     * @return statistics about the current game
     */
    public MarioResult playGame(String level, int timer) {
        return this.runGame(new Agent(), level, timer, 0, true, 30, 2);
    }

    /**
     * Play a certain mario level
     *
     * @param level      a string that constitutes the mario level, it uses the same representation as the VGLC but with more details. for more details about each symbol check the json file in the levels folder.
     * @param timer      number of ticks for that level to be played. Setting timer to anything <=0 will make the time infinite
     * @param marioState the initial state that mario appears in. 0 small mario, 1 large mario, and 2 fire mario.
     * @return statistics about the current game
     */
    public MarioResult playGame(String level, int timer, int marioState) {
        return this.runGame(new Agent(), level, timer, marioState, true, 30, 2);
    }

    /**
     * Play a certain mario level
     *
     * @param level      a string that constitutes the mario level, it uses the same representation as the VGLC but with more details. for more details about each symbol check the json file in the levels folder.
     * @param timer      number of ticks for that level to be played. Setting timer to anything <=0 will make the time infinite
     * @param marioState the initial state that mario appears in. 0 small mario, 1 large mario, and 2 fire mario.
     * @param fps        the number of frames per second that the update function is following
     * @return statistics about the current game
     */
    public MarioResult playGame(String level, int timer, int marioState, int fps) {
        return this.runGame(new Agent(), level, timer, marioState, true, fps, 2);
    }

    /**
     * Play a certain mario level
     *
     * @param level      a string that constitutes the mario level, it uses the same representation as the VGLC but with more details. for more details about each symbol check the json file in the levels folder.
     * @param timer      number of ticks for that level to be played. Setting timer to anything <=0 will make the time infinite
     * @param marioState the initial state that mario appears in. 0 small mario, 1 large mario, and 2 fire mario.
     * @param fps        the number of frames per second that the update function is following
     * @param scale      the screen scale, that scale value is multiplied by the actual width and height
     * @return statistics about the current game
     */
    public MarioResult playGame(String level, int timer, int marioState, int fps, float scale) {
        return this.runGame(new Agent(), level, timer, marioState, true, fps, scale);
    }

    /**
     * Run a certain mario level with a certain agent
     *
     * @param agent the current AI agent used to play the game
     * @param level a string that constitutes the mario level, it uses the same representation as the VGLC but with more details. for more details about each symbol check the json file in the levels folder.
     * @param timer number of ticks for that level to be played. Setting timer to anything <=0 will make the time infinite
     * @return statistics about the current game
     */
    public MarioResult runGame(MarioAgent agent, String level, int timer) {
        return this.runGame(agent, level, timer, 0, false, 0, 2);
    }

    /**
     * Run a certain mario level with a certain agent
     *
     * @param agent      the current AI agent used to play the game
     * @param level      a string that constitutes the mario level, it uses the same representation as the VGLC but with more details. for more details about each symbol check the json file in the levels folder.
     * @param timer      number of ticks for that level to be played. Setting timer to anything <=0 will make the time infinite
     * @param marioState the initial state that mario appears in. 0 small mario, 1 large mario, and 2 fire mario.
     * @return statistics about the current game
     */
    public MarioResult runGame(MarioAgent agent, String level, int timer, int marioState) {
        return this.runGame(agent, level, timer, marioState, false, 0, 2);
    }

    /**
     * Run a certain mario level with a certain agent
     *
     * @param agent      the current AI agent used to play the game
     * @param level      a string that constitutes the mario level, it uses the same representation as the VGLC but with more details. for more details about each symbol check the json file in the levels folder.
     * @param timer      number of ticks for that level to be played. Setting timer to anything <=0 will make the time infinite
     * @param marioState the initial state that mario appears in. 0 small mario, 1 large mario, and 2 fire mario.
     * @param visuals    show the game visuals if it is true and false otherwise
     * @return statistics about the current game
     */
    public MarioResult runGame(MarioAgent agent, String level, int timer, int marioState, boolean visuals) {
        return this.runGame(agent, level, timer, marioState, visuals, visuals ? 30 : 0, 2);
    }

    /**
     * Run a certain mario level with a certain agent
     *
     * @param agent      the current AI agent used to play the game
     * @param level      a string that constitutes the mario level, it uses the same representation as the VGLC but with more details. for more details about each symbol check the json file in the levels folder.
     * @param timer      number of ticks for that level to be played. Setting timer to anything <=0 will make the time infinite
     * @param marioState the initial state that mario appears in. 0 small mario, 1 large mario, and 2 fire mario.
     * @param visuals    show the game visuals if it is true and false otherwise
     * @param fps        the number of frames per second that the update function is following
     * @return statistics about the current game
     */
    public MarioResult runGame(MarioAgent agent, String level, int timer, int marioState, boolean visuals, int fps) {
        return this.runGame(agent, level, timer, marioState, visuals, fps, 2);
    }


    KeyController keyController = null;
    
    public MarioResult runGame(MarioAgent agent, String level, int timer, int marioState, boolean visuals, int fps, float scale) {
        if (visuals) {
            window = new JFrame("Mario AI Framework");
            keyController = new KeyController();
            keyController.marioGame = this;
           // window.addKeyListener(keyController);
            
            window.setUndecorated(true);
            render = new MarioRender(scale);
            render.addKeyListener(keyController);
            
            window.setContentPane(render);
            window.pack();
            window.setResizable(false);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            render.init();
            window.setVisible(true);
            
        	if(agent instanceof BTAgent){
        		render.btAgent = (BTAgent) agent;
        	}
        }
        this.setAgent(agent);
        return this.gameLoop(level, timer, marioState, visuals, fps);
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
    
    
    void initGame(String level, int timer, int marioState, boolean visual, int fps){
        this.world = new MarioWorld(this.killEvents);
        this.world.visuals = visual;
        this.world.initializeLevel(level, 1000 * timer);
        if (visual) {
            this.world.initializeVisuals(this.render.getGraphicsConfiguration());
        }
        this.world.mario.isLarge = marioState > 0;
        this.world.mario.isFire = marioState > 1;
        this.world.update(new boolean[MarioActions.numberOfActions()]);
        currentTime = System.currentTimeMillis();

        //initialize graphics
        if (visual) {
            renderTarget = this.render.createVolatileImage(MarioGame.width, MarioGame.height);
            backBuffer = this.render.getGraphics();
            currentBuffer = renderTarget.getGraphics();
            this.render.addFocusListener(this.render);
        }
        gameEvents = new ArrayList<>();
        agentEvents = new ArrayList<>();
        
        agentTimer = new MarioTimer(MarioGame.maxTime);
        this.agent.initialize(new MarioForwardModel(this.world.clone()), agentTimer);
    }
    
    void renderWorld(MarioForwardModel model){
        if (this.world.visuals) {
        	
        	render.model = model;
            render.renderWorld(this.world, renderTarget, backBuffer, currentBuffer);
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


    private MarioResult gameLoop(String level, int timer, int marioState, boolean visual, int fps) {
    	initGame(level, timer, marioState, visual, fps);

        while (this.world.gameStatus == GameStatus.RUNNING) {
        	MarioForwardModel model = new MarioForwardModel(this.world.clone());
            if (!this.pause) {
                boolean[] actions = getAIActions(model);
                updateWorld(actions);
            }

            renderWorld(model);
            delay(fps);
        }
        return new MarioResult(this.world, gameEvents, agentEvents);
    }
}
