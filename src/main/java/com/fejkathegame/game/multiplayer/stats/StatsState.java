/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.multiplayer.stats;

import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import com.fejkathegame.game.entities.Character;

/**
 *
 * @author Filip
 */
public class StatsState extends State {
    
    ClientProgram client;
    private String name;
    private Character localPlayer;
    private Stats stats;
    
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

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        stats = new Stats(name);
        stats.setCharacters(characters);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        
        stats.render(g);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
    }
    
}
