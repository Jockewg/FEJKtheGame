package com.khamekaze.fejkathegame.arena;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Arena {
    
    private TiledMap map;
    
    public Arena(String name) throws SlickException {
        map = new TiledMap("data/levels/" + name + ".tmx", "data/img");
    }
    
    public void render() {
        map.render(0, 0);
    }
}
