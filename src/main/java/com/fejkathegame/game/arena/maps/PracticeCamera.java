package com.fejkathegame.game.arena.maps;

/**
 * Created by Swartt on 2015-05-11.
 */
public class PracticeCamera {
    private float offsetMaxX;
    private float offsetMaxY;
    private float offsetMinX = 0;
    private float offsetMinY = 0;
    private float camX, camY = 0;
    private float cameraWidth, cameraHeight;

    public PracticeCamera(float offsetMaxX, float offsetMaxY) {
        this.offsetMaxX = offsetMaxX;
        this.offsetMaxY = offsetMaxY;
    }
    
    public float getCameraWidth() {
        return cameraWidth;
    }
    
    public void setCameraWidth(float cameraWidth) {
        this.cameraWidth = cameraWidth;
    }
    
    public float getCameraHeight() {
        return cameraHeight;
    }
    
    public void setCameraHeight(float cameraHeight) {
        this.cameraHeight = cameraHeight;
    }

    public float getOffsetMaxX() {
        return offsetMaxX;
    }

    public void setOffsetMaxX(float offsetMaxX) {
        this.offsetMaxX = offsetMaxX;
    }

    public float getOffsetMaxY() {
        return offsetMaxY;
    }

    public void setOffsetMaxY(float offsetMaxY) {
        this.offsetMaxY = offsetMaxY;
    }

    public float getOffsetMinX() {
        return offsetMinX;
    }

    public void setOffsetMinX(float offsetMinX) {
        this.offsetMinX = offsetMinX;
    }

    public float getOffsetMinY() {
        return offsetMinY;
    }

    public void setOffsetMinY(float offsetMinY) {
        this.offsetMinY = offsetMinY;
    }

    public float getCamX() {
        return camX;
    }

    public void setCamX(float camX) {
        this.camX = camX;
    }

    public float getCamY() {
        return camY;
    }

    public void setCamY(float camY) {
        this.camY = camY;
    }
}
