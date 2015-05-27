package com.fejkathegame;

import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.entities.logic.HealthSystem;
import org.newdawn.slick.SlickException;

import java.io.IOException;

/**
 * Created by Swartt on 2015-05-27.
 */
public class TestObject extends LevelObject {



    /**
     * Constructor for a {@code LevelObject}, creates a new level entity with standard values
     *
     * @param x coordinate for spawing the object
     * @param y coordinate for spawing the object
     * @throws org.newdawn.slick.SlickException
     */
    public TestObject(float x, float y) throws SlickException, IOException {
        super(x, y);
        health = 5;
    }
}
