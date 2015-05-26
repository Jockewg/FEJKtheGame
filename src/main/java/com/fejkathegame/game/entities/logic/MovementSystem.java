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
       if (character.isCanUlti()) {
          handleChargeAttack(i, delta);
       }
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
            jump();
            character.playJumpSound();
            character.setHasJumped(true);
        } else {
            character.setHasJumped(false);
        }
        if( i.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && character.getStoredAttacks() > 0) {
            character.attackSystem.attack(i);
        }
    }

    /**
     * handles the keyboard input from the player
     * @param i
     * @param delta
     */
    public void handleKeyBoardInput(Input i, int delta) {
        if(i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)) {
            moveLeft(delta);
        } else if(i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)) {
            moveRight(delta);
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
        if(i.isKeyDown(Input.KEY_E) && !character.getIsFullyCharged() && character.isAlive()) {
            character.setMovingLeft(false);
            character.setMovingRight(false);
            character.setIsCharging(true);
            character.setMoving(false);
            character.attackSystem.chargeSuperAttack(delta);
        } else {
            character.setIsCharging(false);
            character.stopChargeSound();
        }
    }
    /**
     * Decelerates the Character, avoiding jerky movement
     *
     * @param delta
     */
    public void decelerate(int delta) {
        if (character.x_velocity > 0) {
            character.x_velocity -= character.decelerationSpeed * delta;
            if (character.x_velocity < 0) {
                character.x_velocity = 0;
            }
        } else if (character.x_velocity < 0) {
            character.x_velocity += character.decelerationSpeed * delta;
            if (character.x_velocity > 0) {
                character.x_velocity = 0;
            }
        }
    }
    /**
     * Launches the Character into the air upwards
     */
    public void jump() {
        character.currentPositionX = character.getX() - 2;
        character.currentPositionY = character.getY() + 32;

        character.fallAnimation.setCurrentFrame(0);
        character.fallAnimation.stop();

        if (!character.grounded) {
            character.jumpIndicatorTransp = 1.0f;
        }

        character.jumpAnimation.setCurrentFrame(0);
        character.jumpAnimation.start();

        character.y_velocity = -0.55f;
        character.storedJumps--;

    }
    public void checkMomentum() {
        if (character.y_velocity < 0 && !character.isAttacking) {
            character.isJumping = true;
            character.isFalling = false;
            character.grounded = false;
        } else if (character.y_velocity > 0 && !character.isAttacking) {
            character.isJumping = false;
            character.isFalling = true;
            character.grounded = false;
        } else if (character.onGround) {
            character.grounded = true;
            character.isJumping = false;
            character.isFalling = false;
        }
    }
    /**
     * Moves the character to the left
     *
     * @param delta
     */
    public void moveLeft(int delta) {
        character.movingRight = false;
        character.flipped = true;
        if (character.x_velocity > -character.maximumSpeed) {
            character.x_velocity -= character.accelerationSpeed * delta;

            if (character.x_velocity < -character.maximumSpeed) {
                character.x_velocity = -character.maximumSpeed;
            }
        }
        character.moving = true;
        character.movingLeft = true;
    }
    /**
     * moves the character to the right
     *
     * @param delta
     */
    public void moveRight(int delta) {
        character.movingLeft = false;
        character.flipped = false;
        if (character.x_velocity < character.maximumSpeed) {
            character.x_velocity += character.accelerationSpeed * delta;

            if (character.x_velocity > character.maximumSpeed) {
                character.x_velocity = character.maximumSpeed;
            }
        }
        character.moving = true;
        character.movingRight = true;

    }
}
