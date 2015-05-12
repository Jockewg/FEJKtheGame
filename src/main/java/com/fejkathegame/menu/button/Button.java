/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.menu.button;

import org.newdawn.slick.Graphics;

/**
 *
 * @author Joakim
 */
public class Button {
    
    int posX;
    int posY;
    int width;
    int height;
    
    public Button(){
        
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
