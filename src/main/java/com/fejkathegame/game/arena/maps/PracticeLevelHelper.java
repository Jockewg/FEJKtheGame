package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.entities.PracticeTarget;
import com.fejkathegame.game.timer.PracticeTimer;
import org.newdawn.slick.*;

import java.awt.Font;
import java.util.ArrayList;

/**
 * Created by Swartt on 2015-05-11.
 */
public class PracticeLevelHelper {

    Font font;
    TrueTypeFont ttf;
    String score;
    PracticeTimer timer;
    ArrayList<PracticeTarget> targets;
    PracticeTarget movableTarget;
    float movableTargetStartingPos;
    float targetVelY;
    Font font2;
    TrueTypeFont ttf2;


    public PracticeLevelHelper(PracticeTimer timer, ArrayList<PracticeTarget> targets) {
        this.timer = timer;
        font = new Font("Verdana", Font.BOLD, 20);
        ttf = new TrueTypeFont(font, true);
        font2 = new Font("Arial", Font.BOLD, 50);
        ttf2 = new TrueTypeFont(font2, true);
        this.targets = targets;
        timer.startCountdown(10);
        
        score = "Targets Left: " + String.valueOf(targets.size());
        
    }

    public void updateText(float x, float y) {
        ttf.drawString(x, y, score, org.newdawn.slick.Color.green);
        ttf.drawString(x + 200, y, "Time: " + String.valueOf(timer.getTimerDuration()), org.newdawn.slick.Color.red);
        if (timer.isCountdownRunning()){
        ttf2.drawString(x + 350, y + 250, "countdown: " + String.valueOf(timer.getCurrentCountdownTime()));
        }
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

