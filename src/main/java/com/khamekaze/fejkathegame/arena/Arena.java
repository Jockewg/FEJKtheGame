package com.khamekaze.fejkathegame.arena;

import com.khamekaze.fejkathegame.tiles.AirTile;
import com.khamekaze.fejkathegame.tiles.SolidTile;
import com.khamekaze.fejkathegame.tiles.Tile;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

public class Arena {
    
    private TiledMap map;
    
    private Tile[][] tiles;
    
    private ArrayList<LevelObject> players;
    
    public Arena(String name, LevelObject levelObject) throws SlickException {
        map = new TiledMap("data/levels/" + name + ".tmx", "data/img");
        players = new ArrayList<LevelObject>();
        
        addPlayer(levelObject);
        
        loadTileMap();
    }
    
    public void loadTileMap() {
        tiles = new Tile[map.getWidth() + 1][map.getHeight() + 1];
        
        int layerIndex = map.getLayerIndex("CollisionLayer");
        
        if(layerIndex == -1) {
            System.err.println("Map does not conatin CollisionLayer");
            System.exit(0);
        }
        
        for(int x = 0; x < map.getWidth(); x++) {
            for(int y = 0; y < map.getHeight(); y++) {
                
                int tileID = map.getTileId(x, y, layerIndex);
                
                Tile tile = null;
                
                switch(map.getTileProperty(tileID, "tileType", "solid")) {
                    case "air":
                        tile = new AirTile(x, y);
                        break;
                    default:
                        tile = new SolidTile(x, y);
                        break;
                }
                tiles[x][y] = tile;
                System.out.println(tile.toString());
            }
        }
    }
    
    public void addPlayer(LevelObject p) {
        players.add(p);
    }
    
    public ArrayList<LevelObject> getPlayers() {
        return players;
    }
    
    public Tile[][] getTiles() {
        return tiles;
    }
    
    public void render() throws SlickException {
        map.render(0, 0, 0, 0, 36, 20);
        
        for(LevelObject p : players) {
            p.render();
        }
    }
}
