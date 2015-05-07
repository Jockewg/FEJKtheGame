/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena.practice;

import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.arena.tiles.AirTile;
import com.fejkathegame.game.arena.tiles.SolidTile;
import com.fejkathegame.game.arena.tiles.TargetTile;
import com.fejkathegame.game.arena.tiles.Tile;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Swartt
 */
public class Practice extends Level {

    private TiledMap map;

    private Tile[][] tiles;

    private ArrayList<LevelObject> players;
    private ArrayList<PracticeTarget> targets;

    private PracticeTarget movableTarget;
    float movableTargetStartingPos;

    Font font;
    TrueTypeFont ttf;
    String score;

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
        addPracticeTargets();

        loadTileMap();
        font = new Font("Verdana", Font.BOLD, 20);
        ttf = new TrueTypeFont(font, true);

        score = "Targets Left: " + String.valueOf(targets.size());

        movableTarget = targets.get(15);
        movableTargetStartingPos = movableTarget.getY();
    }

    /**
     * populates the arena with tiles
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
     * Creates and adds Practice targets to the {@code targets} array
     */
    public void addPracticeTargets() {
        

//        try {
//            PracticeTarget target1 = new PracticeTarget(60, 70);
//            targets.add(target1);
//            PracticeTarget target2 = new PracticeTarget(100, 300);
//            targets.add(target2);
//            PracticeTarget target3 = new PracticeTarget(200, 50);
//            targets.add(target3);
//            PracticeTarget target4 = new PracticeTarget(400, 30);
//            targets.add(target4);
//            PracticeTarget target5 = new PracticeTarget(600, 50);
//            targets.add(target5);
//            PracticeTarget target6 = new PracticeTarget(400, 400);
//            targets.add(target6);
//            PracticeTarget target7 = new PracticeTarget(400, 200);
//            targets.add(target7);
//            PracticeTarget target8 = new PracticeTarget(750, 80);
//            targets.add(target8);
//            PracticeTarget target9 = new PracticeTarget(800, 435);
//            targets.add(target9);
//            PracticeTarget target10 = new PracticeTarget(160, 200);
//            targets.add(target10);
//        } catch (SlickException ex) {
//            Logger.getLogger(Practice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Practice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
    }

    /**
     * Updates the scoreboard
     */
    public void updateScore() {
        if (targets.size() > 0) {
            score = "Targets Left: " + String.valueOf(targets.size());
        } else {
            score = "You are a winrar!";
        }
    }
//  doesnt work
    public void moveTarget() {


    if (movableTarget.getY() <= movableTargetStartingPos) {
        movableTarget.setY(movableTargetStartingPos++);
    }

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

        //for debugging
        /*for (int i = 0; i < targets.size(); i++) {
            ttf.drawString(targets.get(i).getX(), targets.get(i).getY(), String.valueOf(i));
        }*/

    }

    public void updateText(float x, float y) {
        ttf.drawString(x, y, score, Color.green);
    }
}
