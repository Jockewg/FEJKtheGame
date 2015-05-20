package com.fejkathegame.game.arena.maps.multiplayer.versus01;

import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.arena.tiles.AirTile;
import com.fejkathegame.game.arena.tiles.SolidTile;
import com.fejkathegame.game.arena.tiles.Tile;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public class Versus extends Level {
    
    private TiledMap map;
    
    private Image[] crankAnim;
    
    private Animation animation;
    
    private ArrayList<Tile> targetTiles;
    /**
     * Constructor for Arena, creates the playingfield and adds all players to the field
     * @param name name of the file containing the tilemap
     * @param levelObject a player to be added to the level
     * @throws SlickException
     */
    public Versus(String name, LevelObject levelObject) throws SlickException {
        map = new TiledMap("src/main/resources/data/levels/multiplayer/" + name + ".tmx", "src/main/resources/data/img");
        players = new ArrayList<>();
        
        
        addPlayer(levelObject);
        
        loadTileMap();
        
        loadCrankAnim();
        animation = new Animation(crankAnim, 30);


    }
    
    public void loadCrankAnim() throws SlickException {
        crankAnim = new Image[12];
        
        crankAnim[0] = new Image("src/main/resources/data/img/animations/crankAnim1.png");
        crankAnim[1] = new Image("src/main/resources/data/img/animations/crankAnim2.png");
        crankAnim[2] = new Image("src/main/resources/data/img/animations/crankAnim3.png");
        crankAnim[3] = new Image("src/main/resources/data/img/animations/crankAnim4.png");
        crankAnim[4] = new Image("src/main/resources/data/img/animations/crankAnim5.png");
        crankAnim[5] = new Image("src/main/resources/data/img/animations/crankAnim6.png");
        crankAnim[6] = new Image("src/main/resources/data/img/animations/crankAnim7.png");
        crankAnim[7] = new Image("src/main/resources/data/img/animations/crankAnim8.png");
        crankAnim[8] = new Image("src/main/resources/data/img/animations/crankAnim9.png");
        crankAnim[9] = new Image("src/main/resources/data/img/animations/crankAnim10.png");
        crankAnim[10] = new Image("src/main/resources/data/img/animations/crankAnim11.png");
        crankAnim[11] = new Image("src/main/resources/data/img/animations/crankAnim12.png");
    }
    
    public float getArenaWidth() {
        return map.getWidth();
    }
    
    public float getArenaHeight() {
        return map.getHeight();
    }

    /**
     * populates the level with tiles
     */
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
            }
        }
    }

    /**
     * adds a player to the player array
     * @param p
     */
    @Override
    public void addPlayer(LevelObject p) {
        players.add(p);
    }

    /**
     * Renders the level
     * @throws SlickException
     */
    @Override
    public void render() throws SlickException {
        map.render(0, 0, 0, 0, 36, 20);
        
        for(LevelObject p : players) {
            p.render();
        }
    }
    
    public Animation getAnimation() {
        return animation;
    }

}
