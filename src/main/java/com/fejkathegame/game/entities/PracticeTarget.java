package com.fejkathegame.game.entities;

import com.fejkathegame.game.entities.logic.HealthSystem;

import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * @author Swartt
 */
public class PracticeTarget extends LevelObject {

    private HealthSystem healthSystem;
    private int health;
    private boolean isAlive;
    private Image sprite;
    private Shape hitBox;

    /**
     * @param x the X coordinate of the object
     * @param y the Y coordinate of the object
     * @throws SlickException
     * @throws IOException
     */
    public PracticeTarget(float x, float y) throws SlickException, IOException {
        super(x, y);
        healthSystem = new HealthSystem(this);
        health = 1;
        sprite = new Image("src/main/resources/data/img/target/target.png");
        hitBox = new Rectangle(x, y, 30, 30);
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    public Shape getHitbox() {
        return hitBox;
    }

    @Override
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void render() throws SlickException {
        updateHitBox();
        sprite.draw(x, y);
    }

    public HealthSystem getHealthSystem() {
        return healthSystem;
    }

    /**
     * moves the hitbox to the position of the {@code PracticeTarget}
     */
    public void updateHitBox() {
        hitBox.setX(x);
        hitBox.setY(y);
    }
}
