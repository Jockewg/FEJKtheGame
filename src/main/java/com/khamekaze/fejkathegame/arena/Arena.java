package com.khamekaze.fejkathegame.arena;

import com.khamekaze.fejkathegame.tiles.AirTile;
import com.khamekaze.fejkathegame.tiles.SolidTile;
import com.khamekaze.fejkathegame.tiles.Tile;
import java.util.ArrayList;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Arena {
    
    private TiledMap map;
    
    private Tile[][] tiles;
    
    private ArrayList<Player> players;
    
    public Arena(String name) throws SlickException {
        map = new TiledMap("data/levels/" + name + ".tmx", "data/img");
        players = new ArrayList<Player>();
        
        loadTileMap();
    }
    
    public void loadTileMap() {
        tiles = new Tile[map.getWidth()][map.getHeight()];
        
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
            }
        }
    }
    
    public void addPlayer(Player p) {
        players.add(p);
    }
    
    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public Tile[][] getTiles() {
        return tiles;
    }
    
    public void render() {
        map.render(0, 0);
        
        for(Player p : players) {
            p.render();
        }
    }
}
