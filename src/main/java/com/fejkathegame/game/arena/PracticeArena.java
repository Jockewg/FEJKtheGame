/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena;

import com.fejkathegame.game.tiles.AirTile;
import com.fejkathegame.game.tiles.SolidTile;
import com.fejkathegame.game.tiles.Tile;
import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Swartt
 */
public class PracticeArena {

   private TiledMap map;

   private Tile[][] tiles;

   private ArrayList<LevelObject> players;
   
   private ArrayList<LevelObject> targets;

   /**
    * Constructor for Arena, creates the playingfield and adds all players to
    * the field
    *
    * @param name
    * @param levelObject
    * @throws SlickException
    */
   public PracticeArena(String name, LevelObject levelObject) throws SlickException {
      map = new TiledMap("data/levels/" + name + ".tmx", "data/img");
      players = new ArrayList<LevelObject>();

      addPlayer(levelObject);

      loadTileMap();
   }

   /**
    * populates the arena with tiles
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

            int tileID = map.getTileId(x, y, layerIndex);

            Tile tile = null;

            switch (map.getTileProperty(tileID, "tileType", "solid")) {
               case "air":
                  tile = new AirTile(x, y);
                  break;
               default:
                  tile = new SolidTile(x, y);
                  break;
            }
            tiles[x][y] = tile;
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
   public void addPracticeTargets(LevelObject t) {
      targets.add(t);
   }

   /**
    * returns the player array
    *
    * @return
    */
   public ArrayList<LevelObject> getPlayers() {
      return players;
   }
   
   public ArrayList<LevelObject> getPracticeTargets() {
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
      map.render(0, 0, 0, 0, 36, 20);

      for (LevelObject p : players) {
         p.render();
      }
      for (LevelObject t : targets) {
         t.render();
      }
   }
}
