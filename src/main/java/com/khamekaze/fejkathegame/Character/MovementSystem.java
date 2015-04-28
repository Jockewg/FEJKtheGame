package com.khamekaze.fejkathegame.Character;

/**
 * Created by Swartt on 2015-04-28.
 */
public class MovementSystem {
    Character character;
    public MovementSystem(Character character) {
        this.character = character;
    }

    public void jump() {
        if (character.getStoredJumps() < 0 && character.getCanJump() == true) {
            //Update character position
            //character.setCurrentPositionY(character.getCurrentPositionY() + character.getVelocityY());
        }
    }
    public void falling() {
        if (!character.isGrounded()) {

        }
    }
    public void gravity () {
        character.setVelocityY(character.getVelocityY() += character.getGravity());

        character.getPlayer().setY(character.getPlayer().getY() + character.getVelocityY());
    }
}
