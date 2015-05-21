package com.fejkathegame.game;

import com.fejkathegame.game.arena.maps.singelplayer.tower02.Tower02State;
import com.fejkathegame.game.arena.maps.singelplayer.bigblue03.BigBlue02State;
import com.fejkathegame.game.arena.maps.multiplayer.versus01.VersusState;
import com.fejkathegame.game.arena.maps.singelplayer.city04.CityState04;
import com.fejkathegame.game.arena.maps.singelplayer.tutorial01.Tutorial01State;
import com.fejkathegame.game.multiplayer.lobby.LobbyState;
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
    //menu constants
    public static final int MENU = 0;
    public static final int VERSUSSTATE = 1;
    public static final int HOSTSTATE = 2;
    public static final int LEVELSELECTSTATE = 3;
    public static final int LOBBYSTATE = 1337;
    public static final int STATSSTATE = 1338;
    //singelplayer constants
    public static final int BIG_BlUE03 = 4;
    public static final int CITY04 = 5;
    public static final int TOWER02 = 6;
    public static final int TUTORIAL01 = 7;






    
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
        //add menus
        addState(new HostScreenState("host"));
        addState(new MenuState("menu"));
        addState(new LevelSelectState());
        //add multiplayer maps
        //add singelplayermaps
        addState(new Tutorial01State("tutorial01"));
        addState(new Tower02State("tower02"));
        addState(new BigBlue02State("big_blue04"));
        addState(new CityState04("city03"));

        
        this.enterState(MENU);

    }
    
    public static void main(String[] args) {
        try {
                    //The gamecontainer used by Slick, this is where everything will be contained
                    AppGameContainer appgc = new AppGameContainer(new Main());
                    appgc.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, FULLSCREEN);
                    appgc.setDisplayMode(900, 500, false);
                    appgc.getFPS();
                    appgc.setShowFPS(false);
                    appgc.setTargetFrameRate(100);
                    appgc.setAlwaysRender(true);
                    appgc.start();
            } catch (SlickException ex) {
                    Logger.getLogger(ex.toString());
            }

    }
}
