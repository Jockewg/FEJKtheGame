package com.fejkathegame.game.arena.maps.highscore;

import java.io.*;
import java.util.Properties;

/**
 * Created by Swartt on 2015-05-13.
 */
public class HighscoreAdapter {
    Properties properties = new Properties();
    OutputStream outputStream = null;
    InputStream inputStream = null;
    String configFilePath = "config.prop";

    public void saveScore(int map, int score) {
        String Smap = String.valueOf(map);
        String Sscore = String.valueOf(score);
        System.out.println("wat");
        try {
            outputStream = new FileOutputStream(configFilePath);

            properties.setProperty(Smap, Sscore);

            properties.store(outputStream, null);
            System.out.println("shit worked");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int readScore(int map) {
        String Smap = String.valueOf(map);

        int score = 0;

        try {
            inputStream = new FileInputStream(configFilePath);
            score = Integer.valueOf(properties.getProperty(Smap));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


        return score;
    }
}
