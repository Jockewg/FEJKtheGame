/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena;

import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.arena.tiles.AirTile;
import com.fejkathegame.game.arena.tiles.SolidTile;
import com.fejkathegame.game.arena.tiles.Tile;
import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Swartt
 */
public abstract class Level {

   private TiledMap map;

   private Tile[][] tiles;

   private ArrayList<LevelObject> players;

   public abstract void loadTileMap();

   public abstract void addPlayer(LevelObject p);

   public abstract ArrayList<LevelObject> getPlayers();

   public abstract Tile[][] getTiles();

 
}
