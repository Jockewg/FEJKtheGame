/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Filip
 */
public class MovementTest extends StateBasedGame{

    public MovementTest() {
        super("Movement Test");
    }

    public static void main(String[] args) {
        try {
            //The gamecontainer used by Slick, this is where everything will be contained
            AppGameContainer appgc = new AppGameContainer(new MovementTest());
            appgc.setDisplayMode(900, 500, false);
            appgc.getFPS();
            appgc.setShowFPS(true);
            appgc.setTargetFrameRate(100);
            appgc.setAlwaysRender(true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(ex.toString());
        }

    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
    }
}
