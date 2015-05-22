package com.fejkathegame.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;

public class JoinScreen {
    
    private TextField playerNameTextField, ipField;
    private Image connect;
    private TrueTypeFont ttf;
    private Font font;
    private String ip, playerName;
    
    /**
     * Initiates the gui for this screen.
     * 
     * @param name name of the state
     * @param gc the container to be used
     * @throws SlickException 
     */
    public JoinScreen(String name, GameContainer gc) throws SlickException {
        font = gc.getDefaultFont();
        playerNameTextField = new TextField(gc, font, gc.getWidth() / 2 - 150, gc.getHeight() / 2 - 80, 300, 40);
        playerNameTextField.setBackgroundColor(Color.white);
        ipField = new TextField(gc, font, gc.getWidth() / 2 - 150, gc.getHeight() / 2, 300, 40);
        ipField.setBackgroundColor(Color.white);
        playerNameTextField.setTextColor(Color.black);
        ipField.setTextColor(Color.black);
        connect = new Image("src/main/resources/data/img/buttons/connect.png");
        
        playerNameTextField.setMaxLength(10);
    }
    
    public JoinScreen() {
        
    }
    
    /**
     * Renders the objects on the screen.
     * 
     * @param gc
     * @param g
     */
    public void render(GameContainer gc, Graphics g) {
        g.setColor(Color.white);
        playerNameTextField.render(gc, g);
        ipField.render(gc, g);
        connect.draw(gc.getWidth() / 2 - 150, gc.getHeight() / 2 + 80);
    }

    public TextField getPlayerNameTextField() {
        return playerNameTextField;
    }

    public TextField getIpField() {
        return ipField;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public String getIp() {
        return ip;
    }
}
