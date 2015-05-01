package com.fejkathegame.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.TextField;

public class HostScreen {
    
    private TextField playerName, ipField;
    private Image connect;
    private Font font;
    
    public HostScreen(String name, GameContainer gc) throws SlickException {
        font = new UnicodeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 26));
        playerName = new TextField(gc, font, gc.getWidth() / 2 - 150, gc.getHeight() / 2 - 80, 300, 40);
        playerName.setBackgroundColor(Color.white);
        ipField = new TextField(gc, font, gc.getWidth() / 2 - 150, gc.getHeight() / 2, 300, 40);
        ipField.setBackgroundColor(Color.white);
        connect = new Image("data/img/buttons/connect.png");
    }
    
    public void render(GameContainer gc, Graphics g) {
        playerName.render(gc, g);
        ipField.render(gc, g);
        connect.draw(gc.getWidth() / 2 - 150, gc.getHeight() / 2 + 80);
    }

}
