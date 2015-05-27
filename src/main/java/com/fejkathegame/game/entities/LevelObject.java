package com.fejkathegame.game.entities;

import com.fejkathegame.game.arena.collision.AABoundingRect;
import com.fejkathegame.game.arena.collision.BoundingShape;
import com.fejkathegame.game.entities.logic.HealthSystem;

import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class LevelObject {

    public float x, y;
    public BoundingShape boundingShape;
    public float x_velocity = 0;
    public float y_velocity = 0;
    public float maxFallSpeed = 1;
    public float decelerationSpeed = 1;
    public boolean moving = false;
    public int storedJumps;
    public boolean onGround = true;
    public int storedAttacks;
    public int health;
    public boolean isAlive;
    public Graphics g;
    public boolean isAttacking = false;
    public Shape hitBox;
    public HealthSystem healthSystem;

    /**
     * Constructor for a {@code LevelObject}, creates a new level entity with standard values
     *
     * @param x coordinate for spawing the object
     * @param y coordinate for spawing the object
     * @throws SlickException
     */
    public LevelObject(float x, float y) throws SlickException, IOException {
        this.x = x;
        this.y = y;
        g = new Graphics();

        storedJumps = 0;

        storedAttacks = 0;
        healthSystem = new HealthSystem(this);

        boundingShape = new AABoundingRect(x, y, 32, 31);
        hitBox = new Rectangle(x, y, 32, 32);
    }

    /**
     * Applies given gravity to the object
     *
     * @param gravity
     */
    public void applyGravity(float gravity) {
        if (y_velocity < maxFallSpeed) {
            y_velocity += gravity;

            if (y_velocity > maxFallSpeed) {
                y_velocity = maxFallSpeed;
            }
        }
    }

    /**
     * changes the coordinates of the object
     */
    public void updateBoundingShape() {
        boundingShape.updatePosition(x, y);
    }

    public void updateHitBox() {
        hitBox.setX(x);
        hitBox.setY(y);
    }

    public Shape getHitBox() {
        return hitBox;
    }

    public void setHitBox(Shape hitBox) {
        this.hitBox = hitBox;
    }

    public void setHealthSystem(HealthSystem hs) {
        this.healthSystem = hs;
    }

    public HealthSystem getHealthSystem() {
        return healthSystem;
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
        updateHitBox();
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getStoredJumps() {
        return storedJumps;
    }

    public void setStoredJumps(int storedJumps) {
        this.storedJumps = storedJumps;
    }

    public int getStoredAttacks() {
        return storedAttacks;
    }

    public void setStoredAttacks(int storedAttacks) {
        this.storedAttacks = storedAttacks;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean getIsAttacking() {
        return isAttacking;
    }


    /**
     * Applies deceleration to the object
     *
     * @param delta
     */
    public void decelerate(int delta) {
        if (x_velocity > 0) {
            x_velocity -= decelerationSpeed * delta;
            if (x_velocity < 0) {
                x_velocity = 0;
            }
        } else if (x_velocity < 0) {
            x_velocity += decelerationSpeed * delta;
            if (x_velocity > 0) {
                x_velocity = 0;
            }
        }
    }


}
