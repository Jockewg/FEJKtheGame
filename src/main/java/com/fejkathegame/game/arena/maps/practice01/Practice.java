/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena.maps.practice01;

import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.arena.maps.MapHelper;
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
public class Practice extends Level {

    private TiledMap map;

    private Tile[][] tiles;

    private ArrayList<LevelObject> players;
    private ArrayList<PracticeTarget> targets;

    MapHelper helper;
    PracticeTimer timer;


    /**
     * Constructor for Arena, creates the playingfield and adds all players to
     * the field
     *
     * @param levelObject
     * @throws SlickException
     */
    public Practice(String name, LevelObject levelObject) throws SlickException {
        map = new TiledMap("src/main/resources/data/levels/" + name + ".tmx", "src/main/resources/data/img");
        players = new ArrayList<>();
        targets = new ArrayList<>();

        addPlayer(levelObject);


        loadTileMap();

        timer = new PracticeTimer();
        timer.startTimer();

        helper = new MapHelper(timer, targets);


        PracticeTarget movableTarget = targets.get(15);
        float movableTargetStartingPos = movableTarget.getY();
        helper.moveTargetConstructor(movableTarget, movableTargetStartingPos, 1.0f);

    }

    /**
     * populates the arena with tiles
     *
     * @throws org.newdawn.slick.SlickException
     */
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
                    Logger.getLogger(Practice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Practice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    public ArrayList<PracticeTarget> getTargets() {
        return targets;
    }


    /**
     * @return a multidimensional array of tiles
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Renders the arena
     *
     * @throws SlickException
     */
    public void render() throws SlickException {
        map.render(0, 0, 0, 0, 100, 40);


        for (LevelObject p : players) {
            p.render();
        }

        for (LevelObject t : targets) {
            t.render();
        }
    }

}
