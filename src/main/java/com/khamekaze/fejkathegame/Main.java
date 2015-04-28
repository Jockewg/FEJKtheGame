package com.khamekaze.fejkathegame;

import com.khamekaze.fejkathegame.game.Game;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
    
    public static void main(String[] args) {
        try {
                    AppGameContainer appgc;
                    appgc = new AppGameContainer(new Game("FEJKA: The Game"));
                    appgc.setDisplayMode(900, 500, false);
                    appgc.setTargetFrameRate(60);
                    appgc.setShowFPS(false);
                    appgc.start();
            } catch (SlickException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

}
