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
    
    /**
     * Initiates the gui for this screen.
     * 
     * @param name name of the state
     * @param gc the container to be used
     * @throws SlickException 
     */
    public HostScreen(String name, GameContainer gc) throws SlickException {
        font = gc.getDefaultFont();
        playerName = new TextField(gc, font, gc.getWidth() / 2 - 150, gc.getHeight() / 2 - 80, 300, 40);
        playerName.setBackgroundColor(Color.white);
        ipField = new TextField(gc, font, gc.getWidth() / 2 - 150, gc.getHeight() / 2, 300, 40);
        ipField.setBackgroundColor(Color.white);
        playerName.setTextColor(Color.black);
        ipField.setTextColor(Color.black);
        connect = new Image("src/main/resources/data/img/buttons/connect.png");
    }
    
    /**
     * Renders the objects on the screen.
     * 
     * @param gc
     * @param g
     */
    public void render(GameContainer gc, Graphics g) {
        playerName.render(gc, g);
        ipField.render(gc, g);
        connect.draw(gc.getWidth() / 2 - 150, gc.getHeight() / 2 + 80);
    }

    public TextField getPlayerName() {
        return playerName;
    }

    public TextField getIpField() {
        return ipField;
    }
}
