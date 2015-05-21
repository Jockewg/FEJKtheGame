/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.menu;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Filip
 */
public class HostScreenState extends State{
    
    private String name;
    private HostScreen hostScreen;
    
    @Override
    public int getID() {
        return Main.HOSTSTATE;
    }
    
    public HostScreenState(String name) {
        this.name = name;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        hostScreen = new HostScreen();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
