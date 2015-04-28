package com.khamekaze.fejkathegame.Character;

import org.newdawn.slick.Input;

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
            regainStoredJumps();
            character.setVelocityY(character.getJumpStrength());
            character.setStoredJumps(-1);
        }
        character.getPlayer().setY(character.getPlayer().getY() - 0.1f);
        if (character.getStoredJumps() >= 0) {
            character.setVelocityY(character.getJumpStrength());
            character.setStoredJumps(-1);
        }

    }

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
        } else {

        }
    }


    public void regainStoredJumps() {
        character.setStoredJumps(2);
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
