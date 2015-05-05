package com.fejkathegame.menu;

import com.fejkathegame.game.Main;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Menu {

    private Image buttonJoin, buttonHost, logo, buttonPractice;
    
    public Menu(String name) throws SlickException {
        buttonJoin = new Image("data/img/buttons/joinButton.png");
        buttonHost = new Image("data/img/buttons/hostButton.png");
        buttonPractice = new Image("data/img/buttons/practiceButton.png");
        logo = new Image("data/img/logo/logo.png");
    }
    
    public void render() {
        
        buttonJoin.draw(Main.WINDOW_WIDTH / 2 - buttonJoin.getWidth() - 30, 300);
        buttonHost.draw(Main.WINDOW_WIDTH / 2 + 30, 300);
        buttonPractice.draw(Main.WINDOW_WIDTH / 2 - (buttonPractice.getWidth() / 2), 400);
        logo.draw(Main.WINDOW_WIDTH / 2 - (logo.getWidth() / 2), 50);
        
    }
    
}
