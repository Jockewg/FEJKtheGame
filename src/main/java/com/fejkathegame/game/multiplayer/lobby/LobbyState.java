package com.fejkathegame.game.multiplayer.lobby;

import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.client.MPPlayer;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.arena.maps.multiplayer.versus01.VersusState;
import com.fejkathegame.game.entities.Character;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Khamekaze
 */
public class LobbyState extends State {
    
    
    private ClientProgram client = new ClientProgram();
    
    private Character localPlayer;
    
    private String name;
    private Lobby lobby;
    private ArrayList<Image> heads;
    private ArrayList<Character> characters;

    @Override
    public int getID() {
        return Main.LOBBYSTATE;
    }
    
    public LobbyState(String name) {
        this.name = name;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        lobby = new Lobby(name);
        heads = lobby.getImages();
        characters = lobby.getCharacters();
        client.network();
        
        localPlayer = null;
        try {
            localPlayer = new com.fejkathegame.game.entities.Character(800, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        characters.add(localPlayer);
        sbg.addState(new VersusState("01versus", client, localPlayer));
    }
    
    public void checkIfNewPlayerConnected() {
        for (MPPlayer mpPlayer : client.getPlayers().values()) { //other player render here.
            if (mpPlayer.character == null) {
                try {
                    mpPlayer.character = new Character(300, 40);
                } catch (SlickException | IOException ex) {
                    Logger.getLogger(VersusState.class.getName()).log(Level.SEVERE, null, ex);
                }
                mpPlayer.character.setHealth(5);
                mpPlayer.hp = 5;
                characters.add(mpPlayer.character);
            }
            if (mpPlayer.connected == false) {
                characters.remove(mpPlayer.character);
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        lobby.render();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        checkIfNewPlayerConnected();
        if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
            sbg.getState(Main.VERSUSSTATE).init(gc, sbg);
            sbg.enterState(Main.VERSUSSTATE);
        }
    }

}
