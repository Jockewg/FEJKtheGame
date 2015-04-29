package com.khamekaze.fejkathegame.arena;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player {
    
    protected float x, y;
    protected Image sprite;
    
    public Player(float x, float y) throws SlickException {
        this.x = x;
        this.y = y;
        
        sprite = new Image("data/img/placeholder.png");
    }
    
    public void moveLeft(int delta) {
        x = x - (0.15f * delta);
    }
    
    public void moveRight(int delta) {
        x = x + (0.15f * delta);
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
