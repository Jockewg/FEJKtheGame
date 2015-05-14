package com.fejkathegame.menu;

import com.fejkathegame.game.Main;
import com.fejkathegame.menu.button.LevelSelectButton;
import com.fejkathegame.menu.camera.MenuCamera;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Khamekaze
 */
public class LevelSelectState extends BasicGameState {

    private LevelSelect levelSelect;
    private String name;
    private Input input;
    private MenuCamera cam;

    @Override
    public int getID() {
        return Main.LEVELSELECTSTATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        levelSelect = new LevelSelect();
        input = gc.getInput();
        cam = new MenuCamera(0, 0);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        //g.scale(900, 900);
        g.translate(-cam.getX(), -cam.getY());
        levelSelect.render(g);
        g.resetTransform();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        
        scrollPage(input, cam, levelSelect);
        selectLevel(mouseX, mouseY, input, sbg);
        
    }
    
    public void scrollPage(Input i, MenuCamera camera, LevelSelect lvlselect){
        
        int mouseBefore = Mouse.getDWheel();
        
        if(mouseBefore < 0) {
            for(LevelSelectButton lvlbtn: lvlselect.getLevelButtons()){
                lvlbtn.setHitPosY(lvlbtn.getHitPosY() +20);
            }
            camera.addToY(-20);
        } else if(mouseBefore > 0) {
            for(LevelSelectButton lvlbtn: lvlselect.getLevelButtons()){
                lvlbtn.setHitPosY(lvlbtn.getHitPosY() -20);
            }
            camera.addToY(20);
        }
        
    }

    public void selectLevel(int x, int y, Input i, StateBasedGame sbg) {
        
        if(i.isKeyPressed(Input.KEY_ESCAPE)){
            sbg.enterState(Main.MENU);
        }
        
        if(levelSelect.getLevelButtons().get(0).onHover(x, y)){
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.BIG_BlUE03);
            }
        }

        if(levelSelect.getLevelButtons().get(1).onHover(x, y)){
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.CITY04);
            }
        }

        if(levelSelect.getLevelButtons().get(2).onHover(x, y)){
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.TOWER02);
            }
        }

        if(levelSelect.getLevelButtons().get(3).onHover(x, y)){
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.TUTORIAL01);
            }
        }
            
    }
}
