package com.fejkathegame.game.arena.versus;

import com.fejkathegame.game.entities.logic.MovementSystem;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.entities.Character;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

public class VersusState extends BasicGameState {

    private Versus arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private Character obj;

    /**
     * Constructor for ArenaState
     * @param name of the stage
     */
    public VersusState(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        obj = null;
        try {
            obj = new Character(800, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }


        arena = new Versus(name, obj);
        
        movementSystem = new MovementSystem(obj);

        /*playerController = new MouseAndKeyBoardPlayerController(player);*/

        physics = new Physics();

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.scale(Main.SCALE, Main.SCALE);
        arena.render();
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
//        playerController.handleInput(gc.getInput(), i);
        movementSystem.handleInput(gc.getInput(), i);
        physics.handlePhysics(arena, i);
        obj.update(i);
    }

}
