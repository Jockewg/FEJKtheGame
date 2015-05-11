package com.fejkathegame.game.arena.practice;

import com.fejkathegame.game.entities.logic.MovementSystem;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.physics.Physics;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;
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
    private com.fejkathegame.game.entities.Character player2;
    
    private float offsetMaxX = 2050;
    private float offsetMaxY = 750;
    private float offsetMinX = 0;
    private float offsetMinY = 0;
    private float camX, camY = 0;
    private float acc = 5.0f;
    private Line line;

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
        
        try {
            player2 = new com.fejkathegame.game.entities.Character(64, 40);
        } catch (IOException ex) {
            Logger.getLogger(PracticeState.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        line = new Line(obj.getX(), obj.getY(), player2.getX(), player2.getY());


        arena = new Practice(name, obj);
        arena.addPlayer(player2);
        
        movementSystem = new MovementSystem(obj);

        physics = new Physics();

 


    }
    
    public void checkCameraOffset() {
        
        
        
        Vector2f objVector = new Vector2f(obj.getX(), obj.getY());
        Vector2f player2Vector = new Vector2f(player2.getX(), player2.getY());
        
        line = new Line(objVector, player2Vector);
        
        camX = line.getCenterX() - 450;
        camY = line.getCenterY() - 250;
        
        
//        if(obj.getX() <= offsetMinX + 450)
//            camX = offsetMinX;
//        else if(obj.getX() >= offsetMaxX)
//            camX = offsetMaxX - 450;
//        else
//            camX = obj.getX() - 450.0f;
//        
//        if(obj.getY() <= offsetMinY + 250)
//            camY = offsetMinY;
//        else if(obj.getY() >= offsetMaxY)
//            camY = offsetMaxY - 250;
//        else
//        camY = obj.getY() - 250.0f;
    }
    

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setAntiAlias(false);
        g.scale(Main.SCALE, Main.SCALE);
        g.translate(-camX, -camY);
        
        arena.render();
        arena.updateText(camX , camY);
        g.setColor(Color.yellow);
        g.drawLine(obj.getX() + 9.5f, obj.getY() + 12.5f, player2.getX() + 9.5f, player2.getY() + 12.5f);
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
        System.out.println(line.getX2());
        System.out.println(line.getX1());
    }
    
    public void checkCollisionWithTarget() {
        for(int i = 0; i < arena.getTargets().size(); i++) {
            if(obj.getAttackIndicator().intersects(arena.getTargets().get(i).getHitbox()) && obj.getIsAttacking()
                    || obj.getIsFullyCharged() && obj.getSuperAttackIndicator().intersects(arena.getTargets().get(i).getHitbox())) {
                System.out.println("HIT");
                arena.getTargets().get(i).getHealthSystem().dealDamage(1);
                arena.getTargets().remove(i);
                arena.updateScore();
            }
        }
    }

}
