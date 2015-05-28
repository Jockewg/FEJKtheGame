package com.fejkathegame.game.arena.collision;

import com.fejkathegame.game.arena.tiles.Tile;

import java.util.ArrayList;


/**
 * This class is used to create hitboxes for objects in the game.
 * 
 * @author Kim Göransson
 */
public class AABoundingRect extends BoundingShape {
    
    public float x, y, width, height;
    
    public AABoundingRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Checks if a hitbox is colliding with another hitbox
     * 
     * @param box - the hitbox to check collision against
     * @return 
     */
    
    @Override
    public boolean checkCollision(AABoundingRect box) {
        return !(box.x > this.x+width ||
                box.x+box.width < this.x ||
                box.y > this.y + height ||
                box.y + box.height < this.y);
    }

    /**
     * Updates the position of the hitbox
     * @param newX
     * @param newY
     */
    @Override
    public void updatePosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    /**
     * Moves the position of the hitbox
     * @param x
     * @param y
     */
    @Override
    public void movePosition(float x, float y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Checks the tiles that a hitbox is occupying.
     * 
     * @param tiles - The tiles that make up the level
     * @return the tiles that are occupied by a hitbox
     */
    @Override
    public ArrayList<Tile> getTilesOccupying(Tile[][] tiles) {
        ArrayList<Tile> occupiedTiles = new ArrayList<Tile>();
        
        for(int i = (int) x; i <= x + width + (25 - width%25); i += 25) {
            for(int j = (int) y; j <= y + height + (25 - height%25); j += 25) {
                occupiedTiles.add(tiles[i/25][j/25]);
            }
        }
        return occupiedTiles;
    }

    /**
     * Checks if a hitbox is beneath a hitbox.
     * 
     * @param tiles - The tiles that make up the level
     * @return the tiles that are directly beneath the hitbox
     */
    @Override
    public ArrayList<Tile> getGroundTiles(Tile[][] tiles) {
        ArrayList<Tile> tilesUnderneath = new ArrayList<>();
        int j = (int) (y + height + 1);
        
        for(int i = (int) x; i <= x + width + (25 - width % 25); i += 25) {
            tilesUnderneath.add(tiles[i/25][j/25]);
        }
        return tilesUnderneath;
    }
}
