
package com.fejkathegame.menu.button;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Joakim
 */
public class MenuButton extends Button {
    
    Image img;
    
    public MenuButton(int x, int y, Image image){
        this.posX = x;
        this.posY = y;
        this.hitPosX = x;
        this.hitPosY = y;
        this.img = image;
        this.width = img.getWidth();
        this.height = img.getHeight();
        
        
    }
    
    public MenuButton(Image image){
        img = image;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    public void render() {
        img.draw(posX, posY);
    }
    
    
    
    
    
    
    
}
