package com.fejkathegame.game.tiles;

import com.fejkathegame.game.collision.BoundingShape;

public class Tile {
    
    protected BoundingShape boundingShape;
    
    protected int x, y;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        boundingShape = null;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public BoundingShape getBoundingShape() {
        return boundingShape;
    }

}
