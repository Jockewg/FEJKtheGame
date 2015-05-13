/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena.maps.tutorial04;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.arena.maps.PracticeStateHelper;
import com.fejkathegame.game.arena.maps.practice03.BigBlue02;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.logic.MovementSystem;
import java.io.IOException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Swartt
 */
public class Tutorial03State extends BasicGameState {
   
    private Tutorial03 arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private com.fejkathegame.game.entities.Character obj;
    private PracticeStateHelper helper;
    private PracticeCamera camera;

    private float offsetMaxX;
    private float offsetMaxY;
    
    private boolean inPit = false;

    /**
     * Constructor for ArenaState
     *
     * @param name of the stage
     */
    public Tutorial03State(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return Main.TUTORIAL;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        obj = null;
        try {
            obj = new com.fejkathegame.game.entities.Character(50, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }

        arena = new Tutorial03(name, obj);

        setCameraBoundaries();

        movementSystem = new MovementSystem(obj);

        physics = new Physics();

        camera = new PracticeCamera(offsetMaxX, offsetMaxY);

        helper = new PracticeStateHelper(arena, obj, camera);
    }

    public void setCameraBoundaries() {
        offsetMaxX = arena.getMap().getWidth() * 25 -450;
        offsetMaxY = arena.getMap().getHeight() * 25 -325;
    }
    
    public void checkIfInPit() {
        int tileId = arena.getMap().getTileId((int) (obj.getX()) / 25, (int) (obj.getY() + 50) / 25, 0);
        String property = arena.getMap().getTileProperty(tileId, "tileType", "pit");
        
        if("pit".equals(property)) {
            inPit = true;
        } else {
            inPit = false;
        }
    }


    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setAntiAlias(false);
        g.scale(Main.SCALE, Main.SCALE);
        g.translate(-camera.getCamX(), -camera.getCamY());
        arena.render();
        /*arena.helper.updateText(camera.getCamX(), camera.getCamY());*/
        g.resetTransform();
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        checkIfInPit();
        if(inPit) {
            sbg.enterState(Main.TUTORIAL);
        }
        helper.checkCameraOffset();
        movementSystem.handleInput(gc.getInput(), i);
        physics.handlePhysics(arena, i);
        helper.checkCollisionWithTarget(getID());
        obj.update(i);
        arena.timer.calculateSecond(i);
        
    }
}
