package com.fejkathegame.game.entities;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.collision.AABoundingRect;
import com.fejkathegame.game.entities.logic.AttackSystem;
import com.fejkathegame.game.entities.logic.HealthSystem;
import com.fejkathegame.game.entities.logic.MovementSystem;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Swartt on 2015-04-28.
 */
public class Character extends LevelObject {

    public Vector2f networkPosition = new Vector2f(0, 0);
    public boolean grounded = false;
    public boolean flipped;
    public boolean movingRight = false;
    public boolean movingLeft = false;
    public boolean isJumping, isFalling, hasJumped;
    private boolean ready = false;
    private boolean canUlti = true;
    public int numberOfAttacks = 0;
    private int numberOfHits = 0;
    private int hitPercent = 0;

    private SpriteSheet runningSheet;
    private Animation runningAnimation;
    private SpriteSheet stanceSheet;
    private Animation stanceAnimation;
    private SpriteSheet jumpSheet;
    public Animation jumpAnimation;
    private SpriteSheet fallingSheet;
    public Animation fallAnimation;
    private SpriteSheet chargingSheet;
    private Animation chargingAnimation;
    private SpriteSheet chargeReleaseSheet;
    public Animation chargeReleaseAnimation;
    public Image explosion;
    private SpriteSheet charginParticleSheet;
    private Animation chargingParticleAnimation;

    public double sweepAttack, sweepLimit;
    private float currentX = 0, currentY = 0;
    private float jumpStrength;
    private float size;
    public float currentPositionX;
    public float currentPositionY;
    public float attackCoolDown, stopAttack;
    private float gravity;
    private float mousePositionX;
    private float previousMousePositionX;
    public float accelerationSpeed = 1;
    public float maximumSpeed = 2;
    private float playerWidth, playerHeight;
    public float jumpIndicatorTransp = 0.0f;
    private float attackIndicatorTransp = 0.0f;
    public float sweepXStart, sweepYStart, sweepXEnd, sweepYEnd, sweepSpeed;
    public float oldRotate;
    public float rotateDirection;
    public float calculatedXAttack, calculatedYAttack;
    public float attackVelocity;

    public Vector2f attackDirection = new Vector2f(0, 0), attackStart, current;
    public MovementSystem movementSystem;
    private Shape player;
    private Color color;
    public Audio jumpSound, attackSound, chargeActivationSound, chargeAttackSound;
    private Shape jumpIndicator;
    public Polygon attackIndicator;
    public Ellipse superAttackIndicator;
    private boolean isCharging = false;
    public boolean isFullyCharged = false;
    private Image[] numberOfJumps = new Image[2];
    private Image attackCharger;
    private float attackChargeSize = 0;
    private String name;
    public AttackSystem attackSystem;

    /**
     * Constructor for creating a character, gives it the default values for a
     * character
     */
    public Character(float x, float y) throws SlickException, IOException {
        super(x, y);

        accelerationSpeed = 0.005f;
        maximumSpeed = 0.40f;
        maxFallSpeed = 0.75f;
        decelerationSpeed = 0.003f;
        boundingShape = new AABoundingRect(x, y, 19, 25);
        health = 5;
        attackCoolDown = 100;
        stopAttack = 100;
        storedAttacks = 2;
        storedJumps = 0;
        size = 40;
        isAlive = true;
        healthSystem = new HealthSystem(this);
        movementSystem = new MovementSystem(this);
        attackSystem = new AttackSystem(this);
        gravity = 0.5f;
        jumpStrength = -15;
        jumpSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("src/main/resources/data/sound/Jump5.wav"));
        attackSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("src/main/resources/data/sound/Attack.wav"));
        chargeActivationSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("src/main/resources/data/sound/Charge.wav"));
        chargeAttackSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("src/main/resources/data/sound/ChargeExplosion.wav"));
        sweepAttack = 4;
        sweepLimit = 10;
        attackVelocity = 1.4f;
        attackIndicator = new Polygon();
        attackIndicator.addPoint(0, 0);
        attackIndicator.addPoint(0, 4);
        attackIndicator.addPoint(48, 4);
        attackIndicator.addPoint(48, 0);
