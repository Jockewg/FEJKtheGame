package com.fejkathegame.game.arena.maps.practice03;

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
import com.fejkathegame.game.entities.Character;

import java.io.IOException;

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

    private float offsetMaxX;
    private float offsetMaxY;

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
        return Main.BIGBLUESTATE;
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

        physics = new Physics();

        camera = new PracticeCamera(offsetMaxX, offsetMaxY);

        helper = new PracticeStateHelper(arena, obj, camera);
    }

    public void setCameraBoundaries() {
        offsetMaxX = arena.getMap().getWidth() * 22;
        offsetMaxY = arena.getMap().getHeight() * 20;
    }


    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setAntiAlias(false);
        g.scale(Main.SCALE, Main.SCALE);
        g.translate(-camera.getCamX(), -camera.getCamY());
        arena.render();
        arena.helper.updateText(camera.getCamX(), camera.getCamY());
        g.resetTransform();
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        helper.checkCameraOffset();
        movementSystem.handleInput(gc.getInput(), i);
        physics.handlePhysics(arena, i);
        helper.checkCollisionWithTarget();
        obj.update(i);
    }



}
