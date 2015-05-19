/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena;


import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.entities.Character;
import org.newdawn.slick.state.BasicGameState;

/**
 *
 * @author Swartt
 */
public abstract class PracticeState extends BasicGameState {
   
   public PracticeLevel arena;
   public PracticeLevel getLevel() {return arena;}
   
   public boolean paused;
   public boolean isPaused() {return paused;}
   public void setPaused(boolean newState) {paused = newState;}
   
   public boolean isCameraAnimationRunning;
   public boolean isCameraAnimationRunning(){return isCameraAnimationRunning;}
   public void setCameraAnimationRunning(boolean newState){isCameraAnimationRunning = newState;}
   
   public float scale;
   public void setScale(float newScale) {scale = newScale;}
   
   public Character player;
   
   public PracticeCamera camera;


}
