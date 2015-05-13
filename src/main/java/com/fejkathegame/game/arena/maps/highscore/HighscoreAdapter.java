package com.fejkathegame.game.arena.maps.highscore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by Swartt on 2015-05-13.
 */
public class HighscoreAdapter {
    Properties properties = new Properties();
    OutputStream outputStream = null;


    public void saveScore(int map, int score) {
        String Smap = String.valueOf(map);
        String Sscore = String.valueOf(score);

        try {
            outputStream = new FileOutputStream("config.prop");

            properties.setProperty(Smap, Sscore);

            properties.store(outputStream, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
