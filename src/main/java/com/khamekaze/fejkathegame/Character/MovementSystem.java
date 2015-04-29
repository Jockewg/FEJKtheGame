package com.khamekaze.fejkathegame.Character;

import com.khamekaze.fejkathegame.physics.Physics;
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


    /**
     * Moves the character horizontally upwards, check is the player is either on the ground (if true then regain all jumps)
     * or if he has stored jumps left
     */
    public void jump() {
        /*character.getPlayer().setY(character.getPlayer().getY() + 0.1f);*/
        if (character.isGrounded()) {
            regainStoredJumps();
            character.setVelocityY(character.getJumpStrength());
            character.setStoredJumps(-1);
        }
        /*character.getPlayer().setY(character.getPlayer().getY() - 0.1f);*/
        if (character.getStoredJumps() >= 0) {
            character.setVelocityY(character.getJumpStrength());
            character.setStoredJumps(-1);
        }

    }

    /**
     * will move the character left or right along the X axel depending on mouse movement
     */
    public void move() {
        if (character.getMousePositionX() > character.getPreviousMousePositionX()) {
            float tempvelx = character.getVelocityX();
            if (character.getVelocityX() < 5)
                character.setVelocityX(tempvelx += 0.2);
            character.getPlayer().setCenterX(character.getCurrentPositionX() + character.getVelocityX());
            character.setCurrentPositionX(character.getPlayer().getCenterX());

        } else if (character.getMousePositionX() > character.getPreviousMousePositionX()) {
            float tempvelx = character.getVelocityX();
            if (character.getVelocityX() > 5)
                character.setVelocityX(tempvelx -= 0.2);
            character.getPlayer().setCenterX(character.getCurrentPositionX() + character.getVelocityX());
            character.setCurrentPositionX(character.getPlayer().getCenterX());
        }
        else {
            if(character.getVelocityX() < 0) {
                float temp = character.getVelocityX();
                character.setVelocityX(temp += 0.2f);
            }
            if(character.getVelocityX() > 0) {
                float temp = character.getVelocityX();
                character.setVelocityX(temp -= 0.2f);
            }
            if(character.getVelocityX() < 0.2 && character.getVelocityX() > -0.2) {
                character.setVelocityX(0);
                character.getPlayer().setCenterX(character.getCurrentPositionX() + character.getVelocityX());
                character.setCurrentPositionX(character.getPlayer().getCenterX());
            }

        }
        character.setMoving(true);
    }


    /**
     * restores the amount of stored jumps a character has
     */
    public void regainStoredJumps() {
        character.setStoredJumps(2);
    }

    /**
     * Applies gravity on the character
     *
     * LEGACY
     */
    public void gravity() {
        float i = character.getVelocityY();
        character.setVelocityY(i += character.getGravity());

        character.getPlayer().setY(character.getPlayer().getY() + character.getVelocityY());
        if (true) { //CHECK HIT DETECTiON
            character.getPlayer().setY(character.getPlayer().getY() - character.getVelocityY());
        }
    }
}
