package com.fejkathegame.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HostScreenState extends BasicGameState {
    
    private HostScreen hostScreen;
    private String name;
    private Input input;
    
    public HostScreenState(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        input = gc.getInput();
        hostScreen = new HostScreen(name, gc);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setBackground(Color.decode("#655d5d"));
        
        hostScreen.render(gc, g);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        int x = input.getMouseX();
        int y = input.getMouseY();
        checkIfConnectIsClicked(x, y, input);
    }
    
    public void checkIfConnectIsClicked(int x, int y, Input i) {
        if((x > 300 && x < 600) && (y > 331 && y < 371)) {
            if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                //Logic for clicking connect
            }
        }
    }

}
