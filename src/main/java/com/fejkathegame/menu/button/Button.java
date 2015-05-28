
package com.fejkathegame.menu.button;

/**
 *
 * @author Joakim
 * 
 * Basic Button with xy positioning, width and hight.
 * It also uses a separate hitbox for when rendering/update differences occure.
 */
public class Button {
    
    int posX;
    int posY;
    int hitPosX;
    int hitPosY;
    int width;
    int height;
    
    public Button(){
        
    }
    
    /**
     * When your mousepointer hover over the button
     * @param x
     * @param y
     */
    public boolean onHover(int x, int y){
        if(x > hitPosX && x < (hitPosX + width)){
            if(y > hitPosY && y < (hitPosY + height)){
                return true;
            }
            return false;
        }
        return false;
    }
    
    public void setPos(int x, int y){
        this.posX = x;
        this.posY = y;
        this.hitPosX = x;
        this.hitPosY = y;
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
    
    public int getHitPosX() {
        return hitPosX;
    }

    public void setHitPosX(int hitPosX) {
        this.hitPosX = hitPosX;
    }

    public int getHitPosY() {
        return hitPosY;
    }

    public void setHitPosY(int hitPosY) {
        this.hitPosY = hitPosY;
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
