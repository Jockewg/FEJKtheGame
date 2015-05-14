package com.fejkathegame.game.arena.maps.highscore;

import java.io.*;
import java.util.Properties;

/**
 * Created by Swartt on 2015-05-13.
 */
public class HighscoreAdapter {
    Properties properties;
    OutputStream outputStream = null;
    InputStream inputStream = null;
    String configFilePath = "config.prop";


    /**
     * saves the score to a map in the config.prop file
     * @param map
     * @param score
     */
    public void saveScore(int map, int score) {
        int prevscore = readScore(map);
        System.out.println("prevscore is: "+prevscore);

        if (prevscore > score) {


            String Smap = String.valueOf(map);
            System.out.println("String version of map is: "+Smap);
            String Sscore = String.valueOf(score);
            System.out.println("String version of score is: " + Sscore);
            try {
                outputStream = new FileOutputStream(configFilePath);

                properties.setProperty(Smap, Sscore);

                properties.store(outputStream, null);
                outputStream.close();
                int newScore = readScore(map);
                System.out.println("the new highscore is: " + newScore);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fetches the score of a map, if no score is present, it returns {@code Integer.MAX_VALUE}
     * @param map the map for which score to fetch
     * @return
     */
    public int readScore(int map) {
        String Smap = String.valueOf(map);

        int score = 0;

        try {
            inputStream = new FileInputStream(configFilePath);
            properties.load(inputStream);
            if (properties.getProperty(Smap) != null) {
                score = Integer.valueOf(properties.getProperty(Smap));
                System.out.println(score);
            } else {
                System.out.println("value was null");
                score = Integer.MAX_VALUE;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
