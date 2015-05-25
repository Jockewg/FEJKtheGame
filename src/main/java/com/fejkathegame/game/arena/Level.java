package com.fejkathegame.game.arena;

import com.fejkathegame.game.arena.maps.PracticeLevelHelper;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.arena.tiles.Tile;

import java.util.ArrayList;

import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.game.timer.Timer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Swartt
 */
public abstract class Level {

    public PracticeLevelHelper helper;

    public TiledMap map;

    public Tile[][] tiles;

    public ArrayList<LevelObject> players;

    public ArrayList<PracticeTarget> targets;

    public ArrayList<Tile> targetTiles;

    public Timer timer;

    public abstract void addPlayer(LevelObject p);
    
    public abstract void render() throws SlickException;

    public void initMovableTarget() {}

}
