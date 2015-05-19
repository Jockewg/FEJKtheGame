/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena.maps.singelplayer.bigblue03;

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
public class BigBlue02 extends PracticeLevel {

    private TiledMap map;

    private Tile[][] tiles;

    private ArrayList<LevelObject> players;
    private ArrayList<PracticeTarget> targets;
    private ArrayList<Tile> targetTiles;

    PracticeLevelHelper helper;

    PracticeTimer timer;

    /**
     * Constructor for Arena, creates the playingfield and adds all players to
     * the field
     *
     * @param levelObject
     * @throws org.newdawn.slick.SlickException
     */
    public BigBlue02(String name, LevelObject levelObject) throws SlickException {
        map = new TiledMap("src/main/resources/data/levels/singelplayer/" + name + ".tmx", "src/main/resources/data/img");
        players = new ArrayList<>();
        targets = new ArrayList<>();
        targetTiles = new ArrayList<>();
        addPlayer(levelObject);
        loadTileMap();
        timer = new PracticeTimer();
        timer.startTimer();


        helper = new PracticeLevelHelper(timer, targets);
    }

    /**
     * populates the arena with tiles
     *
     * @throws org.newdawn.slick.SlickException
     */
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
                            targetTiles.add(tile);
                            break;

                        default:
                            tile = new SolidTile(x, y);
                            break;
                    }
                    tiles[x][y] = tile;
                } catch (SlickException ex) {
                    Logger.getLogger(BigBlue02.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BigBlue02.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
     * @return the player array which includes all player objects
     */
    public ArrayList<LevelObject> getPlayers() {
        return players;
    }

    /**
     * @return the player array which includes all {@code targetPractice} objects
     */
    @Override
    public ArrayList<PracticeTarget> getTargets() {
        return targets;
    }

    public ArrayList<Tile> getTargetTiles() {
        return targetTiles;
    }

    /**
     * @return a multidimensional array of tiles
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    @Override
    public PracticeLevelHelper getMapHelper() {
        return helper;
    }


    public TiledMap getMap() {
        return map;
    }

    /**
     * Renders the arena
     *
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render() throws SlickException {
        map.render(0, 0, 0, 0, 150, 50);

        for (LevelObject p : players) {
            p.render();
        }

        for (LevelObject t : targets) {
            t.render();
        }
    }

   @Override
   public PracticeTimer getTimer() {
      return timer;
   }

}
