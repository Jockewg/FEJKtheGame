package com.khamekaze.fejkathegame.arena;

import com.khamekaze.fejkathegame.collision.AABoundingRect;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player extends LevelObject {
    
    protected Image sprite;
    
    protected float accelerationSpeed = 1;
    protected float decelerationSpeed = 1;
    protected float maximumSpeed = 1;
    protected boolean moving = false;
    
    public Player(float x, float y) throws SlickException {
        super(x, y);
        
        sprite = new Image("data/img/placeholder.png");
        
        boundingShape = new AABoundingRect(x, y, 25, 25);
        
        accelerationSpeed = 0.001f;
        maximumSpeed = 0.15f;
        maxFallSpeed = 0.3f;
        decelerationSpeed = 0.001f;
    }
    
    public void updateBoundingShape() {
        boundingShape.updatePosition(x, y);
    }
    
    public void decelerate(int delta) {
        if(x_velocity > 0) {
            x_velocity -= decelerationSpeed * delta;
            if(x_velocity < 0) {
                x_velocity = 0;
            }
        } else if(x_velocity < 0) {
            x_velocity += decelerationSpeed * delta;
            if(x_velocity > 0) {
                x_velocity = 0;
            }
        }
    }
    
    public void jump() {
        if(onGround) {
            y_velocity = -0.4f;
        }
    }
    
    public void moveLeft(int delta) {
        if(x_velocity > -maximumSpeed) {
            x_velocity -= accelerationSpeed * delta;
            if(x_velocity < -maximumSpeed) {
                x_velocity = -maximumSpeed;
            }
        }
        moving = true;
    }
    
    public void moveRight(int delta) {
        if(x_velocity < maximumSpeed) {
            x_velocity += accelerationSpeed * delta;
            if(x_velocity > maximumSpeed) {
                x_velocity = maximumSpeed;
            }
        }
        moving = true;
    }
    
    public boolean isMoving() {
        return moving;
    }
    
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public void render() {
        sprite.draw(x-2, y-2);
    }

}
