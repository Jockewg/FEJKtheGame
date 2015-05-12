package com.fejkathegame.game.arena.maps.versus01;

import com.fejkathegame.game.entities.logic.MovementSystem;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.entities.LevelObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class VersusState extends BasicGameState {

    private Versus arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private Character obj;
    private Character player2;
    
    private float cameraX, cameraY;
    private float cameraWidth = 900;
    private float cameraHeight = cameraWidth * 0.55f;
    private float cameraScale = 1.0f;
    
    //Camera stuff
    private float offsetMaxX = 2050;
    private float offsetMaxY = 750;
    private float offsetMinX = 0;
    private float offsetMinY = 0;
    private float offsetX = 0;
    private float offsetY = 0;
    private float camX, camY = 0;
    private Line line;
    
    private ArrayList<Character> characters;

    /**
     * Constructor for ArenaState
     * @param name of the stage
     */
    public VersusState(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return Main.VERSUSSTATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        obj = null;
        player2 = null;
        try {
            obj = new Character(800, 40);
            player2 = new Character(200, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        characters = new ArrayList<>();
        characters.add(obj);
        characters.add(player2);

        line = new Line(obj.getX(), obj.getY(), player2.getX(), player2.getY());
        
        arena = new Versus(name, obj);
        arena.addPlayer(player2);
        
        movementSystem = new MovementSystem(obj);

        physics = new Physics();

    }
    
    public void checkCollisionWithTarget() {
        
        if(obj.getAttackIndicator().intersects(player2.getHitBox()) && obj.getIsAttacking()
                || obj.getIsFullyCharged() && obj.getSuperAttackIndicator().intersects(player2.getHitBox())) {
            System.out.println("Player 1 hit Player 2 omfg");
            player2.getHealthSystem().dealDamage(1);
            if(player2.getHealth() <= 0) {
                arena.getPlayers().remove(player2);
            }
        }
        
        if(player2.getAttackIndicator().intersects(obj.getHitBox()) && player2.getIsAttacking()
                || player2.getIsFullyCharged() && player2.getSuperAttackIndicator().intersects(obj.getHitBox())) {
            System.out.println("Player 2 hit Player 1 omfg");
            obj.getHealthSystem().dealDamage(1);
            if(obj.getHealth() <= 0) {
                arena.getPlayers().remove(obj);
            }
        }
        
    }
    
    public void updateCameraRect() {
        float dY = line.getDY();
        float dX = +line.getDX();
        if(dX < 0) {
            dX = dX * -1;
        }
        if(dY < 0) {
            dY = dY * -1;
        }
        cameraWidth = dX + 100;
        if(cameraWidth < 364) {
            cameraWidth = 364;
        }
        cameraHeight = cameraWidth * 0.55f;
        if(cameraHeight < dY + 100) {
            cameraHeight = dY + 100;
            cameraWidth = cameraHeight / 0.55f;
        }
        
        cameraX = line.getCenterX() - (cameraWidth / 2);
        cameraY = line.getCenterY() - (cameraHeight / 2);
        
        if((cameraX + cameraWidth) > 900) {
            float newX = (cameraX + cameraWidth) - 900;
            cameraX -= newX;
        } else if(cameraX < 1) {
            cameraX = 1;
        }
        
        if((cameraY + cameraHeight) > 499) {
            float newY = (cameraY + cameraHeight) - 499;
            cameraY -= newY;
        } else if(cameraY < 1) {
            cameraY = 1;
        }
        
        cameraScale = 900 / cameraWidth;
        
        
    }
    
    public void updateVectorLine() {
        Vector2f objVector = new Vector2f(obj.getX(), obj.getY());
        Vector2f player2Vector = new Vector2f(player2.getX(), player2.getY());
        line = new Line(objVector, player2Vector);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.scale(cameraScale, cameraScale);
        g.translate(-cameraX, -cameraY);
        arena.getAnimation().draw(200, 50);
        arena.render();
        
        g.resetTransform();
        g.translate(-cameraX, -cameraY);
        obj.getHealthSystem().render(cameraX + 450 - 135 - 60, cameraY + 473);
        player2.getHealthSystem().render(cameraX + 450 + 60, cameraY + 473);
        g.resetTransform();
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        updateVectorLine();
        updateCameraRect();
        movementSystem.handleInput(gc.getInput(), i);
        physics.handlePhysics(arena, i);
        player2.update(i);
        obj.update(i);
        checkCollisionWithTarget();
    }

}
