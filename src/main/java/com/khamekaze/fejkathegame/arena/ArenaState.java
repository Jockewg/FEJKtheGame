package com.khamekaze.fejkathegame.arena;

import com.khamekaze.fejkathegame.Character.*;
import com.khamekaze.fejkathegame.Main;
import com.khamekaze.fejkathegame.physics.Physics;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ArenaState extends BasicGameState {
    
    private Arena arena;
    private String name;
    private Player player;
    private MouseAndKeyBoardPlayerController playerController;
    private Physics physics;
    
    public ArenaState(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        
        player = new Player(800, 40);

        arena = new Arena(name, player);
        
        playerController = new MouseAndKeyBoardPlayerController(player);
        
        physics = new Physics();

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.scale(Main.SCALE, Main.SCALE);
        arena.render();
    }
    

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        playerController.handleInput(gc.getInput(), i);
        physics.handlePhysics(arena, i);
    }
    
}
