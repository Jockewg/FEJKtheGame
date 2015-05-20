package com.fejkathegame.menu;

import com.fejkathegame.game.Main;
import com.fejkathegame.menu.button.MenuButton;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState {
    
    private Menu menu;
    private String name;
    private Input input;
    
    public MenuState(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        menu = new Menu(name);

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setBackground(Color.decode("#655d5d"));
        menu.render();
        
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        input = gc.getInput();
        int mouseX = Mouse.getX();
        int mouseY = gc.getHeight() - Mouse.getY();
        checkIfButtonIsPressed(mouseX, mouseY, input, sbg);

    }
    
    /**
     * Checks if a GUI button is being pressed.
     * 
     * If the cursor is above a button and left mouse button is pressed,
     * the corresponding state will be entered.
     * 
     * @param x x position of the cursor
     * @param y y position of the cursor
     * @param i the Input that handles mouse movement
     * @param sbg context of the game that calls for new states
     */
    public void checkIfButtonIsPressed(int x, int y, Input i, StateBasedGame sbg) {
//        if((x > 250 && x < 400) && (y < 200 && y > 125)) {
//            if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
//                sbg.enterState(Main.VERSUSSTATE);
//            }
//        } else if((x > 550 && x < 700) && (y < 200 && y > 125)) {
//            if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
//                sbg.enterState(Main.HOSTSTATE);
//            }
//        } else if((x > 325 && x < 500) && (y < 100 && y > 25)) {
//            if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
//                sbg.enterState(Main.LEVELSELECTSTATE);
//            }
//        }
        
        if(menu.getButtons()[0].onHover(x, y)){
            if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.HOSTSTATE);
            }
        }else if(menu.getButtons()[1].onHover(x, y)){
            if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.LOBBYSTATE);
            }
        }else if(menu.getButtons()[2].onHover(x, y)){
            if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                sbg.enterState(Main.LEVELSELECTSTATE);
            }
        }
            
        
    }

}
