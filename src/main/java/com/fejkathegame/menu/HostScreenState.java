package com.fejkathegame.menu;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.multiplayer.lobby.LobbyState;
import com.fejkathegame.server.ServerProgram;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import sun.security.x509.IPAddressName;

public class HostScreenState extends BasicGameState {
    
    private HostScreen hostScreen;
    private String name;
    private Input input;
    
    private String playerName, ip;
    
    public HostScreenState(String name) {
        this.name = name;
    }
    
    public HostScreenState() {
        
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

    /**
     * Updates the state.
     * 
     * @param gc
     * @param sbg
     * @param i
     * @throws SlickException 
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        int x = input.getMouseX();
        int y = input.getMouseY();
        checkIfConnectIsClicked(x, y, input, gc, sbg);
        System.out.println("IP: " + ip);
        System.out.println("PLAYER: " + playerName);
    }
    
    /**
     * Checks if the mosue is on the connect button.
     * 
     * If the mouse is above the connect button and the left mouse button is
     * clicked, connection to server will be attempted.
     * 
     * @param x x position of the mouse
     * @param y y position of the mouse
     * @param i the Input to be used to read mouse pos
     * @param sbg
     */
    public void checkIfConnectIsClicked(int x, int y, Input i, GameContainer gc, StateBasedGame sbg) throws SlickException {
        if((x > 300 && x < 600) && (y > 331 && y < 371)) {
            if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                ip = hostScreen.getIpField().getText();
                playerName = hostScreen.getPlayerNameTextField().getText();
                sbg.addState(new LobbyState(this));
                sbg.getState(Main.LOBBYSTATE).init(gc, sbg);
                sbg.enterState(Main.LOBBYSTATE);
            }
        }
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
