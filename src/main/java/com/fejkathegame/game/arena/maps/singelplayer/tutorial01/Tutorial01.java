package com.fejkathegame.game.arena.maps.singelplayer.tutorial01;

import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.arena.maps.PracticeLevelHelper;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.timer.Timer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

/**
 * @author Swartt
 */
public class Tutorial01 extends Level {

    public Tutorial01(String name, LevelObject levelObject) throws SlickException {
        map = new TiledMap("src/main/resources/data/levels/singelplayer/" + name + ".tmx", "src/main/resources/data/img");

        players = new ArrayList<>();
        targets = new ArrayList<>();
        targetTiles = new ArrayList<>();
        addPlayer(levelObject);

        timer = new Timer();
        timer.startTimer();

        helper = new PracticeLevelHelper(this);
        helper.loadTileMap();
    }


    @Override
    public void addPlayer(LevelObject p) {
        players.add(p);
    }

    public void render() throws SlickException {
        map.render(0, 0, 0, 0, 100, 30);

        for (LevelObject p : players) {
            p.render();
        }

        for (LevelObject t : targets) {
            t.render();
        }
    }

}
