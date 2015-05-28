package com.fejkathegame.game.entities.logic;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.entities.*;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

/**
 * The character attack system.
 * 
 * Created by Swartt on 2015-05-26.
 */
public class AttackSystem {
    Character character;

    /**
     * a constructor accepting a character
     * 
     * @param character 
     */
    public AttackSystem(Character character) {
        this.character = character;
    }
    
    /**
     * The explostion animation.
     */
    public void renderExplosion() {
        if (character.isFullyCharged) {
            character.explosion.draw(character.superAttackIndicator.getX(),
                    character.superAttackIndicator.getY(),
                    character.superAttackIndicator.getRadius1() * 2,
                    character.superAttackIndicator.getRadius2() * 2);
        }
    }
    /**
     * Charges the super attack.
     * <p/>
     * Charges the attack as long as not interrupted. If interrupted, the charge
     * will be reset.
     *
     * @param delta
     */
    public void chargeSuperAttack(int delta) {
        float shrinking1 = character.superAttackIndicator.getRadius1() - (0.75f / delta);
        character.superAttackIndicator.setRadius1(shrinking1);
        character.superAttackIndicator.setRadius2(shrinking1);
        character.superAttackIndicator.setCenterX(character.getX() + 9);
        character.superAttackIndicator.setCenterY(character.getY() + 13);

        if (character.superAttackIndicator.getRadius1() < 16) {
            character.isFullyCharged = true;
            character.chargeActivationSound.stop();
        }

    }
    /**
     * Called when the super attack is fully charged.
     * <p/>
     * Activates an attack that damages everthing within 900 radius
     *
     * @param delta the update time per frame.
     */
    public void activateSuperAttack(int delta) {
        character.chargeAttackSound.playAsSoundEffect(1.0f, 1.0f, false);
        float expanding = character.superAttackIndicator.getRadius1() + (2 * delta);
        character.superAttackIndicator.setCenterX(character.getX());
        character.superAttackIndicator.setCenterY(character.getY());
        character.superAttackIndicator.setRadii(expanding, expanding);
        if (character.chargeReleaseAnimation.isStopped() && character.chargeReleaseAnimation.getFrame() == 0) {
            character.chargeReleaseAnimation.start();
        }

        if (character.chargeReleaseAnimation.getFrame() == 7) {
            character.chargeReleaseAnimation.setCurrentFrame(7);
            character.chargeReleaseAnimation.stop();
        }

        if (character.superAttackIndicator.getRadius1() > Main.WINDOW_WIDTH) {
            character.isFullyCharged = false;
            character.superAttackIndicator.setRadii(32, 32);
            character.chargeReleaseAnimation.stop();
            character.chargeReleaseAnimation.setCurrentFrame(0);
        }
    }
    /**
     * Preforms an attack, launching the Character in the direction of the
     * attack
     *
     * @param i
     */
    public void attack(Input i) {
        character.sweepXStart = i.getMouseX();
        character.sweepYStart = i.getMouseY();

        if (character.sweepXEnd != character.sweepXStart && character.sweepYStart != character.sweepYEnd) {
            character.sweepSpeed = (float) Math.sqrt(Math.pow(character.sweepXStart - character.sweepXEnd, 2)
                    + Math.pow(character.sweepYStart - character.sweepYEnd, 2));
        }

        character.attackDirection.set(character.sweepXStart - character.sweepXEnd,
                character.sweepYStart - character.sweepYEnd);

        if (character.sweepSpeed >= character.sweepAttack && character.sweepSpeed <= character.sweepLimit
                && character.attackCoolDown <= 0) { // Attack movement here
            character.setIsAttacking(true);
            character.attackStart = new Vector2f(character.x, character.y);

            character.calculatedXAttack = (float) ((character.attackVelocity) * Math.cos(Math.toRadians(character.attackDirection.getTheta())));
            character.calculatedYAttack = (float) ((character.attackVelocity) * Math.sin(Math.toRadians(character.attackDirection.getTheta())));

            character.rotateDirection = (float) character.attackDirection.getTheta();
            character.attackCoolDown = 1000;

            updateAttackIndicator();

            character.storedAttacks--;
            character.numberOfAttacks++;
            if (character.sweepXEnd > character.sweepXStart) {
                character.flipped = true;
            } else if (character.sweepXStart > character.sweepXEnd) {
                character.flipped = false;
            }


        }

        character.sweepXEnd = character.sweepXStart;
        character.sweepYEnd = character.sweepYStart;

    }
    
    /**
     * Update the attack indicator to the degree the attack is.
     */
    public void updateAttackIndicator() {
        character.attackIndicator.setLocation(0, 0);
        character.attackIndicator = (Polygon) character.attackIndicator.transform(
                Transform.createRotateTransform((float) Math.toRadians(character.rotateDirection - character.oldRotate)));
        character.attackIndicator.setLocation(character.x + 16, character.y + 16);
        character.oldRotate = character.rotateDirection;
        character.playAttackSound();
    }
}
