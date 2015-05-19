/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.arena;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.PracticeTarget;
import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Swartt
 */
public class MenuHelper {
 PracticeState state;
 Rectangle pauseMenuBackground;
   
   public MenuHelper (PracticeState state) {
      this.state = state;
      pauseMenuBackground = new Rectangle(0, 0, 600, 300);
   }
 
   public void pauseGame(Input i, StateBasedGame sbg, float playerSpawnX, float playerSpawnY) throws SlickException, IOException {
        if(i.isKeyPressed(Input.KEY_ESCAPE)) {
            if(!state.isPaused())
                state.setPaused(true);
            else
                state.setPaused(false);
        }
        
        if(state.isPaused() && i.isKeyPressed(Input.KEY_X)) {
            sbg.enterState(Main.LEVELSELECTSTATE);
            resetLevel(playerSpawnX, playerSpawnY);
        }
    }
   public void resetLevel(float x, float y) throws SlickException, IOException {
        state.setCameraAnimationRunning(true);
        state.setPaused(false);
        state.setScale(0.24f);
        state.player.setX(x);
        state.player.setY(y);
        state.getLevel().getTargets().clear();
        for(Tile at : state.getLevel().getTargetTiles()) {
            PracticeTarget pt = new PracticeTarget(at.getX() * 25, at.getY() * 25);
            state.getLevel().getTargets().add(pt);
        }
    }
   public void drawPauseMenu(Graphics g) {
        g.setColor(new Color(0.8f, 0.8f, 0.8f, 0.8f));
        pauseMenuBackground.setCenterX(state.camera.getCamX() + 450);
        pauseMenuBackground.setCenterY(state.camera.getCamY() + 250);
        g.fill(pauseMenuBackground);
        g.setColor(Color.yellow);
        g.drawString("Press escape to unpause or X to exit the level", state.camera.getCamX() + 300, state.camera.getCamY() + 150);
//        System.out.println("X: " + pauseMenuBackground.getCenterX());
//        System.out.println("Y: " + pauseMenuBackground.getCenterY());
    }
}
