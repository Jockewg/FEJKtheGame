package com.fejkathegame.game.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Swartt on 2015-05-11.
 */
public class PracticeTimer {

    private long start = 0;
    private long stop = 0;
    private boolean running = false;

    public void startTimer() {
        start = System.currentTimeMillis();
        running = true;
    }

    public void stopTimer() {
        stop = System.currentTimeMillis();
        running = false;
    }

    public long getElapsedTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = ((System.currentTimeMillis() - start) / 1000);
        }
        else {
            elapsed = ((stop - start) / 1000);
        }
        return elapsed;
    }
}
