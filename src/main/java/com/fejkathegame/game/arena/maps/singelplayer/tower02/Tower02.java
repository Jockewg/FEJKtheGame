package com.fejkathegame.game.arena.maps.singelplayer.tower02;

import com.fejkathegame.game.arena.PracticeLevel;
import com.fejkathegame.game.arena.maps.PracticeLevelHelper;
import com.fejkathegame.game.arena.tiles.AirTile;
import com.fejkathegame.game.arena.tiles.SolidTile;
import com.fejkathegame.game.arena.tiles.TargetTile;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.game.timer.PracticeTimer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Swartt
 */
public class Tower02 extends PracticeLevel {

    private TiledMap map;

    private Tile[][] tiles;

    private ArrayList<LevelObject> players;

    PracticeLevelHelper helper;


    /**
     * Constructor for Arena, creates the playingfield and adds all players to
     * the field
     *
     * @param levelObject
     * @throws org.newdawn.slick.SlickException
     */
    public Tower02(String name, LevelObject levelObject) throws SlickException {
        map = new TiledMap("src/main/resources/data/levels/singelplayer/" + name + ".tmx", "src/main/resources/data/img");
        players = new ArrayList<>();
        targets = new ArrayList<>();
        targetTiles = new ArrayList<>();
        addPlayer(levelObject);


        timer = new PracticeTimer();
        timer.startTimer();

        helper = new PracticeLevelHelper(this);
        helper.loadTileMap();
    }


    public void loadTileMap() {
        tiles = new Tile[map.getWidth() + 1][map.getHeight() + 1];

        int collisionLayer = map.getLayerIndex("CollisionLayer");
        int noLayer = -1;

        if (collisionLayer == noLayer) {
            System.err.println("Map does not contain CollisionLayer");
            System.exit(0);
        }


        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {


                try {
                    int tileID = map.getTileId(x, y, collisionLayer);

                    Tile tile = null;
                    PracticeTarget target = null;

                    switch (map.getTileProperty(tileID, "tileType", "solid")) {
                        case "air":
                            tile = new AirTile(x, y);
                            break;

                        case "target":
                            tile = new TargetTile(x, y);
                            target = new PracticeTarget(x * 25, y * 25);
                            targets.add(target);
                            break;

                        default:
                            tile = new SolidTile(x, y);
                            break;
                    }
                    tiles[x][y] = tile;
                } catch (SlickException ex) {
                    Logger.getLogger(Tower02.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Tower02.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }

            }
        }
    }

    /**
     * adds a player to the player array
     *
     * @param p
     */
    public void addPlayer(LevelObject p) {
        players.add(p);
    }

    /**
     * Renders the level
     *
     * @throws org.newdawn.slick.SlickException
     */
    public void render() throws SlickException {
        map.render(0, 0, 0, 0, 36, 100);

        for (LevelObject p : players) {
            p.render();
        }

        for (LevelObject t : targets) {
            t.render();
        }
    }
}