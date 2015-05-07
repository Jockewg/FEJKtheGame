package com.fejkathegame.game.arena.practice;

import com.fejkathegame.game.entities.logic.MovementSystem;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.physics.Physics;

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
public class PracticeState extends BasicGameState {
    private Practice arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private com.fejkathegame.game.entities.Character obj;
    
    private float offsetMaxX = Main.WINDOW_WIDTH - 900;
    private float offsetMaxY = Main.WINDOW_HEIGHT - 500;
    private float offsetMinX, offsetMinY = 0;
    private float camX, camY = 0;

    /**
     * Constructor for ArenaState
     * @param name of the stage
     */
    public PracticeState(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        obj = null;
        try {
            obj = new com.fejkathegame.game.entities.Character(32, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }


        arena = new Practice(name, obj);
        
        movementSystem = new MovementSystem(obj);

        physics = new Physics();



    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setAntiAlias(false);
        g.scale(0.85f, 0.85f);
        g.translate(-camX, -camY);
        arena.render();
        g.resetTransform();
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        camX = obj.getX() - 450;
        camY = obj.getY() - 250;
        movementSystem.handleInput(gc.getInput(), i);
        physics.handlePhysics(arena, i);
        checkCollisionWithTarget();
        obj.update();
    }
    
    public void checkCollisionWithTarget() {
        for(int i = 0; i < arena.getTargets().size(); i++) {
            if(obj.getAttackIndicator().intersects(arena.getTargets().get(i).getHitbox())) {
                System.out.println("HIT");
                arena.getTargets().get(i).getHealthSystem().dealDamage(1);
                arena.getTargets().remove(i);
                arena.updateScore();
            }
        }
    }

}
