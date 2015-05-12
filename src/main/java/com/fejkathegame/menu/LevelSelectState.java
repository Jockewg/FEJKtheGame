package com.fejkathegame.menu;

import com.fejkathegame.game.Main;
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

    @Override
    public int getID() {
        return Main.LEVELSELECTSTATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        levelSelect = new LevelSelect();
        input = gc.getInput();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        levelSelect.render(g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        selectLevel(mouseX, mouseY, input, sbg);
    }

    public void selectLevel(int x, int y, Input i, StateBasedGame sbg) {

        if ((x >= 150 && x <= 300) && (y >= 150 && y <= 300)) {
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.TOWER1STATE);
            }
        }
        if ((x >= 350 && x <= 450) && (y >= 150 && y <= 300)) {
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.BIGBLUESTATE);
            }
        }
        if ((x >= 550 && x <= 650) && (y >= 150 && y <= 300)) {
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.FIRSTPRACTICE);
            }
        }
        if ((x >= 750 && x <= 800) && (y >= 150 && y <= 300)) {
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.TUTORIAL);
            }
        }
    }
}
