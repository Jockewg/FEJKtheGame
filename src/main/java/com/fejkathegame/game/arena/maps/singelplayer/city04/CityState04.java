package com.fejkathegame.game.arena.maps.singelplayer.city04;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.arena.maps.PracticeStateHelper;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.entities.logic.MovementSystem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 *
 * @author Swartt
 */
public class CityState04 extends State {

    private String name;

    private PracticeStateHelper stateHelper;
    
    private float offsetMaxX = 2050;
    private float offsetMaxY = 750;
    

    /**
     * Constructor for ArenaState
     * @param name of the stage
     */
    public CityState04(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return Main.CITY04;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        player = null;
        try {
            player = new Character(32, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        level = new City04(name, player);

        physics = new Physics();

        camera = new PracticeCamera(offsetMaxX, offsetMaxY);

        stateHelper = new PracticeStateHelper(this);
        
        

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
       stateHelper.render(g);
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        stateHelper.update(gc, sbg, i, 32, 40);
        level.helper.moveTarget();
    }
   
   
    


}
