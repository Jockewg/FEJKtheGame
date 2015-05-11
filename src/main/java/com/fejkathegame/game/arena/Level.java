package com.fejkathegame.game.arena;

import com.fejkathegame.game.arena.maps.MapHelper;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.arena.tiles.AirTile;
import com.fejkathegame.game.arena.tiles.SolidTile;
import com.fejkathegame.game.arena.tiles.Tile;

import java.util.ArrayList;

import com.fejkathegame.game.entities.PracticeTarget;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Swartt
 */
public abstract class Level {

    private TiledMap map;

    private Tile[][] tiles;

    private ArrayList<LevelObject> players;

    public abstract void loadTileMap();

    public abstract void addPlayer(LevelObject p);

    public abstract ArrayList<LevelObject> getPlayers();

    public abstract ArrayList<PracticeTarget> getTargets();

    public abstract Tile[][] getTiles();

    public abstract MapHelper getMapHelper();

    public abstract TiledMap getMap();
}
