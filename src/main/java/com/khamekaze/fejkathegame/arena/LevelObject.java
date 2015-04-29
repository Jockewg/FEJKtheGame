package com.khamekaze.fejkathegame.arena;

import com.khamekaze.fejkathegame.collision.AABoundingRect;
import com.khamekaze.fejkathegame.collision.BoundingShape;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class LevelObject {
    
    protected float x, y;
    protected BoundingShape boundingShape;
    
    protected float x_velocity = 0;
    protected float y_velocity = 0;
    protected float maxFallSpeed = 1;
    protected float decelerationSpeed = 1;
    protected Image sprite;
    protected boolean moving = false;
    
    protected boolean onGround = true;
    
    public LevelObject(float x, float y) throws SlickException {
        this.x = x;
        this.y = y;
        
        sprite = new Image("data/img/placeholder.png");
        
        boundingShape = new AABoundingRect(x, y, 25, 25);
    }
    
    public void applyGravity(float gravity) {
        if(y_velocity < maxFallSpeed) {
            y_velocity += gravity;
            
            if(y_velocity > maxFallSpeed) {
                y_velocity = maxFallSpeed;
            }
        }
    }
    
    public void updateBoundingShape() {
        boundingShape.updatePosition(x, y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        updateBoundingShape();
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        updateBoundingShape();
    }

    public BoundingShape getBoundingShape() {
        return boundingShape;
    }

    public void setBoundingShape(BoundingShape boundingShape) {
        this.boundingShape = boundingShape;
    }

    public float getX_velocity() {
        return x_velocity;
    }

    public void setX_velocity(float x_velocity) {
        this.x_velocity = x_velocity;
    }

    public float getY_velocity() {
        return y_velocity;
    }

    public void setY_velocity(float y_velocity) {
        this.y_velocity = y_velocity;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
    
    public void render() throws SlickException {
        sprite.draw(x - 4, y - 4);
    }
    
    public boolean isMoving() {
        return moving;
    }
    
    public void setMoving(boolean moving) {
        this.moving = moving;
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
    

}
