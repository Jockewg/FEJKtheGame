/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.multiplayer.stats;

import java.util.ArrayList;
import org.newdawn.slick.Image;
import com.fejkathegame.game.entities.Character;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Filip
 */
public class Stats {
    
    private Image challenger, disconnect, playAgain;
    private ArrayList<Image> heads;
    private ArrayList<Character> characters;

    public Stats(String name) {
        try {
            challenger = new Image("src/main/resources/data/img/spritesheets/characterHead.png");
            disconnect = new Image ("src/main/resources/data/img/buttons/disconnect.png");
            playAgain = new Image ("src/main/resources/data/img/buttons/playagain.png");
        } catch (SlickException ex) {
            Logger.getLogger(Stats.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        characters = new ArrayList<>();
        heads = new ArrayList<>();
        heads.add(challenger);
        
    }
    
    public void render(Graphics g) {
        for(Character c : characters) {
            g.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
            challenger.draw((characters.indexOf(c) + 1) * 128, 100, 32, 32);
            g.drawString(c.getName(), (characters.indexOf(c) + 1) * 128, 68);
            g.drawString(String.valueOf(c.getNumberOfAttacks()), (characters.indexOf(c) + 1) * 128, 132);
        }
    }
    
    public ArrayList<Character> getCharacters() {
        return characters;
    }
    
    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
    
    public ArrayList<Image> getImages() {
        return heads;
    }

    public Image getDisconnect() {
        return disconnect;
    }

    public Image getPlayAgain() {
        return playAgain;
    }
}
