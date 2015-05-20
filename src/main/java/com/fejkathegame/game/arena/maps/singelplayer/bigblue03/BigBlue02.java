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

    PracticeLevelHelper helper;


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

        timer = new PracticeTimer();
        timer.startTimer();


        helper = new PracticeLevelHelper(this);
        helper.loadTileMap();
    }

    /**
     * populates the level with tiles
     *
     * @throws org.newdawn.slick.SlickException
     */

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
     * Renders the level
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

}