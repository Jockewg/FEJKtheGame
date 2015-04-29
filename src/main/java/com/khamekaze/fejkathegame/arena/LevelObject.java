package com.khamekaze.fejkathegame.arena;

import com.khamekaze.fejkathegame.collision.AABoundingRect;
import com.khamekaze.fejkathegame.collision.BoundingShape;

public class LevelObject {
    
    protected float x, y;
    protected BoundingShape boundingShape;
    
    protected float x_velocity = 0;
    protected float y_velocity = 0;
    protected float maxFallSpeed = 1;
    
    protected boolean onGround = true;
    
    public LevelObject(float x, float y) {
        this.x = x;
        this.y = y;
        
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
    
    

}
