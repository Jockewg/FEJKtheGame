package com.fejkathegame.game.arena;

import com.fejkathegame.game.arena.maps.PracticeLevelHelper;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.arena.tiles.Tile;

import java.util.ArrayList;

import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.game.timer.PracticeTimer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Swartt
 */
public abstract class PracticeLevel {

    private TiledMap map;

    private Tile[][] tiles;

    private ArrayList<LevelObject> players;

    public abstract void loadTileMap();

    public abstract void addPlayer(LevelObject p);

    public abstract ArrayList<LevelObject> getPlayers();

    public abstract ArrayList<PracticeTarget> getTargets();
    
    public abstract ArrayList<Tile> getTargetTiles();

    public abstract Tile[][] getTiles();

    public abstract PracticeLevelHelper getMapHelper();

    public abstract TiledMap getMap();
    
    public abstract void render() throws SlickException;
    
    public abstract PracticeTimer getTimer();
}
