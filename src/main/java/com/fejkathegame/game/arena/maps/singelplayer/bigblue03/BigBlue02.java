package com.fejkathegame.game.arena.maps.singelplayer.bigblue03;

import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.arena.maps.PracticeLevelHelper;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.timer.PracticeTimer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

/**
 * @author Swartt
 */
public class BigBlue02 extends Level {

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