//        jumpIndicator = new Rectangle(x, y, sprite.getWidth() + 4, 2);
        current = new Vector2f(x, y);
        superAttackIndicator = new Ellipse(x + 16, y + 16, 32, 32);
        hitBox = new Rectangle(x, y, 32, 32);

        loadStoredJumpsIndicator();
        attackCharger = new Image("src/main/resources/data/img/statusBar/attackCharge/attackCharge.png");

        loadCharacterAnimations();

    }

    public void loadCharacterAnimations() throws SlickException {
        runningSheet = new SpriteSheet("src/main/resources/data/img/spritesheets/spritesheet3.png", 192, 192);
        runningAnimation = new Animation(runningSheet, 30);
        runningAnimation.setAutoUpdate(false);

        stanceSheet = new SpriteSheet("src/main/resources/data/img/spritesheets/charStance.png", 112, 112);
        stanceAnimation = new Animation(stanceSheet, 120);
        stanceAnimation.setAutoUpdate(false);

        jumpSheet = new SpriteSheet("src/main/resources/data/img/spritesheets/spriteSheetJump1.png", 415, 415);
        jumpAnimation = new Animation(jumpSheet, 30);
        jumpAnimation.setAutoUpdate(false);
        jumpAnimation.setLooping(false);

        fallingSheet = new SpriteSheet("src/main/resources/data/img/spritesheets/spriteSheetFalling1.png", 415, 415);
        fallAnimation = new Animation(fallingSheet, 60);
        fallAnimation.setAutoUpdate(false);

        chargingSheet = new SpriteSheet("src/main/resources/data/img/spritesheets/chargeUpSheet.png", 415, 415);
        chargingAnimation = new Animation(chargingSheet, 60);
        chargingAnimation.setAutoUpdate(false);

        chargeReleaseSheet = new SpriteSheet("src/main/resources/data/img/spritesheets/chargeReleaseSheet.png", 415, 415);
        chargeReleaseAnimation = new Animation(chargeReleaseSheet, 40);
        chargeReleaseAnimation.setAutoUpdate(false);
//        chargeReleaseAnimation.setLooping(false);
        chargeReleaseAnimation.setCurrentFrame(0);

        explosion = new Image("src/main/resources/data/img/spritesheets/explosion1.png");

        charginParticleSheet = new SpriteSheet("src/main/resources/data/img/spritesheets/chargingSheet.png", 434, 434);
        chargingParticleAnimation = new Animation(charginParticleSheet, 30);
        chargingParticleAnimation.setAutoUpdate(false);
    }

    public void loadStoredJumpsIndicator() throws SlickException {
        numberOfJumps[0] = new Image("src/main/resources/data/img/statusBar/jump/jumpCounter.png");
        numberOfJumps[1] = new Image("src/main/resources/data/img/statusBar/jump/jumpCounterEmpty.png");
    }



    public void playJumpSound() {
//        if(!jumpSound.isPlaying())
        jumpSound.playAsSoundEffect(1.0f, 1.0f, false);
    }

    public void playAttackSound() {
        if (!attackSound.isPlaying())
            attackSound.playAsSoundEffect(1.0f, 1.0f, false);
    }

    /**
     * Plays the sound effect of charging the super attack.
     */
    public void playChargeSound() {
        if (isCharging) {
            if (!chargeActivationSound.isPlaying()) {
                chargeActivationSound.playAsSoundEffect(1.0f, 1.0f, false);
            }
        }
    }

    /**
     * Stops the charge effect of the super attack.
     */
    public void stopChargeSound() {
        if (!isCharging) {
            if (chargeActivationSound.isPlaying()) {
                chargeActivationSound.stop();
            }
        }
    }

    /**
     * sets a current location on every update and calculates distance before
     * slowing the player down.
     *
     * @param delta
     */
    public void update(int delta) {
        currentX = x;
        currentY = y;
        if (getIsAttacking()) {
            current = new Vector2f(x, y);
            attackIndicatorTransp = 1.0f;
            x_velocity = calculatedXAttack;
            y_velocity = calculatedYAttack;
            gravity = 0.0f;
            if (attackStart.distance(current) >= 150) {
                gravity = 0.0015f * delta;
                y_velocity = 0;
                if (x_velocity < -maximumSpeed) {
                    x_velocity = -maximumSpeed;
                } else if (x_velocity > maximumSpeed) {
                    x_velocity = maximumSpeed;
                }
                setIsAttacking(false);
            }
        }

        runningAnimation.update(delta);
        stanceAnimation.update(delta);
        jumpAnimation.update(delta);
        fallAnimation.update(delta);
        chargingAnimation.update(delta);
        chargeReleaseAnimation.update(delta);
        chargingParticleAnimation.update(delta);
        healthSystem.damageCooldown(delta);

        movementSystem.checkMomentum();

        if (!isCharging && !isFullyCharged) {
            superAttackIndicator.setRadii(32, 32);
        }

        if (isFullyCharged) {
            attackSystem.activateSuperAttack(delta);
        }

        if (getIsAttacking()) {
            if (stopAttack <= 0) {
                setIsAttacking(false);
            }
            stopAttack--;
        } else {
            stopAttack = 100;
        }
    }

    public void renderStoredJumpsIndicator(float x, float y) {
        if (storedJumps == 2) {
            numberOfJumps[0].getScaledCopy(0.1f).draw(x - 1, y);
            numberOfJumps[0].getScaledCopy(0.1f).draw(x - 1, y + 16);
        } else if (storedJumps == 1) {
            numberOfJumps[0].getScaledCopy(0.1f).draw(x - 1, y);
            numberOfJumps[1].getScaledCopy(0.1f).draw(x - 1, y + 16);
        } else {
            numberOfJumps[1].getScaledCopy(0.1f).draw(x - 1, y);
            numberOfJumps[1].getScaledCopy(0.1f).draw(x - 1, y + 16);
        }
    }

    public void renderAttackCharge(float x, float y) {
        if (attackCoolDown < 1000) {
            attackChargeSize += 1.35f;
        } else if (attackCoolDown <= 0) {
            attackChargeSize = 135;
        } else {
            attackChargeSize = 0;
        }

        if (attackChargeSize >= 135) {
            attackChargeSize = 135;
        }
        attackCharger.draw(x, y, attackChargeSize, 8);
    }

    public void renderAttackChargeReversed(float x, float y) {
        if (attackCoolDown < 1000) {
            attackChargeSize -= 1.35f;
        } else if (attackCoolDown <= 0) {
            attackChargeSize = -135;
        } else {
            attackChargeSize = 0;
        }

        if (attackChargeSize <= -135) {
            attackChargeSize = -135;
        }
        attackCharger.getFlippedCopy(true, false).draw(x, y, attackChargeSize, 8);
    }

    public void renderCharacterAnimation() {
        if (isCharging) {
            playChargeSound();
            if (!flipped) {
                chargingAnimation.draw(x - 2, y, 32, 32);
                chargingParticleAnimation.draw(x - 14, y - 6, 48, 48);
            } else {
                chargingAnimation.getCurrentFrame().getFlippedCopy(true, false).draw(x - 16, y, 32, 32);
                chargingParticleAnimation.draw(x - 11, y - 6, 48, 48);
            }
        } else if (isFullyCharged) {
            stopChargeSound();
            if (!flipped) {
                chargeReleaseAnimation.draw(x - 4, y - 6, 32, 32);
            } else {
                chargeReleaseAnimation.getCurrentFrame().getFlippedCopy(true, false).draw(x - 14, y - 6, 32, 32);
            }

        } else if (movingRight && grounded) {
            runningAnimation.draw(x - 4, y - 2, 27, 27);
        } else if (movingLeft && grounded) {
            runningAnimation.getCurrentFrame().getFlippedCopy(true, false).draw(x - 9, y - 2, 27, 27);
        } else if (!movingLeft && !movingRight && grounded) {
            if (flipped) {
                stanceAnimation.getCurrentFrame().getFlippedCopy(true, false).draw(x - 9, y - 2, 27, 27);
            } else {
                stanceAnimation.draw(x - 4, y - 2, 27, 27);
            }
        } else if (isJumping) {
            if (flipped) {
                jumpAnimation.getCurrentFrame().getFlippedCopy(true, false).draw(x - 9, y - 2, 27, 27);
            } else {
                jumpAnimation.draw(x - 4, y - 2, 27, 27);
            }
        } else if (isFalling) {
            if (flipped) {
                fallAnimation.setCurrentFrame(0);
                fallAnimation.getCurrentFrame().getFlippedCopy(true, false).draw(x - 7, y - 2, 27, 27);
            } else {
                fallAnimation.setCurrentFrame(0);
                fallAnimation.draw(x - 6, y - 2, 27, 27);
            }
        }
    }

    /**
     * renders the character
     *
     * @throws SlickException
     */
    @Override
    public void render() throws SlickException {
//        renderJumpIndicator(currentPositionX, currentPositionY);
        if (isAlive) {
            renderAttackIndicator();
            updateHitBox();
            attackSystem.renderExplosion();

            renderCharacterAnimation();
        }
    }

    /**
     * Displays a white line under the character when he jumps in the air
     *
     * @param x
     * @param y
     */
    @Deprecated
    public void renderJumpIndicator(float x, float y) {
        g.setColor(new Color(1.0f, 1.0f, 1.0f, jumpIndicatorTransp));
        jumpIndicator.setX(x);
        jumpIndicator.setY(y);
        g.fill(jumpIndicator);
        jumpIndicatorTransp -= 0.02f;
    }

    /**
     * Shows an indicator of the characters attack
     */
    public void renderAttackIndicator() {
        attackIndicator.setX(x + 16);
        attackIndicator.setY(y + 16);
        g.setColor(new Color(1.0f, 1.0f, 1.0f, attackIndicatorTransp));
        g.fill(attackIndicator);

        attackIndicatorTransp -= 0.07f;
    }


    //      *******************************
