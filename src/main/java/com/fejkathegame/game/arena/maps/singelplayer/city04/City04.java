package com.fejkathegame.game.arena.maps.singelplayer.city04;

import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.arena.maps.PracticeLevelHelper;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.game.timer.Timer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

/**
 * @author Swartt
 */
public class City04 extends Level {

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


        timer = new Timer();
        timer.startTimer();

        helper = new PracticeLevelHelper(this);
        helper.loadTileMap();

        initMovableTarget();
        

    }
    
    public void initMovableTarget() {
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
}
