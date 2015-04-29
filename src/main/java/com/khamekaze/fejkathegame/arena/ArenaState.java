package com.khamekaze.fejkathegame.arena;

import com.khamekaze.fejkathegame.Main;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ArenaState extends BasicGameState {
    
    Arena arena;
    String name;
    
    public ArenaState(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        arena = new Arena(name);

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.scale(Main.SCALE, Main.SCALE);
        arena.render();
    }
    

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        
    }
    
}
