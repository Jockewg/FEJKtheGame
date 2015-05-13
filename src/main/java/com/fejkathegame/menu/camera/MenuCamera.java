
package com.fejkathegame.menu.camera;

/**
 *
 * @author Joakim
 */
public class MenuCamera {
    
    private float x, y;
    
    public MenuCamera(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public void addToY(float y) {
        this.y += y;
    }
    
}
