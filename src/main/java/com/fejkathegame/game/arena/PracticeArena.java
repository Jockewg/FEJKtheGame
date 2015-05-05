/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena;

import com.fejkathegame.game.Character.PracticeTarget;
import com.fejkathegame.game.tiles.AirTile;
import com.fejkathegame.game.tiles.SolidTile;
import com.fejkathegame.game.tiles.Tile;
import java.io.IOException;
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
   public void addPracticeTargets(LevelObject t) throws SlickException, IOException {
      PracticeTarget target1 = new PracticeTarget(160, 200);
      PracticeTarget target2 = new PracticeTarget(100, 200);
      PracticeTarget target3 = new PracticeTarget(200, 200);
      PracticeTarget target4 = new PracticeTarget(300, 200);
      PracticeTarget target5 = new PracticeTarget(500, 200);
//      PracticeTarget target6 = new PracticeTarget(160, 200);
//      PracticeTarget target7 = new PracticeTarget(160, 200);
//      PracticeTarget target8 = new PracticeTarget(160, 200);
   }

   /**
    * returns the player array
    *
    * @return
    */
   public ArrayList<LevelObject> getPlayers() {
      return players;
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
     
   }
}
