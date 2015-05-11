package com.fejkathegame.game.arena.maps.practice02;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.maps.StateHelper;
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
    private StateHelper helper;

    private float offsetMaxX = 450;
    private float offsetMaxY = 2250;
    private float offsetMinX = 0;
    private float offsetMinY = 0;
    private float camX, camY = 0;
    private float acc = 5.0f;

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

        helper = new StateHelper(arena, obj);


    }
    
    public void checkCameraOffset() {
        if(obj.getX() <= offsetMinX + 450)
            camX = offsetMinX;
        else if(obj.getX() >= offsetMaxX)
            camX = offsetMaxX - 450;
        else
            camX = obj.getX() - 450.0f;
        
        if(obj.getY() <= offsetMinY + 250)
            camY = offsetMinY;
        else if(obj.getY() >= offsetMaxY)
            camY = offsetMaxY - 250;
        else
        camY = obj.getY() - 250.0f;
    }
    

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setAntiAlias(false);
        g.scale(Main.SCALE, Main.SCALE);
        g.translate(-camX, -camY);
        arena.render();
        arena.helper.updateText(camX, camY);
        g.resetTransform();
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        checkCameraOffset();
        movementSystem.handleInput(gc.getInput(), i);
        physics.handlePhysics(arena, i);
        helper.checkCollisionWithTarget();
        obj.update(i);
    }


}
