/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.multiplayer.stats;

import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.client.MPPlayer;
import com.fejkathegame.client.PacketServerIsPlaying;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.multiplayer.lobby.LobbyState;
import com.fejkathegame.menu.JoinScreenState;
import com.fejkathegame.server.ServerProgram;
import org.newdawn.slick.Input;

/**
 *
 * @author Filip
 */
public class StatsState extends State {

    ClientProgram client;
    ServerProgram server;
    private String name;
    private Character localPlayer;
    private Stats stats;
    private int percent = 0;

    private ArrayList<Character> characters;

    @Override
    public int getID() {
        return Main.STATSSTATE;
    }

    public StatsState(String name, ClientProgram client, Character localPlayer, ArrayList<Character> characters) {
        this.name = name;
        this.client = client;
        this.localPlayer = localPlayer;
        this.characters = characters;
    }

    public StatsState(String name, ClientProgram client, Character localPlayer, ArrayList<Character> characters, ServerProgram server) {
        this.name = name;
        this.client = client;
        this.localPlayer = localPlayer;
        this.characters = characters;
        this.server = server;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        stats = new Stats(name);
        stats.setCharacters(characters);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        stats.render(g);

        g.drawImage(stats.getDisconnect(), (Main.WINDOW_WIDTH / 2) - 100, 350);
        g.drawImage(stats.getPlayAgain(), (Main.WINDOW_WIDTH / 2) + 100, 350);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

        checkIfPlayAgainIsPressed(gc.getInput(), sbg, gc);
        checkIfDisconnectIsPressed(gc.getInput(), sbg);
    }

    private void checkIfPlayAgainIsPressed(Input i, StateBasedGame sbg, GameContainer gc) throws SlickException {
        if (i.getMouseX() > 550 && i.getMouseX() < 750
                && i.getMouseY() > 350 && i.getMouseY() < 425) {
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                for (MPPlayer mp : client.getPlayers().values()) {
                    mp.ready = false;
                    mp.character = null;
                }
                if (ServerProgram.server != null) {
                    PacketServerIsPlaying packet = new PacketServerIsPlaying();
                    packet.serverIsPlaying = false;
                    client.getClient().sendTCP(packet);
                }
                sbg.getState(Main.oldLobby.getID()).init(gc, sbg);
                sbg.enterState(Main.oldLobby.getID());
            }
        }
    }

    private void checkIfDisconnectIsPressed(Input i, StateBasedGame sbg) {
        if (i.getMouseX() > 350 && i.getMouseX() < 550
                && i.getMouseY() > 350 && i.getMouseY() < 425) {
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (ServerProgram.getServer() != null) {
                    ServerProgram.getServer().close();
                }
                client.getClient().close();
                sbg.enterState(Main.MENU);
            }
        }
    }

}
