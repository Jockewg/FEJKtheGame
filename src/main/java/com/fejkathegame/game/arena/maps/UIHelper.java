
package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.arena.PracticeState;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.Main;

/**
 *
 * @author Joakim
 */
public class UIHelper {
 
    private final float X_CENTER = Main.WINDOW_WIDTH/2;
    private final float Y_CENTER = Main.WINDOW_HEIGHT/2;
    PracticeState state;
    
    public UIHelper(PracticeState state){
    this.state = state;
    }
    
    public void renderVersusUI(Character character){
        character.getHealthSystem().render(getCenterXOffset(-195f), getCenterYOffset(223f));
        character.renderStoredJumpsIndicator(getCenterXOffset(-205f), getCenterYOffset(223f));
        character.renderAttackCharge(getCenterXOffset(-195.5f), getCenterYOffset(215f));
    }
    
    public void renderPracticeUI(){
       /* state.level.updateText( state.camera.getCamX(), state.camera.getCamY());*/
    }
    
    public float getCenterXOffset(float offset){
        return state.camera.getCamX() + X_CENTER + offset;
    }
    
    public float getCenterYOffset(float offset){
        return state.camera.getCamY() + Y_CENTER + offset;
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


