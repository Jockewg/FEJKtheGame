package com.khamekaze.fejkathegame.Character;

import org.newdawn.slick.Input;

/**
 * Created by Swartt on 2015-04-28.
 */
public class MovementSystem {
    Character character;

    /**
     * Constructor, needs to know what character it has to apply the movement too
     * @param character
     */
    public MovementSystem(Character character) {
        this.character = character;
    }
    
    public void handleInput(Input i, int delta) {
        handleMouseInput(i, delta);
        handleKeyBoardInput(i, delta);
    }
    
    public void handleMouseInput(Input i, int delta) {
        if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON) && character.getStoredJumps() > 0||
                i.isKeyPressed(Input.KEY_SPACE) && character.getStoredJumps() > 0) {
            character.jump();
        }
    }
    
    public void handleKeyBoardInput(Input i, int delta) {
        if(i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)) {
            character.moveLeft(delta);
        } else if(i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)) {
            character.moveRight(delta);
        } else {
            character.setMoving(false);
        }
    }
}
