package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.properties.HighscoreAdapter;
import com.fejkathegame.game.arena.tiles.*;
import com.fejkathegame.game.entities.PracticeTarget;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Swartt on 2015-05-11.
 */
public class PracticeLevelHelper {

    Font font;
    TrueTypeFont ttf;
    String score;

    PracticeTarget movableTarget;
    float movableTargetStartingPos;
    float targetVelY;
    Font font2;
    TrueTypeFont ttf2;
    HighscoreAdapter adapter;
    boolean writtenToFile = false;
    Level level;


    public PracticeLevelHelper(Level level) {
        this.level = level;
        adapter = new HighscoreAdapter();
        font = new Font("Verdana", Font.BOLD, 20);
        ttf = new TrueTypeFont(font, true);
        font2 = new Font("Arial", Font.BOLD, 50);
        ttf2 = new TrueTypeFont(font2, true);
        startTimer();
        score = "Targets Left: " + String.valueOf(level.targets.size());
    }

    /**
     * Starts the countdown
     */
    public void startTimer(){
        int delayBeforeStart = 3;
        level.timer.startCountdown(delayBeforeStart);
    };

    /**
     * Updates the texts position
     * @param x coordinates
     * @param y coordinates
     */
    public void updateText(float x, float y) {
        ttf.drawString(x, y, score, Color.green);
        ttf.drawString(x + 200, y, "Time: " + String.valueOf(level.timer.getTimerDuration()), Color.red);
        if (level.timer.isCountdownRunning()) {
            ttf2.drawString(x + 350, y + 250, "countdown: " + String.valueOf(level.timer.getCurrentCountdownTime()), Color.blue);
        }
    }

    /**
     * Updates all current score, also finishes the game if all targets have been destroyed
     * @param map
     */
    public void updateScore(int map) {
        if (level.targets.size() > 0) {
            score = "Targets Left: " + String.valueOf(level.targets.size());
        } else {
            score = "You are a winrar!";
            level.timer.stopTimer();
            saveScore(map);
        }
    }

    /**
     * Constructor for movable targets
     * @param movableTarget
     * @param movableTargetStartingPos
     * @param targetVelY
     */
    public void moveTargetConstructor(PracticeTarget movableTarget, float movableTargetStartingPos, float targetVelY) {
        this.movableTarget = movableTarget;
        this.movableTargetStartingPos = movableTargetStartingPos;
        this.targetVelY = targetVelY;
    }

    /**
     * Moves selected target
     */
    public void moveTarget() {
        movableTarget.setY(movableTarget.getY() + targetVelY);

        if (movableTarget.getY() > (movableTargetStartingPos + 100) && targetVelY > 0) {
            targetVelY = -1.0f;
        } else if (movableTarget.getY() < movableTargetStartingPos && targetVelY < 0) {
            targetVelY = 1.0f;
        }

    }

    /**
     * Shows the targets number in the array, used for debugging
     */
    public void showTargetNumber() {
        for (int i = 0; i < level.targets.size(); i++) {
            ttf.drawString(level.targets.get(i).getX(), level.targets.get(i).getY(), String.valueOf(i));
        }
    }

    /**
     * Saves the score to the database
     * @param map
     */
    public void saveScore(int map) {
        if (!writtenToFile) {
            adapter.saveScore(map, level.timer.getTimerDuration());
            writtenToFile = true;
        }

    }
    /**
     * populates the level with tiles
     *
     * @throws org.newdawn.slick.SlickException
     */
    public void loadTileMap() {
        level.tiles = new Tile[level.map.getWidth() + 1][level.map.getHeight() + 1];

        int collisionLayer = level.map.getLayerIndex("CollisionLayer");
        int noLayer = -1;

        if (collisionLayer == noLayer) {
            System.err.println("Map does not contain CollisionLayer");
            System.exit(0);
        }


        for (int x = 0; x < level.map.getWidth(); x++) {
            for (int y = 0; y < level.map.getHeight(); y++) {


                try {
                    int tileID = level.map.getTileId(x, y, collisionLayer);

                    Tile tile = null;
                    PracticeTarget target = null;

                    switch (level.map.getTileProperty(tileID, "tileType", "solid")) {
                        case "air":
                            tile = new AirTile(x, y);
                            break;

                        case "target":
                            tile = new TargetTile(x, y);
                            target = new PracticeTarget(x * 25, y * 25);
                            level.targets.add(target);
                            level.targetTiles.add(tile);
                            break;

                        case "pit":
                            tile = new PitTile(x, y);
                            break;

                        default:
                            tile = new SolidTile(x, y);
                            break;
                    }
                    level.tiles[x][y] = tile;
                } catch (SlickException ex) {
                    Logger.getLogger(level.getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(level.getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }

            }
        }
    }
}

   


