/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena.maps.tutorial04;

import com.fejkathegame.game.arena.PracticeLevel;
import com.fejkathegame.game.arena.maps.PracticeLevelHelper;
import com.fejkathegame.game.arena.maps.practice03.BigBlue02;
import com.fejkathegame.game.arena.tiles.AirTile;
import com.fejkathegame.game.arena.tiles.SolidTile;
import com.fejkathegame.game.arena.tiles.TargetTile;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.game.timer.PracticeTimer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Swartt
 */
public class Tutorial03 extends PracticeLevel {

   private TiledMap map;

   private Tile[][] tiles;

   private ArrayList<LevelObject> players;

   private ArrayList<PracticeTarget> targets;

   PracticeLevelHelper helper;

   PracticeTimer timer;

   public Tutorial03(String name, LevelObject levelObject) throws SlickException {
      map = new TiledMap("src/main/resources/data/levels/" + name + ".tmx", "src/main/resources/data/img");

      players = new ArrayList<>();
      targets = new ArrayList<>();
      addPlayer(levelObject);
      loadTileMap();

      timer = new PracticeTimer();
      timer.startTimer();

      helper = new PracticeLevelHelper(timer, targets);
   }

   @Override
   public void loadTileMap() {
      tiles = new Tile[map.getWidth() + 1][map.getHeight() + 1];

      int collisionLayer = map.getLayerIndex("CollisionLayer");
      int noLayer = -1;

      if (collisionLayer == noLayer) {
         System.err.println("Map does not contain CollisionLayer");
         System.exit(0);
      }

      for (int x = 0; x < map.getWidth(); x++) {
         for (int y = 0; y < map.getHeight(); y++) {

            try {
               int tileID = map.getTileId(x, y, collisionLayer);

               Tile tile = null;
               PracticeTarget target = null;

               switch (map.getTileProperty(tileID, "tileType", "solid")) {
                  case "air":
                     tile = new AirTile(x, y);
                     break;

                  case "target":
                     tile = new TargetTile(x, y);
                     target = new PracticeTarget(x * 25, y * 25);
                     targets.add(target);
                     break;

                  default:
                     tile = new SolidTile(x, y);
                     break;
               }
               tiles[x][y] = tile;
            }
            catch (SlickException ex) {
               Logger.getLogger(BigBlue02.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            catch (IOException ex) {
               Logger.getLogger(BigBlue02.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

         }
      }
   }

   @Override
   public void addPlayer(LevelObject p) {
      players.add(p);
   }

   @Override
   public ArrayList<LevelObject> getPlayers() {
      return players;
   }

   @Override
   public ArrayList<PracticeTarget> getTargets() {
      return targets;
   }

   @Override
   public Tile[][] getTiles() {
      return tiles;
   }

   @Override
   public PracticeLevelHelper getMapHelper() {
      return helper;
   }

   @Override
   public TiledMap getMap() {
         return map;
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
