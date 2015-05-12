
package com.fejkathegame.menu.button;

import org.newdawn.slick.Image;

/**
 *
 * @author Joakim
 */
public class Button {
    
    int posX;
    int posY;
    int width;
    int height;
    Image img;
    
    public Button(int x, int y, Image image){
        this.img = image;
        this.posX = x;
        this.posY = y;
        this.width = img.getWidth();
        this.height = img.getHeight();
        
    }
    
    public Button(Image image){
        this.img = image;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }
    
    public void render(){
        img.draw(posX, posY);
    }
    
    public boolean onHover(int x, int y){
        if(x > posX && x < (posX + width)){
            if(y > posY && y < (posY + height)){
                return true;
            }
            return false;
        }
        return false;
    }
    
    public void setPos(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}