//      *    Getters and Setters      *
//      *******************************
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean getMovingRight() {
        return movingRight;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean getMovingLeft() {
        return movingLeft;
    }

    public boolean getIsCharging() {
        return isCharging;
    }

    public void setIsCharging(boolean isCharging) {
        this.isCharging = isCharging;
    }

    public void setIsFullyCharged(boolean isFullyCharged) {
        this.isFullyCharged = isFullyCharged;
    }

    public boolean getIsFullyCharged() {
        return isFullyCharged;
    }

    public Ellipse getSuperAttackIndicator() {
        return superAttackIndicator;
    }


    public float getAttackCoolDown() {
        return attackCoolDown;
    }

    public void setAttackCoolDown(float attackCoolDown) {
        this.attackCoolDown = attackCoolDown;
    }

    public void setAttackIndicatorTransp(float attackIndicatorTransp) {
        this.attackIndicatorTransp = attackIndicatorTransp;
    }

    public void setRotateDirection(float rotateDirection) {
        this.rotateDirection = rotateDirection;
    }

    public Vector2f getAttackDirection() {
        return attackDirection;
    }

    public Shape getPlayer() {
        return player;
    }

    public void setPlayer(Shape player) {
        this.player = player;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public Polygon getAttackIndicator() {
        return attackIndicator;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getCurrentX() {
        return currentX;
    }

    public float getCurrentY() {
        return currentY;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setIsJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setIsFalling(boolean isFalling) {
        this.isFalling = isFalling;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public boolean getGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public void setHasJumped(boolean hasJumped) {
        this.hasJumped = hasJumped;
    }

    public String getName() {
        if (name == null) {
            return "HELLO";
        } else
            return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isCanUlti() {
        return canUlti;
    }

    public void setCanUlti(boolean canUlti) {
        this.canUlti = canUlti;
    }

    public int getNumberOfAttacks() {
        return numberOfAttacks;
    }

    public void setNumberOfHits(int hits) {
        this.numberOfHits = hits;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public MovementSystem getMovementSystem() {
        return movementSystem;
    }
}
