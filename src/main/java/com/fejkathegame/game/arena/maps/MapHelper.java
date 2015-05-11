package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.arena.tiles.AirTile;
import com.fejkathegame.game.arena.tiles.SolidTile;
import com.fejkathegame.game.arena.tiles.TargetTile;
import com.fejkathegame.game.arena.tiles.Tile;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.game.timer.PracticeTimer;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Swartt on 2015-05-11.
 */
public class MapHelper {

    Font font;
    TrueTypeFont ttf;
    String score;
    PracticeTimer timer;
    ArrayList<PracticeTarget> targets;
    PracticeTarget movableTarget;
    float movableTargetStartingPos;
    float targetVelY;


    public MapHelper(PracticeTimer timer, ArrayList<PracticeTarget> targets) {
        this.timer = timer;
        font = new Font("Verdana", Font.BOLD, 20);
        ttf = new TrueTypeFont(font, true);
        this.targets = targets;

        score = "Targets Left: " + String.valueOf(targets.size());
    }

    public void updateText(float x, float y) {
        ttf.drawString(x, y, score, org.newdawn.slick.Color.green);
        ttf.drawString(x + 200, y, "Time: " + String.valueOf(timer.getElapsedTimeSecs()), org.newdawn.slick.Color.red);
    }
    public void updateScore() {
        if (targets.size() > 0) {
            score = "Targets Left: " + String.valueOf(targets.size());
        } else {
            score = "You are a winrar!";
            timer.stopTimer();
        }
    }
    public void moveTargetConstructor(PracticeTarget movableTarget, float movableTargetStartingPos, float targetVelY) {
        this.movableTarget = movableTarget;
        this.movableTargetStartingPos = movableTargetStartingPos;
        this.targetVelY = targetVelY;
    }
    public void moveTarget() {
        movableTarget.setY(movableTarget.getY() + targetVelY);

        if (movableTarget.getY() > (movableTargetStartingPos + 100) && targetVelY > 0) {
            targetVelY = -1.0f;
        } else if (movableTarget.getY() < movableTargetStartingPos && targetVelY < 0) {
            targetVelY = 1.0f;
        }

    }
    public void showTargetNumber() {
        for (int i = 0; i < targets.size(); i++) {
            ttf.drawString(targets.get(i).getX(), targets.get(i).getY(), String.valueOf(i));
        }
    }
}

