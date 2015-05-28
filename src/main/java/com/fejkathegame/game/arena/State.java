package com.fejkathegame.game.arena;


import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.entities.Character;
import org.newdawn.slick.state.BasicGameState;

/**
 *
 * @author Swartt
 */
public abstract class State extends BasicGameState {
   
   public Level level;
   
   public boolean paused;
   public boolean isPaused() {return paused;}
   public void setPaused(boolean newState) {paused = newState;}
   
   public boolean isCameraAnimationRunning;
   public void setCameraAnimationRunning(boolean newState){isCameraAnimationRunning = newState;}
   
   public float scale;
   public void setScale(float newScale) {scale = newScale;}
   
   public Character player;
   
   public PracticeCamera camera;
   
   public Physics physics;


}
