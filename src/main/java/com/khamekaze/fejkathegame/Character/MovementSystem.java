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


        character.getPlayer().setY(character.getPlayer().getY() + 0.1f);
        if (HITDETECTION) {
            character.setStoredJumps(2);
            character.setVelocityY(character.getJumpStrength());
            character.setStoredJumps(-1);
        }
        character.getPlayer().setY(character.getPlayer().getY() - 0.1f);
        if (character.getStoredJumps() >= 0) {
            character.setVelocityY(character.getJumpStrength());
            character.setStoredJumps(-1);
        }

    }

    public void regainStoredJumps() {

    }

    public void gravity() {
        float i = character.getVelocityY();
        character.setVelocityY(i += character.getGravity());

        character.getPlayer().setY(character.getPlayer().getY() + character.getVelocityY());
        if (HITDETECTION) { //CHECK HIT DETECTiON
            character.getPlayer().setY(character.getPlayer().getY() - character.getVelocityY());
        }
    }
}
