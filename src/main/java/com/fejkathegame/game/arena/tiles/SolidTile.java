package com.fejkathegame.game.arena.tiles;

import com.fejkathegame.game.arena.collision.AABoundingRect;

public class SolidTile extends Tile {

    public SolidTile(int x, int y) {
        super(x, y);
        boundingShape = new AABoundingRect(x * 25, y * 25, 25, 25);
    }

}
