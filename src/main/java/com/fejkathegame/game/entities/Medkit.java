package com.fejkathegame.game.entities;

import com.fejkathegame.game.entities.logic.HealthSystem;
import com.fejkathegame.game.timer.Timer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Swartt on 2015-05-24.
 */
public class Medkit extends LevelObject {

    HealthSystem healthSystem;
    Shape hitBox;
    Timer timer;
    boolean isAlive;
    boolean needNewNumber;


    /**
     * Constructor for a {@code LevelObject}, creates a new level entity with standard values
     *
     * @param x coordinate for spawing the object
     * @param y coordinate for spawing the object
     * @throws org.newdawn.slick.SlickException
     */
    public Medkit(float x, float y) throws SlickException, IOException {
        super(x, y);
        healthSystem = new HealthSystem(this);
        health = 1;
        sprite = new Image("src/main/resources/data/img/target/target.png");
        hitBox = new Rectangle(x, y, 30, 30);
        setAlive(false);
        timer = new Timer();
        needNewNumber = true;
        getRandomPosition();
    }

    public void render(float x, float y) {
        sprite.draw(x, y);
        updateHitBox();

    }

    public void preSpawnDisplay() {
        sprite.draw(x, y, new Color(1, 1, 1, 0.5f));
    }

    public void run() {
        timer.startTimer();
        if (NeedNewNumber()) {
            getRandomPosition();
        }
        if (isAlive()) {
            render(x, y);
        }
        if (timer.getTimerDuration() >= 5) {
            preSpawnDisplay();
        }
        if (timer.getTimerDuration() >= 10) {
            setAlive(true);
        }


    }

    public void getRandomPosition() {
        int minX = 30;
        int maxX = 870;
        Random randX = new Random();
        int randomX = randX.nextInt((maxX - minX) + 1) + minX;
        int minY = 30;
        int maxY = 300;
        Random randY = new Random();
        int randomY = randY.nextInt((maxY - minY) + 1) + minY;
        x = randomX;
        y = randomY;
        setNeedNewNumber(false);
    }

    public void updateHitBox() {
        hitBox.setX(x);
        hitBox.setY(y);
    }

    @Override
    public HealthSystem getHealthSystem() {
        return healthSystem;
    }

    @Override
    public void setHealthSystem(HealthSystem healthSystem) {
        this.healthSystem = healthSystem;
    }

    @Override
    public Shape getHitBox() {
        return hitBox;
    }

    @Override
    public void setHitBox(Shape hitBox) {
        this.hitBox = hitBox;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean NeedNewNumber() {
        return needNewNumber;
    }

    public void setNeedNewNumber(boolean isSpawned) {
        this.needNewNumber = isSpawned;
    }
}
