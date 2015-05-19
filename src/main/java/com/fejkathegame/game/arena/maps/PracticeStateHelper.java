package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.PracticeLevel;
import com.fejkathegame.game.arena.PracticeState;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.entities.PracticeTarget;
import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Created by Swartt on 2015-05-11.
 */
public class PracticeStateHelper {
      PracticeState state;
      Rectangle pauseMenuBackground;
      

    public PracticeStateHelper(PracticeState state) {
       this.state = state;
       pauseMenuBackground = new Rectangle(0, 0, 600, 300);
    }

    public void checkCollisionWithTarget(int map) {
        for (int i = 0; i < state.arena.getTargets().size(); i++) {
            if (state.player.getAttackIndicator().intersects(state.arena.getTargets().get(i).getHitbox()) && state.player.getIsAttacking()
                    || state.player.getIsFullyCharged() && state.player.getSuperAttackIndicator().intersects(state.arena.getTargets().get(i).getHitbox())) {
                state.arena.getTargets().get(i).getHealthSystem().dealDamage(1);
                state.arena.getTargets().remove(i);
                state.arena.getMapHelper().updateScore(map);
            }
        }
    }


    public void checkCameraOffset() {
        if (state.player.getX() <= state.camera.getOffsetMinX() + 450)
            state.camera.setCamX(state.camera.getOffsetMinX());
        else if (state.player.getX() >= state.camera.getOffsetMaxX())
            state.camera.setCamX(state.camera.getOffsetMaxX() - 450);
        else
            state.camera.setCamX(state.player.getX() - 450.0f);

        if (state.player.getY() <= state.camera.getOffsetMinY() + 250)
            state.camera.setCamY(state.camera.getOffsetMinY());
        else if (state.player.getY() >= state.camera.getOffsetMaxY())
            state.camera.setCamY(state.camera.getOffsetMaxY() - 250);
        else
            state.camera.setCamY(state.player.getY() - 250.0f);
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
    }

}
