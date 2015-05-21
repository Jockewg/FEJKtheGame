/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Filip
 */
public class HostScreen {
    
    private TextField playerNameTextField;
    private Image host;
    private Font font;

    public HostScreen(String name, GameContainer gc) throws SlickException {
        
        font = gc.getDefaultFont();
        playerNameTextField = new TextField(gc, font, gc.getWidth() / 2 - 150, gc.getHeight() / 2 - 80, 300, 40);
        playerNameTextField.setBackgroundColor(Color.white);
        playerNameTextField.setTextColor(Color.black);
        host = new Image("src/main/resources/data/img/buttons/hostButton.png");
        
        playerNameTextField.setMaxLength(10);
    }

    public void render(GameContainer gc, Graphics g) {
        playerNameTextField.render(gc, g);
        playerNameTextField.setFocus(true);
        host.draw(gc.getWidth() / 2 - 75, gc.getHeight() / 2 + 80);
    }

    public TextField getPlayerNameTextField() {
        return playerNameTextField;
    }
    
}
