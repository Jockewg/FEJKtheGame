package com.fejkathegame.game.multiplayer.lobby;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Khamekaze
 */
public class Lobby {
    
    private Image challenger;
    private ArrayList<Character> characters;
    
    public Lobby(String name) {
        try {
            challenger = new Image("src/main/resources/data/img/spritesheets/characterHead.png");
        } catch (SlickException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        characters = new ArrayList<>();
    }
    
    public ArrayList<Character> getCharacters() {
        return characters;
    }
}
