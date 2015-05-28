package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.menu.LevelSelect;
import com.fejkathegame.menu.button.Button;
import com.fejkathegame.menu.button.LevelSelectButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Swartt on 2015-05-11.
 */
public class PracticeStateHelper {

    State state;
    Rectangle pauseMenuBackground;
    UIHelper uIHelper;

    public PracticeStateHelper(State state) {
        this.state = state;
        pauseMenuBackground = new Rectangle(0, 0, 600, 300);
        uIHelper = new UIHelper(state);
        state.level.helper.updateScore(state.getID());
        state.player.setCanUlti(false);
    }

    /**
     * Checks if the player hits a target, if true then it updates the current state
     * @param map
     */
    public void checkCollisionWithTarget(int map) {
        for (int i = 0; i < state.level.targets.size(); i++) {
            if (state.player.getAttackIndicator().intersects(state.level.targets.get(i).getHitbox()) && state.player.getIsAttacking()
                    || state.player.getIsFullyCharged() && state.player.getSuperAttackIndicator().intersects(state.level.targets.get(i).getHitbox())) {
                state.level.targets.get(i).getHealthSystem().dealDamage(1);
                state.level.targets.remove(i);
                state.level.helper.updateScore(map);
            }
        }
    }

    /**
     * Checks if the camera is allowed to move, stops the camera from showing out of bounds
     */
    public void checkCameraOffset() {
        if (state.player.getX() <= state.camera.getOffsetMinX() + 450) {
            state.camera.setCamX(state.camera.getOffsetMinX());
        } else if (state.player.getX() >= state.camera.getOffsetMaxX()) {
            state.camera.setCamX(state.camera.getOffsetMaxX() - 450);
        } else {
            state.camera.setCamX(state.player.getX() - 450.0f);
        }

        if (state.player.getY() <= state.camera.getOffsetMinY() + 250) {
            state.camera.setCamY(state.camera.getOffsetMinY());
        } else if (state.player.getY() >= state.camera.getOffsetMaxY()) {
            state.camera.setCamY(state.camera.getOffsetMaxY() - 250);
        } else {
            state.camera.setCamY(state.player.getY() - 250.0f);
        }
    }

    /**
     * Pauses the game
     * @param i
     * @param sbg
     * @param playerSpawnX
     * @param playerSpawnY
     * @throws SlickException
     * @throws IOException
     */
    public void pauseGame(Input i, StateBasedGame sbg, float playerSpawnX, float playerSpawnY) throws SlickException, IOException {
        if (i.isKeyPressed(Input.KEY_ESCAPE)) {
            if (!state.isPaused()) {
                state.setPaused(true);
            } else {
                state.setPaused(false);
            }
        }

        if (state.isPaused() && i.isKeyPressed(Input.KEY_X)) {
            sbg.enterState(Main.LEVELSELECTSTATE);
            resetLevel(playerSpawnX, playerSpawnY);
        }
    }

    /**
     * Resets the level, changes all values to default
     * @param x
     * @param y
     * @throws SlickException
     * @throws IOException
     */
    public void resetLevel(float x, float y) throws SlickException, IOException {
        state.setCameraAnimationRunning(true);
        state.setPaused(false);
        state.setScale(0.24f);
        state.player.setX(x);
        state.player.setY(y);
        state.level.timer.resetTimer();
        state.level.timer.startCountdown(3);
        state.level.timer.startTimer();
        state.level.targets.clear();
        state.level.helper.writtenToFile = false;
        ArrayList<LevelSelectButton> buttons = LevelSelect.getLevelButtons();
        for (LevelSelectButton button : buttons) {
           button.updateBestTime();
        }
        for (Tile at : state.level.targetTiles) {
            PracticeTarget pt = new PracticeTarget(at.getX() * 25, at.getY() * 25);
            state.level.targets.add(pt);
        }
        state.level.helper.updateScore(state.getID());
        state.level.initMovableTarget();
        state.player.setX_velocity(0);
        state.player.setY_velocity(0);
    }

    /**
     * renders the pause menu
     * @param g
     */
    public void drawPauseMenu(Graphics g) {
        g.setColor(new Color(0.8f, 0.8f, 0.8f, 0.8f));
        pauseMenuBackground.setCenterX(state.camera.getCamX() + 450);
        pauseMenuBackground.setCenterY(state.camera.getCamY() + 250);
        g.fill(pauseMenuBackground);
        g.setColor(Color.yellow);
        g.drawString("Press escape to unpause or X to exit the level", state.camera.getCamX() + 300, state.camera.getCamY() + 150);
    }

    /**
     * Updates the game state
     * @param gc
     * @param sbg
     * @param i
     * @param playerSpawnX
     * @param playerSpawnY
     * @throws SlickException
     */
    public void update(GameContainer gc, StateBasedGame sbg, int i, float playerSpawnX, float playerSpawnY) throws SlickException {
        try {
            pauseGame(gc.getInput(), sbg, playerSpawnX, playerSpawnY);
        } catch (IOException ex) {
            Logger.getLogger(state.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        if (!state.paused) {
            checkCameraOffset();
            if (!state.level.timer.isCountdownRunning()) {
                state.player.movementSystem.handleInput(gc.getInput(), i);
                state.physics.handlePhysics(state.level, i);
                checkCollisionWithTarget(state.getID());
            }
            state.player.update(i);
            state.level.timer.calculateSecond(i);
        }
    }

    /**
     * Renders the game
     * @param g
     * @throws SlickException
     */
    public void render(Graphics g) throws SlickException {

        g.setAntiAlias(false);
        g.scale(Main.SCALE, Main.SCALE);
        g.translate(-state.camera.getCamX(), -state.camera.getCamY());
        state.level.render();
        state.level.helper.updateText(state.camera.getCamX(), state.camera.getCamY());
        if (state.paused) {
            drawPauseMenu(g);
        }
        uIHelper.renderPracticeUI();
        g.translate(-state.camera.getCamX(), -state.camera.getCamY());


        g.resetTransform();

    }
}
