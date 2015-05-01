package com.fejkathegame.game.Character;

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
        character.setAttackCoolDown(character.getAttackCoolDown()-1);
        if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON) && character.getStoredJumps() > 0||
                i.isKeyPressed(Input.KEY_SPACE) && character.getStoredJumps() > 0) {
            character.jump();
        }
        if( i.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && character.getStoredAttacks() > 0) {
            character.attack(i, delta);
        }
    }
    
    public void handleKeyBoardInput(Input i, int delta) {
        if(i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)) {
            character.moveLeft(delta);
        } else if(i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)) {
            character.moveRight(delta);
        } else if(i.isKeyPressed(Input.KEY_Q)) {
            character.getHealthSystem().dealDamage(1);
        }
        else {
            character.setMoving(false);
        }
    }
}
