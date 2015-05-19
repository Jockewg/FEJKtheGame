package com.fejkathegame.game.arena.maps.singelplayer.bigblue03;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.arena.maps.PracticeStateHelper;
import com.fejkathegame.game.arena.maps.UIHelper;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.arena.tiles.AirTile;
import com.fejkathegame.game.arena.tiles.TargetTile;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.logic.MovementSystem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.entities.PracticeTarget;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * @author Swartt
 */
public class BigBlue02State extends BasicGameState {
    private BigBlue02 arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private Character obj;
    private PracticeStateHelper helper;
    private PracticeCamera camera;
    private UIHelper practiceUIHelper;

    private float offsetMaxX;
    private float offsetMaxY;
    
    private boolean isCameraAnimationRunning = true;
    private float scale = 0.24f;
    private float scaleSmoothing = 0;
    
    private Shape pauseMenuBackground;
    
    private boolean paused = false;

    /**
     * Constructor for ArenaState
     *
     * @param name of the stage
     */
    public BigBlue02State(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return Main.BIG_BlUE03;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        obj = null;
        try {
            obj = new com.fejkathegame.game.entities.Character(50, 1100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        

        arena = new BigBlue02(name, obj);

        setCameraBoundaries();

        movementSystem = new MovementSystem(obj);
        
        pauseMenuBackground = new Rectangle(0, 0, 600, 300);

        physics = new Physics();

        camera = new PracticeCamera(offsetMaxX, offsetMaxY);
        
        practiceUIHelper = new UIHelper(camera.getCamX(), camera.getCamY());

        helper = new PracticeStateHelper(arena, obj, camera);
    }

    public void setCameraBoundaries() {
        offsetMaxX = arena.getMap().getWidth() * 22;
        offsetMaxY = arena.getMap().getHeight() * 20;
    }
    
    public void cameraAnimation() {
        if(scale < 0.25f) {
            scale += 0.00005f;
            camera.setCamY(0);
        } else {
            scaleSmoothing += 0.00005f;
            scale += (0.005f + scaleSmoothing);
            camera.setCamY(0);
            float newCamY = (750 * scale);
            camera.setCamY(newCamY);
            /*System.out.println("cameraY: " + camera.getCamY());*/
        }
        
        if(scale >= 1) {
            scale = 1;
            isCameraAnimationRunning = false;
        }
        
        /*System.out.println(scale);*/
        
    }
    
    public void pauseGame(Input i, StateBasedGame sbg) throws SlickException, IOException {
        if(i.isKeyPressed(Input.KEY_ESCAPE)) {
            if(!paused)
                paused = true;
            else
                paused = false;
        }
        
        if(paused && i.isKeyPressed(Input.KEY_X)) {
            sbg.enterState(Main.LEVELSELECTSTATE);
            resetLevel();
        }
    }
    
    public void drawPauseMenu(Graphics g) {
        g.setColor(new Color(0.8f, 0.8f, 0.8f, 0.8f));
        pauseMenuBackground.setCenterX(camera.getCamX() + 450);
        pauseMenuBackground.setCenterY(camera.getCamY() + 250);
        g.fill(pauseMenuBackground);
        g.setColor(Color.yellow);
        g.drawString("Press escape to unpause or X to exit the level", camera.getCamX() + 300, camera.getCamY() + 150);
        System.out.println("X: " + pauseMenuBackground.getCenterX());
        System.out.println("Y: " + pauseMenuBackground.getCenterY());
    }
    
    public void resetLevel() throws SlickException, IOException {
        isCameraAnimationRunning = true;
        paused = false;
        scale = 0.24f;
        obj.setX(50);
        obj.setY(1100);
        arena.getTargets().clear();
        for(Tile at : arena.getTargetTiles()) {
            PracticeTarget pt = new PracticeTarget(at.getX() * 25, at.getY() * 25);
            arena.getTargets().add(pt);
        }
    }


    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setAntiAlias(false);
        if(isCameraAnimationRunning) {
            cameraAnimation();
            g.scale(scale, scale);
        } else {
            g.scale(scale, scale);
        }
        g.translate(-camera.getCamX(), -camera.getCamY());
        arena.render();
        practiceUIHelper.renderPracticeUI(arena.helper);
        if(paused && !isCameraAnimationRunning) {
            drawPauseMenu(g);
        }
        g.resetTransform();
        
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        try {
            pauseGame(gc.getInput(), sbg);
        } catch (IOException ex) {
            Logger.getLogger(BigBlue02State.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!paused) {
            helper.checkCameraOffset();
            if(!arena.timer.isCountdownRunning()) {
                movementSystem.handleInput(gc.getInput(), i);
                physics.handlePhysics(arena, i);
                helper.checkCollisionWithTarget(getID());
            }
            obj.update(i);
            arena.timer.calculateSecond(i);
        }
        System.out.println("Paused: " + paused);
    }
}
