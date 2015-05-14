package com.fejkathegame.game;

import com.fejkathegame.game.arena.maps.practice02.Tower01State;
import com.fejkathegame.game.arena.maps.practice03.BigBlue02State;
import com.fejkathegame.game.arena.maps.versus01.VersusState;
import com.fejkathegame.game.arena.maps.practice01.PracticeState;
import com.fejkathegame.game.arena.maps.tutorial04.Tutorial03State;
import com.fejkathegame.menu.HostScreenState;
import com.fejkathegame.menu.LevelSelectState;
import com.fejkathegame.menu.MenuState;

import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {
    
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 500;
    public static final boolean FULLSCREEN = false;
    
    public static final int MENU = 0;
    public static final int VERSUSSTATE = 1;
    public static final int HOSTSTATE = 2;
    public static final int LEVELSELECTSTATE = 3;
    public static final int TOWER1STATE = 21;
    public static final int BIGBLUESTATE = 22;
    public static final int FIRSTPRACTICE = 23;
    public static final int TUTORIAL = 24;

    
    public static final float SCALE = (float) (1 * ((double) WINDOW_WIDTH / 900));
    
    public Main() {
        super("FEJKA the game");
    }
    
    /**
     * Loads the different states(screens/modes/menus) available and
     * enters the state specified with an id.
     * 
     * @param gc - The container given by Slick
     * @throws SlickException 
     */
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new HostScreenState("host"));
        addState(new MenuState("menu"));
        addState(new VersusState("arena2"));
        addState(new Tower01State("tower_1"));
        addState(new BigBlue02State("big_blue"));
        addState(new PracticeState("practice"));
        addState(new Tutorial03State("tutorial"));
        addState(new LevelSelectState());
        
        this.enterState(MENU);

    }
    
    public static void main(String[] args) {
        try {
                    //The gamecontainer used by Slick, this is where everything will be contained
                    AppGameContainer appgc = new AppGameContainer(new Main());
                    appgc.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, FULLSCREEN);
                    appgc.setDisplayMode(900, 500, false);
                    appgc.getFPS();
                    appgc.setShowFPS(true);
                    appgc.setTargetFrameRate(100);
                    appgc.start();
            } catch (SlickException ex) {
                    Logger.getLogger(ex.toString());
            }

    }
}
