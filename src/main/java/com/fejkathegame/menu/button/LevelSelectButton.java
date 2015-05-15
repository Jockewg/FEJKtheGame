
package com.fejkathegame.menu.button;

import com.fejkathegame.game.arena.maps.highscore.HighscoreAdapter;
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
    
    public LevelSelectButton(int x, int y, int width, int height, String name, int mapId) {
        this.posX = x;
        this.posY = y;
        this.hitPosX = x;
        this.hitPosY = y;
        this.width = width;
        this.height = height;
        this.highScoreAdapt = new HighscoreAdapter();
        this.title = name;

        if(highScoreAdapt.readScore(mapId) == Integer.MAX_VALUE){
            this.bestTime = "N/A";
        }else{
            this.bestTime = highScoreAdapt.readScore(mapId)+ "";

        }
        
    }
    
    public void render(Graphics g){
        g.setColor(Color.orange);
        g.drawRect(posX, posY, width, height);
        g.drawString(title, posX + 20, posY + (height / 2));
        g.drawString(bestTime + "", posX + 300, posY + (height / 2));
        g.drawString("HitPosY: " + hitPosY, posX + 160, posY);
    }
    
    public void setBestTime(String time){
        bestTime = time;
    }
    
}
