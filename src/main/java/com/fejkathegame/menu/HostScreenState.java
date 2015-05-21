/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.menu;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.multiplayer.lobby.LobbyState;
import com.fejkathegame.server.ServerProgram;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Filip
 */
public class HostScreenState extends State {

    ServerProgram server;
    private String name;
    private HostScreen hostScreen;
    private Input input;

    private String playerName, ip;

    @Override
    public int getID() {
        return Main.HOSTSTATE;
    }

    public HostScreenState(String name) {
        this.name = name;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        hostScreen = new HostScreen(name, gc);
        server = new ServerProgram();
        input = gc.getInput();
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
        checkIfHostIsClicked(x, y, input, gc, sbg);
    }

    private void checkIfHostIsClicked(int x, int y, Input i, GameContainer gc, StateBasedGame sbg) throws SlickException {
        if ((x > 375 && x < 525) && (y > 330 && y < 405)) {
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                playerName = hostScreen.getPlayerNameTextField().getText();
                ip = "localhost";
                ServerProgram.startServer();
                if (ServerProgram.isServerReady()) {
                    sbg.addState(new LobbyState(this));
                    sbg.getState(Main.LOBBYSTATE).init(gc, sbg);
                    sbg.enterState(Main.LOBBYSTATE);
                }
            }
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getIp() {
        return ip;
    }

}
