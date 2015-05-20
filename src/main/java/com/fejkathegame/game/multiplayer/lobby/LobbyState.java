package com.fejkathegame.game.multiplayer.lobby;

import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.maps.multiplayer.versus01.VersusState;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Khamekaze
 */
public class LobbyState extends BasicGameState {
    
    
    private ClientProgram client = new ClientProgram();
    
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
        sbg.addState(new VersusState("01versus", client));
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
            sbg.getState(Main.VERSUSSTATE).init(gc, sbg);
            sbg.enterState(Main.VERSUSSTATE);
        }
    }

}
