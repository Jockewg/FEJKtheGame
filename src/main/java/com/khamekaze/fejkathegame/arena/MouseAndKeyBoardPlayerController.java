package com.khamekaze.fejkathegame.arena;

import org.newdawn.slick.Input;

public class MouseAndKeyBoardPlayerController {
    
    private Player player;
    
    public MouseAndKeyBoardPlayerController(Player player) {
        this.player = player;
    }
    
    public void handleInput(Input i, int delta) {
        handleKeyBoardInput(i, delta);
        
    }
    
    private void handleKeyBoardInput(Input i, int delta) {
        if(i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)) {
            player.moveLeft(delta);
        } else if(i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)) {
            player.moveRight(delta);
        } else {
            player.setMoving(false);
        }
        
        if(i.isKeyDown(Input.KEY_SPACE)) {
            player.jump();
        }
    }
    
    private void handleMouseInput(Input i, int delta) {
        if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            
        }
    }
    
}
