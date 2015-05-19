package com.fejkathegame.game.arena.maps.singelplayer.tower02;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.PracticeState;
import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.arena.maps.PracticeStateHelper;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.entities.logic.MovementSystem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 *
 * @author Swartt
 */
public class Tower02State extends PracticeState {

   private Tower02 arena;
   private String name;
   private MovementSystem movementSystem;
   private Physics physics;
   private com.fejkathegame.game.entities.Character obj;
   private PracticeStateHelper helper;
   private PracticeCamera camera;

   private float offsetMaxX = 450;
   private float offsetMaxY = 2250;

   private boolean isCameraAnimationRunning = true;
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

      obj = null;
      try {
         obj = new com.fejkathegame.game.entities.Character(450, 2424);
      }
      catch (IOException e) {
         e.printStackTrace();
      }

      arena = new Tower02(name, obj);

      movementSystem = new MovementSystem(obj);

      physics = new Physics();

      camera = new PracticeCamera(offsetMaxX, offsetMaxY);

      helper = new PracticeStateHelper(this);

   }

   public void cameraAnimation() {
      cameraMotionY += arena.timer.getCurrentCountdownTimeInReverseIncrement();
      camera.setCamX(0);
      camera.setCamY(cameraMotionY);
      if (cameraMotionY >= offsetMaxY - 250) {
         isCameraAnimationRunning = false;
      }
   }

   @Override
   public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
      helper.render(gc, sbg, g);
   }

   @Override
   public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
      helper.update(gc, sbg, i, 450, 2424);
   }
}
