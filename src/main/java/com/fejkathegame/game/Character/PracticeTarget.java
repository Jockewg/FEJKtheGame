/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.Character;

import com.fejkathegame.game.arena.LevelObject;
import java.io.IOException;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Swartt
 */
public class PracticeTarget  extends LevelObject{
   HealthSystem healthSystem;
   
   
   public PracticeTarget(float x, float y) throws SlickException, IOException {
      super(x,y);
      healthSystem = new HealthSystem(this);
   }
   
}
