package com.fejkathegame.game;

import com.fejkathegame.game.game.Game;
import com.fejkathegame.game.arena.ArenaState;
import com.fejkathegame.menu.MenuState;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {
    
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 500;
    public static final boolean FULLSCREEN = false;
    
    public static final float SCALE = (float) (1 * ((double) WINDOW_WIDTH / 900));
    
    public Main() {
        super("FEJKA the game");
    }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new MenuState("menu"));
        addState(new ArenaState("arena"));
        
        this.enterState(0);

    }
    
    public static void main(String[] args) {
        try {
                    AppGameContainer appgc = new AppGameContainer(new Main());
                    appgc.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, FULLSCREEN);
                    appgc.setDisplayMode(900, 500, false);
                    appgc.setShowFPS(false);
                    appgc.start();
            } catch (SlickException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
}
