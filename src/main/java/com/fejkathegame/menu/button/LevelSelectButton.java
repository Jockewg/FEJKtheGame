
package com.fejkathegame.menu.button;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Joakim
 */
public class LevelSelectButton extends Button{

    String name;
    double bestTime = 0.0;
    
    public LevelSelectButton(int x, int y, int width, int height, String name) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        
        this.name = name;
    }
    
    public void render(Graphics g){
        g.setColor(Color.orange);
        g.drawRect(posX, posY, width, height);
        g.drawString(name, posX + 20, posY + (height / 2));
        g.drawString(bestTime + "", posX + 300, posY + (height / 2));
    }
    
    public void setBestTime(double time){
        bestTime = time;
    }
    
}
