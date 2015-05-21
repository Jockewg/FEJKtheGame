package com.fejkathegame.game.arena.maps.singelplayer.tutorial01;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.arena.maps.PracticeStateHelper;
import com.fejkathegame.game.arena.physics.Physics;

import java.io.IOException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Swartt
 */
public class Tutorial01State extends State {

   private String name;

   private PracticeStateHelper stateHelper;
   private Image spacebar;
   private Image arrow;
   private Image attack;
   private Image line;

   private float offsetMaxX;
   private float offsetMaxY;

   private boolean inPit = false;

   /**
    * Constructor for ArenaState
    *
    * @param name of the stage
    */
   public Tutorial01State(String name) {
      this.name = name;
   }

   @Override
   public int getID() {
      return Main.TUTORIAL01;
   }

   @Override
   public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

      player = null;
      try {
         player = new com.fejkathegame.game.entities.Character(50, 500);
      }
      catch (IOException e) {
         e.printStackTrace();
      }

      level = new Tutorial01(name, player);

      setCameraBoundaries();

      spacebar = new Image("data/img/levelart/spacebar.png");

      arrow = new Image("data/img/levelart/arrow.png");

      attack = new Image("data/img/levelart/attac.png");

      line = new Image("data/img/levelart/line.png");

      physics = new Physics();

      camera = new PracticeCamera(offsetMaxX, offsetMaxY);

      stateHelper = new PracticeStateHelper(this);
   }

   public void setCameraBoundaries() {
      offsetMaxX = level.map.getWidth() * 25 - 450;
      offsetMaxY = level.map.getHeight() * 25 - 325;
   }

   public void checkIfInPit() {
      int tileId = level.map.getTileId((int) (player.getX()) / 25, (int) (player.getY() + 30) / 25, 0);
      String property = level.map.getTileProperty(tileId, "tileType", "pit");

      if ("pit".equals(property)) {
         inPit = true;
      }
      else {
         inPit = false;
      }
   }

   public void resetMap() {
      level.players.get(0).setX(50);
      level.players.get(0).setY(500);
   }

   @Override
   public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
       g.setAntiAlias(false);
       g.scale(Main.SCALE, Main.SCALE);
       g.translate(-camera.getCamX(), -camera.getCamY());
       level.render();
       
       spacebar.draw(220, 510);
       arrow.draw(300, 500);

       spacebar.draw(700, 510);
       arrow.draw(780, 500);
       spacebar.draw(950, 510);
       arrow.draw(1050, 500);

       spacebar.draw(1500, 510);
       arrow.draw(1580, 500);
       spacebar.draw(1750, 510);
       arrow.draw(1830, 500);

       attack.draw(1960, 510);
       line.draw(2050, 440);

       spacebar.draw(2400, 510);
       attack.draw(2400, 450);
       line.draw(2425, 400);

       spacebar.draw(730, 250);
       spacebar.draw(650, 200);
       if (paused) {
           stateHelper.drawPauseMenu(g);
       }
       level.helper.updateText(camera.getCamX(), camera.getCamY());
       g.translate(-camera.getCamX(), camera.getCamY());
       g.resetTransform();
   }

   @Override
   public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
      checkIfInPit();
      if (inPit) {
         resetMap();
      }
      stateHelper.update(gc, sbg, i, 50, 500);
   }
}
