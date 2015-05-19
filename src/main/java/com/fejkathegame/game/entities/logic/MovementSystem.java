package com.fejkathegame.game.entities.logic;

import com.fejkathegame.game.entities.Character;
import org.newdawn.slick.Input;

/**
 * Created by Swartt on 2015-04-28.
 */
public class MovementSystem {
    Character character;

    /**
     * Constructor, needs to know what {@code LevelObject} it has to apply the movement too
     * @param character
     */
    public MovementSystem(Character character) {
        this.character = character;
    }

    /**
     * Handles the player input from mouse and keyboard
     * @param i
     * @param delta
     */
    public void handleInput(Input i, int delta) {
            handleChargeAttack(i, delta);
        if(!character.getIsAttacking() && !character.getIsCharging()) {
            handleMouseInput(i, delta);
            handleKeyBoardInput(i, delta);
        }
    }

    /**
     * handles the mouse input from the player
     * @param i
     * @param delta
     */
    public void handleMouseInput(Input i, int delta) {
        character.setAttackCoolDown(character.getAttackCoolDown()-delta);
        if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON) && character.getStoredJumps() > 0||
                i.isKeyPressed(Input.KEY_SPACE) && character.getStoredJumps() > 0) {
            character.jump(delta);
        }
        if( i.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && character.getStoredAttacks() > 0) {
            character.attack(i, delta);
        }
    }

    /**
     * handles the keyboard input from the player
     * @param i
     * @param delta
     */
    public void handleKeyBoardInput(Input i, int delta) {
        if(i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)) {
            character.moveLeft(delta);
        } else if(i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)) {
            character.moveRight(delta);
        } else {
            character.setMoving(false);
            character.setMovingLeft(false);
            character.setMovingRight(false);
        }
        
        if(i.isKeyPressed(Input.KEY_Q)) {
            character.getHealthSystem().dealDamage(1);
        }
    }
    
    public void handleChargeAttack(Input i, int delta) {
        if(i.isKeyDown(Input.KEY_E) && !character.getIsFullyCharged()) {
            character.setMovingLeft(false);
            character.setMovingRight(false);
            character.setIsCharging(true);
            character.playChargeSound();
            character.setMoving(false);
            character.chargeSuperAttack(delta);
        } else {
            character.setIsCharging(false);
            character.stopChargeSound();
        }
    }
}
