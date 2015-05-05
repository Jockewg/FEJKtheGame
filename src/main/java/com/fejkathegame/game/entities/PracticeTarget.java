/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.entities;

import com.fejkathegame.game.entities.logic.HealthSystem;
import java.io.IOException;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Swartt
 */
public class PracticeTarget extends LevelObject {

   private HealthSystem healthSystem;
   private int health;
   private boolean isAlive;
   private Image sprite;

   public PracticeTarget(float x, float y) throws SlickException, IOException {
      super(x, y);
      healthSystem = new HealthSystem(this);
      health = 1;
      sprite = new Image("data/img/target/target.png");
   }

   @Override
   public boolean isAlive() {
      return isAlive;
   }

   @Override
   public void setAlive(boolean alive) {
      this.isAlive = alive;
   }

   @Override
    public int getHealth() {
        return health;
    }

   @Override
    public void setHealth(int health) {
        this.health = health;
    }
    @Override
    public void render() throws SlickException {
        sprite.draw(x, y);
    } 
}
