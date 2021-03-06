package com.fejkathegame.game.multiplayer.lobby;

import com.fejkathegame.game.entities.Character;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Khamekaze
 */
public class Lobby {
    
    private Image challenger, readyBtn, unReadyBtn;
    private ArrayList<Character> characters;
    private ArrayList<Image> heads;
    
    public Lobby(String name) {
        try {
            challenger = new Image("src/main/resources/data/img/spritesheets/characterHead.png");
            readyBtn = new Image("src/main/resources/data/img/buttons/ready.png");
            unReadyBtn = new Image("src/main/resources/data/img/buttons/unready.png");
        } catch (SlickException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        characters = new ArrayList<>();
        characters.clear();
        heads = new ArrayList<>();
        heads.add(challenger);
    }
    
    public void render() {
        for(Character c : characters) {
            challenger.draw((characters.indexOf(c) + 1) * 128, 100, 32, 32);
        }
    }
    
    public Image getReadyButton() {
        return readyBtn;
    }
    
    public Image getUnReadyButton() {
        return unReadyBtn;
    }
    
    public ArrayList<Character> getCharacters() {
        return characters;
    }
    
    public ArrayList<Image> getImages() {
        return heads;
    }
}
