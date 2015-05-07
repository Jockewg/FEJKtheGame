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
    
    private float offsetMaxX = 2050;
    private float offsetMaxY = 750;
    private float offsetMinX = 0;
    private float offsetMinY = 0;
    private float camX, camY = 0;
    private float acc = 5.0f;

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
        arena.updateText(camX += 745, camY);
        g.resetTransform();
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        checkCameraOffset();
        movementSystem.handleInput(gc.getInput(), i);
        physics.handlePhysics(arena, i);
        checkCollisionWithTarget();
        obj.update(i);
        arena.moveTarget();
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
