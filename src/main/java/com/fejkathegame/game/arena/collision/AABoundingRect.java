package com.fejkathegame.game.arena.collision;

import com.fejkathegame.game.arena.tiles.Tile;

import java.util.ArrayList;

public class AABoundingRect extends BoundingShape {
    
    public float x, y, width, height;
    
    public AABoundingRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean checkCollision(AABoundingRect box) {
        return !(box.x > this.x+width ||
                box.x+box.width < this.x ||
                box.y > this.y + height ||
                box.y + box.height < this.y);
    }

    @Override
    public void updatePosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    @Override
    public void movePosition(float x, float y) {
        this.x += x;
        this.y += y;
    }

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
