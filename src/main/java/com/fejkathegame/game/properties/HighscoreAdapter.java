package com.fejkathegame.game.properties;

import java.io.*;

/**
 * Created by Swartt on 2015-05-13.
 */
public class HighscoreAdapter {
    InputStream inputStream;
    String configFilePath = "config.prop";
    PropertiesAdapter saver = new PropertiesAdapter();


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
            saver.save(Smap, Sscore);
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
        
        if (!"null".equals(saver.load(Smap))) {
           score = Integer.valueOf(saver.load(Smap));
        } else {
           score = Integer.MAX_VALUE;
        }
        return score;
    }
}
