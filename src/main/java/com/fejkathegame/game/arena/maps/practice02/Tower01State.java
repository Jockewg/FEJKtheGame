package com.fejkathegame.game.arena.maps.practice02;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.arena.maps.PracticeStateHelper;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.entities.logic.MovementSystem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 *
 * @author Swartt
 */
public class Tower01State extends BasicGameState {
    private Tower01 arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private com.fejkathegame.game.entities.Character obj;
    private PracticeStateHelper helper;
    private PracticeCamera camera;

    private float offsetMaxX = 450;
    private float offsetMaxY = 2250;
    
    private boolean isCameraAnimationRunning = true;
    private int cameraMotionY = 0;

    /**
     * Constructor for ArenaState
     * @param name of the stage
     */
    public Tower01State(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return Main.TOWER1STATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        obj = null;
        try {
            obj = new com.fejkathegame.game.entities.Character(450, 2424);
        } catch (IOException e) {
            e.printStackTrace();
        }


        arena = new Tower01(name, obj);
        
        movementSystem = new MovementSystem(obj);

        physics = new Physics();

        camera = new PracticeCamera(offsetMaxX, offsetMaxY);

        helper = new PracticeStateHelper(arena, obj, camera);



    }
    
    public void cameraAnimation() {
        cameraMotionY += 5;
        camera.setCamX(0);
        camera.setCamY(cameraMotionY);
        if(cameraMotionY >= offsetMaxY - 250) {
            isCameraAnimationRunning = false;
        }
    }

    

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setAntiAlias(false);
        g.scale(Main.SCALE, Main.SCALE);
        if(isCameraAnimationRunning) {
            cameraAnimation();
            g.translate(-camera.getCamX(), -camera.getCamY());
        } else {
            g.translate(-camera.getCamX(), -camera.getCamY());
        }
        arena.render();
        arena.helper.updateText(camera.getCamX(), camera.getCamY());
        g.resetTransform();
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        helper.checkCameraOffset();
        if(!isCameraAnimationRunning) {
            movementSystem.handleInput(gc.getInput(), i);
            physics.handlePhysics(arena, i);
            helper.checkCollisionWithTarget();
            obj.update(i);
        }
        
    }


}
