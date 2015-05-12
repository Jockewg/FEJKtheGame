package com.fejkathegame.menu;

import com.fejkathegame.game.Main;
import com.fejkathegame.menu.button.Button;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Menu {

    //private Image buttonJoin, buttonHost, logo, buttonPractice;
    private Image logo;
    //private Button buttonJoin, buttonHost, buttonPractice;
    private Button[] buttons;
    
    /**
     * Constructor for menu, populates the state.
     * 
     * @param name
     * @throws SlickException 
     */
    public Menu(String name) throws SlickException {
        buttons = new Button[3];
        logo = new Image("src/main/resources/data/img/logo/logo.png");
        buttons[0] = new Button(new Image("src/main/resources/data/img/buttons/joinButton.png"));
        buttons[0].setPos(Main.WINDOW_WIDTH / 2 - buttons[0].getWidth() - 30, 300);
        buttons[1] = new Button(new Image("src/main/resources/data/img/buttons/hostButton.png"));
        buttons[1].setPos(Main.WINDOW_WIDTH / 2 + 30, 300);
        buttons[2] = new Button(new Image("src/main/resources/data/img/buttons/practiceButton.png"));
        buttons[2].setPos(Main.WINDOW_WIDTH / 2 - (buttons[2].getWidth() / 2), 400);
    }
    
    /**
     * Renders the items to the screen.
     * 
     */
    public void render() {
        
        logo.draw(Main.WINDOW_WIDTH / 2 - (logo.getWidth() / 2), 50);
        buttons[0].render();
        buttons[1].render();
        buttons[2].render();
        
    }

    public Button[] getButtons() {
        return buttons;
    }
    
}
