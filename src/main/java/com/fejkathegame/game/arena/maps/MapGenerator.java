package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.arena.tiles.AirTile;
import com.fejkathegame.game.arena.tiles.SolidTile;
import com.fejkathegame.game.arena.tiles.TargetTile;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.entities.PracticeTarget;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Swartt on 2015-05-11.
 */
public class MapGenerator extends Level {
    Tile[][] tiles;

    TiledMap map;


    ArrayList<PracticeTarget> targets;

    public MapGenerator (TiledMap map) {
        this.map = map;
    }


    @Override
    public void loadTileMap() {
        tiles = new Tile[map.getWidth() + 1][map.getHeight() + 1];

        int layerIndex = map.getLayerIndex("CollisionLayer");

        if (layerIndex == -1) {
            System.err.println("Map does not conatin CollisionLayer");
            System.exit(0);
        }

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {

                try {
                    int tileID = map.getTileId(x, y, layerIndex);

                    Tile tile = null;
                    PracticeTarget target = null;

                    switch (map.getTileProperty(tileID, "tileType", "solid")) {
                        case "air":
                            tile = new AirTile(x, y);
                            break;
                        case "target":
                            tile = new TargetTile(x, y);
                            target = new PracticeTarget(tile.getX() * 25 - 1, tile.getY() * 25 - 1);
                            targets.add(target);
                            break;

                        default:
                            tile = new SolidTile(x, y);
                            break;
                    }
                    tiles[x][y] = tile;
                } catch (SlickException ex) {
                    Logger.getLogger(MapGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MapGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void addPlayer(LevelObject p) {

    }

    @Override
    public ArrayList<LevelObject> getPlayers() {
        return null;
    }

    @Override
    public Tile[][] getTiles() {
        return new Tile[0][];
    }
}
