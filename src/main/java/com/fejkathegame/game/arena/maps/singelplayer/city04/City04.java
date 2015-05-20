package com.fejkathegame.game.arena.maps.singelplayer.city04;

import com.fejkathegame.game.arena.PracticeLevel;
import com.fejkathegame.game.arena.maps.PracticeLevelHelper;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.game.timer.PracticeTimer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

/**
 * @author Swartt
 */
public class City04 extends PracticeLevel {

    PracticeLevelHelper helper;


    /**
     * Constructor for Arena, creates the playingfield and adds all players to
     * the field
     *
     * @param levelObject
     * @throws SlickException
     */
    public City04(String name, LevelObject levelObject) throws SlickException {
        map = new TiledMap("src/main/resources/data/levels/singelplayer/" + name + ".tmx", "src/main/resources/data/img");
        players = new ArrayList<>();
        targets = new ArrayList<>();
        targetTiles = new ArrayList<>();
        addPlayer(levelObject);


        timer = new PracticeTimer();
        timer.startTimer();

        helper = new PracticeLevelHelper(this);
        helper.loadTileMap();


        PracticeTarget movableTarget = targets.get(15);
        float movableTargetStartingPos = movableTarget.getY();
        helper.moveTargetConstructor(movableTarget, movableTargetStartingPos, 1.0f);

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


    @Override
    public TiledMap getMap() {
        return null;
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

    /**
     * Renders the level
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

    @Override
    public ArrayList<Tile> getTargetTiles() {
        return targetTiles;
    }


}
