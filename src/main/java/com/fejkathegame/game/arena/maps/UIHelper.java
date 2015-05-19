
package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.Main;

/**
 *
 * @author Joakim
 */
public class UIHelper {
 
    private final float X_CENTER = Main.WINDOW_WIDTH/2;
    private final float Y_CENTER = Main.WINDOW_HEIGHT/2;
    private float camX;
    private float camY;
    
    public UIHelper(float camX, float camY){
        this.camX = camX;
        this.camY = camY;
    }
    
    public void renderVersusUI(Character character){
        character.getHealthSystem().render(getCenterXOffset(-195f), getCenterYOffset(223f));
        character.renderStoredJumpsIndicator(getCenterXOffset(-205f), getCenterYOffset(223f));
        character.renderAttackCharge(getCenterXOffset(-195.5f), getCenterYOffset(215f));
    }
    
    public void renderPracticeUI(PracticeLevelHelper helper){
        helper.updateText(camX, camX);
    }
    
    public float getCenterXOffset(float offset){
        return camX + X_CENTER + offset;
    }
    
    public float getCenterYOffset(float offset){
        return camY + Y_CENTER + offset;
    }
    
    public float getCenterX(float camX){
        return camX + X_CENTER;
    }
    
    public float getCenterY(float camY){
        return camY + Y_CENTER;
    }
    
    public float getLeft(float camX, float offset){
        return camX + offset;
    }
    
    public static float getRight(float camX, float offset){
        return camX + 900 + offset;
    }
    
    public static float getTop(float camY, float offset){
        return camY + offset;
    }
    
    public static float getBottom(float camY, float offset){
        return camY + 500 + offset;
    }
    
}


