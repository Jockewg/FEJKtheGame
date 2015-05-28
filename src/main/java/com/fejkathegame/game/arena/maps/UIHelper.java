
package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.Main;

/**
 *
 * @author Joakim
 * 
 * This class are supposed to help simplify the use of UI:s by simply calling 
 * wich UI you'll want to render. 
 * Either the Versus UI with HP-bars, powermeter and jumpstack or
 * the Practice UI containing a timer and targetcount.
 */
public class UIHelper {
 
    private final float X_CENTER = Main.WINDOW_WIDTH/2;
    private final float Y_CENTER = Main.WINDOW_HEIGHT/2;
    State state;
    
    public UIHelper(State state){
        this.state = state;
    }
    
    public void renderVersusUI(Character character, float x, float y){
        character.getHealthSystem().render(x + (Main.WINDOW_WIDTH / 2) - 67.5f, y + 500 - 28);
        character.renderStoredJumpsIndicator(x + (Main.WINDOW_WIDTH / 2) - 67.5f - 10, y + 500 - 28);
        character.renderAttackCharge(x + (Main.WINDOW_WIDTH / 2) - 67.5f, y + 500 - 28 - 8);
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


