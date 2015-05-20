package com.fejkathegame.game.arena.maps.singelplayer.tower02;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.arena.maps.PracticeStateHelper;
import com.fejkathegame.game.arena.physics.Physics;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 *
 * @author Swartt
 */
public class Tower02State extends State {

   private String name;
   private PracticeStateHelper helper;

   private float offsetMaxX = 450;
   private float offsetMaxY = 2250;


   private float cameraMotionY = 0;

   /**
    * Constructor for ArenaState
    *
    * @param name of the stage
    */
   public Tower02State(String name) {
      this.name = name;
   }

   @Override
   public int getID() {
      return Main.TOWER02;
   }

   @Override
   public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

      player = null;
      try {
         player = new com.fejkathegame.game.entities.Character(450, 2424);
      }
      catch (IOException e) {
         e.printStackTrace();
      }

      level = new Tower02(name, player);


      physics = new Physics();

      camera = new PracticeCamera(offsetMaxX, offsetMaxY);

      helper = new PracticeStateHelper(this);
       setCameraAnimationRunning(true);
   }

   public void cameraAnimation() {
      cameraMotionY += level.timer.getCurrentCountdownTimeInReverseIncrement();
      camera.setCamX(0);
      camera.setCamY(cameraMotionY);
      if (cameraMotionY >= offsetMaxY - 250) {
         setCameraAnimationRunning(false);
      }
   }

   @Override
   public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
      helper.render(gc, sbg, g);
   }

   @Override
   public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
      helper.update(gc, sbg, i, 450, 2424);
       if (isCameraAnimationRunning) {
           cameraAnimation();
       }
   }
}
