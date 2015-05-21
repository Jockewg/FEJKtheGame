
package com.fejkathegame.menu.button;

import com.fejkathegame.game.properties.HighscoreAdapter;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Joakim
 */
public class LevelSelectButton extends Button{

    String title;
    String bestTime;
    HighscoreAdapter highScoreAdapt;
    int mapId;
    
    public LevelSelectButton(int x, int y, int width, int height, String name, int mapId) {
        this.posX = x;
        this.posY = y;
        this.hitPosX = x;
        this.hitPosY = y;
        this.width = width;
        this.height = height;
        this.highScoreAdapt = new HighscoreAdapter();
        this.title = name;
        this.bestTime = "Best Time : ";
        this.mapId = mapId;
        updateBestTime();
    }
    
    public void render(Graphics g){
        g.setColor(Color.orange);
        g.drawRect(posX, posY, width, height);
        g.drawString(title, posX + 20, posY + (height / 2));
        g.drawString(bestTime + "", posX + 230, posY + (height / 2));
    }
    
    public void setBestTime(String time){
        bestTime = time;
    }
    
    public void updateBestTime() {
       bestTime = "Best Time : ";
       if(highScoreAdapt.readScore(mapId) == Integer.MAX_VALUE){
            this.bestTime = this.bestTime + "N/A";
        }else{
            this.bestTime = this.bestTime + highScoreAdapt.readScore(mapId);
        }
    }
    
}
