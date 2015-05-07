package com.fejkathegame.game.entities;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.collision.AABoundingRect;
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

/**
 * Created by Swartt on 2015-04-28.
 */
public class Character extends LevelObject {

    private boolean grounded;
    private boolean isAlive;
    private boolean moving = false;

    private double sweepAttack, sweepLimit;

    private float jumpStrength;
    private float size;
    private float velocityY;
    private float velocityX;
    private float currentPositionX;
    private float currentPositionY;
    private float attackCoolDown;
    private float gravity;
    private float mousePositionX;
    private float previousMousePositionX;
    private float accelerationSpeed = 1;
    private float decelerationSpeed = 1;
    private float maximumSpeed = 2;
    private float playerWidth, playerHeight;
    private float jumpIndicatorTransp = 0.0f;
    private float attackIndicatorTransp = 0.0f;
    private float sweepXStart, sweepYStart, sweepXEnd, sweepYEnd, sweepSpeed;
    private float oldRotate;
    private float rotateDirection;
    private float calculatedXAttack, calculatedYAttack;
    private float attackVelocity;

    private int health;
    private int storedAttacks;
    private int storedJumps;

    private Vector2f attackDirection, attackStart, current;
    private HealthSystem healthSystem;
    private MovementSystem movementSystem;
    private Shape player;
    private Color color;
    private Image sprite;
    private Audio jumpSound, attackSound, chargeSound;
    private Shape jumpIndicator;
    private Polygon attackIndicator;
    private Ellipse superAttackIndicator;
    private boolean isCharging = false;
    private boolean isFullyCharged = false;

    /**
     * Constructor for creating a character, gives it the default values for a
     * character
     */
    public Character(float x, float y) throws SlickException, IOException {
        super(x, y);

        accelerationSpeed = 0.010f;
        maximumSpeed = 0.40f;
        maxFallSpeed = 0.75f;
        decelerationSpeed = 0.005f;
        sprite = new Image("src/main/resources/data/img/placeholder.png");
        boundingShape = new AABoundingRect(x, y, 32, 32);
        grounded = false;
        health = 5;
        attackCoolDown = 100;
        storedAttacks = 2;
        storedJumps = 0;
        size = 40;
        velocityY = 0;
        velocityX = 0;
        isAlive = true;
        this.healthSystem = new HealthSystem(this);
        this.movementSystem = new MovementSystem(this);
        gravity = 0.5f;
        jumpStrength = -15;
        jumpSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("src/main/resources/data/sound/Jump5.wav"));
        attackSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("src/main/resources/data/sound/Attack.wav"));
        chargeSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("src/main/resources/data/sound/Charge.wav"));
        sweepAttack = 4;
        sweepLimit = 10;
        attackVelocity = 1.4f;
        attackIndicator = new Polygon();
        attackIndicator.addPoint(0, 0);
        attackIndicator.addPoint(0, 4);
        attackIndicator.addPoint(48, 4);
        attackIndicator.addPoint(48, 0);
        jumpIndicator = new Rectangle(x, y, sprite.getWidth() + 4, 2);
        current = new Vector2f(x, y);
        superAttackIndicator = new Ellipse(x + 16, y + 16, 32, 32);
    }

    /**
     * Decelerates the Character, avoiding jerky movement
     *
     * @param delta
     */
    public void decelerate(int delta) {
        if (x_velocity > 0) {
            x_velocity -= decelerationSpeed * delta;
            if (x_velocity < 0) {
                x_velocity = 0;
            }
        } else if (x_velocity < 0) {
            x_velocity += decelerationSpeed * delta;
            if (x_velocity > 0) {
                x_velocity = 0;
            }
        }
    }

    /**
     * Launches the Character into the air upwards
     *
     * @param delta
     */
    public void jump(int delta) {
        currentPositionX = getX() - 2;
        currentPositionY = getY() + 32;

        if (!isOnGround()) {
            jumpIndicatorTransp = 1.0f;
        }

        y_velocity = -0.055f * (float) Math.sqrt(Math.pow(delta, 2));
        storedJumps--;
        jumpSound.playAsSoundEffect(1.0f, 1.0f, false);
    }

    /**
     * Moves the character to the left
     *
     * @param delta
     */
    public void moveLeft(int delta) {
        if (x_velocity > -maximumSpeed) {
            x_velocity -= accelerationSpeed * delta;
            if (x_velocity < -maximumSpeed) {
                x_velocity = -maximumSpeed;
            }
        }
        moving = true;
    }

    /**
     * moves the character to the right
     *
     * @param delta
     */
    public void moveRight(int delta) {
        if (x_velocity < maximumSpeed) {
            x_velocity += accelerationSpeed * delta;
            if (x_velocity > maximumSpeed) {
                x_velocity = maximumSpeed;
            }
        }
        moving = true;
    }

    /**
     * Charges the super attack.
     * 
     * Charges the attack as long as not interrupted.
     * If interrupted, the charge will be reset.
     * 
     * @param i
     * @param delta 
     */
    public void chargeSuperAttack(Input i, int delta) {
//        if (!chargeSound.isPlaying()) {
//            chargeSound.playAsSoundEffect(1.0f, 1.0f, false);
//        }
        float shrinking1 = superAttackIndicator.getRadius1() - (0.75f / delta);
        superAttackIndicator.setRadius1(shrinking1);
        superAttackIndicator.setRadius2(shrinking1);
        superAttackIndicator.setCenterX(getX() + 16);
        superAttackIndicator.setCenterY(getY() + 16);
        
        if (superAttackIndicator.getRadius1() < 16) {
            isFullyCharged = true;
            chargeSound.stop();
        }
        
    }
    
    /**
     * Plays the sound effect of charging the super attack.
     * 
     */
    public void playChargeSound() {
        if(isCharging) {
            if(!chargeSound.isPlaying()) {
            chargeSound.playAsSoundEffect(1.0f, 0.1f, false);
            }
        }
    }
    
    /**
     * Stops the charge effect of the super attack.
     * 
     */
    public void stopChargeSound() {
        if(!isCharging) {
            if(chargeSound.isPlaying()) {
                chargeSound.stop();
            }
        }
    }

    /**
     * Called when the super attack is fully charged.
     * 
     * Activates an attack that damages everthing within 900 radius
     * 
     * @param delta 
     */
    public void activateSuperAttack(int delta) {
        float expanding = superAttackIndicator.getRadius1() + (2 * delta);
        superAttackIndicator.setCenterX(getX());
        superAttackIndicator.setCenterY(getY());
        superAttackIndicator.setRadii(expanding, expanding);
        if (superAttackIndicator.getRadius1() > Main.WINDOW_WIDTH) {
            isFullyCharged = false;
            superAttackIndicator.setRadii(32, 32);
        }
    }

    /**
     * Preforms an attack, launching the Character in the direction of the
     * attack
     *
     * @param i
     * @param delta
     */
    public void attack(Input i, int delta) {
        sweepXStart = i.getMouseX();
        sweepYStart = i.getMouseY();

        if (sweepXEnd != sweepXStart && sweepYStart != sweepYEnd) {
            sweepSpeed = (float) Math.sqrt(Math.pow(sweepXStart - sweepXEnd, 2)
                    + Math.pow(sweepYStart - sweepYEnd, 2));
        }

        attackDirection = new Vector2f(sweepXStart - sweepXEnd,
                sweepYStart - sweepYEnd);

        if (sweepSpeed >= sweepAttack && sweepSpeed <= sweepLimit
                && attackCoolDown <= 0) { // Attack movement here
            setIsAttacking(true);
            attackStart = new Vector2f(x, y);

            calculatedXAttack = (float) ((attackVelocity) * Math.cos(Math.toRadians(attackDirection.getTheta())));
            calculatedYAttack = (float) ((attackVelocity) * Math.sin(Math.toRadians(attackDirection.getTheta())));

            rotateDirection = (float) attackDirection.getTheta();
            attackCoolDown = 1000;

            attackIndicator.setLocation(0, 0);
            attackIndicator = (Polygon) attackIndicator.transform(
                    Transform.createRotateTransform((float) Math.toRadians(rotateDirection - oldRotate)));
            attackIndicator.setLocation(x + 16, y + 16);

            storedAttacks--;
            attackSound.playAsSoundEffect(1.0f, 1.0f, false);
        }

        sweepXEnd = sweepXStart;
        sweepYEnd = sweepYStart;
        oldRotate = rotateDirection;
    }

    /**
     * sets a current location on every update and calculates distance before
     * slowing the player down.
     *
     * @param delta
     */
    public void update(int delta) {
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
        
        
        if (!isCharging && !isFullyCharged) {
            superAttackIndicator.setRadii(32, 32);
        }

        if (isFullyCharged) {
            activateSuperAttack(delta);
        }
    }

    /**
     * renders the character
     *
     * @throws SlickException
     */
    @Override
    public void render() throws SlickException {
        renderJumpIndicator(currentPositionX, currentPositionY);
        renderAttackIndicator();

        sprite.draw(x, y);

        healthSystem.render();

        if (isCharging || isFullyCharged) {
            g.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
            g.draw(superAttackIndicator);
        }
    }

    /**
     * Displays a white line under the character when he jumps in the air
     *
     * @param x
     * @param y
     */
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

    public boolean getIsCharging() {
        return isCharging;
    }

    public void setIsCharging(boolean isCharging) {
        this.isCharging = isCharging;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
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

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    @Override
    public boolean isMoving() {
        return moving;
    }

    @Override
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public double getSweepAttack() {
        return sweepAttack;
    }

    public void setSweepAttack(double sweepAttack) {
        this.sweepAttack = sweepAttack;
    }

    public double getSweepLimit() {
        return sweepLimit;
    }

    public void setSweepLimit(double sweepLimit) {
        this.sweepLimit = sweepLimit;
    }

    public float getJumpStrength() {
        return jumpStrength;
    }

    public void setJumpStrength(float jumpStrength) {
        this.jumpStrength = jumpStrength;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getCurrentPositionX() {
        return currentPositionX;
    }

    public void setCurrentPositionX(float currentPositionX) {
        this.currentPositionX = currentPositionX;
    }

    public float getCurrentPositionY() {
        return currentPositionY;
    }

    public void setCurrentPositionY(float currentPositionY) {
        this.currentPositionY = currentPositionY;
    }

    public float getAttackCoolDown() {
        return attackCoolDown;
    }

    public void setAttackCoolDown(float attackCoolDown) {
        this.attackCoolDown = attackCoolDown;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getMousePositionX() {
        return mousePositionX;
    }

    public void setMousePositionX(float mousePositionX) {
        this.mousePositionX = mousePositionX;
    }

    public float getPreviousMousePositionX() {
        return previousMousePositionX;
    }

    public void setPreviousMousePositionX(float previousMousePositionX) {
        this.previousMousePositionX = previousMousePositionX;
    }

    public float getAccelerationSpeed() {
        return accelerationSpeed;
    }

    public void setAccelerationSpeed(float accelerationSpeed) {
        this.accelerationSpeed = accelerationSpeed;
    }

    public float getDecelerationSpeed() {
        return decelerationSpeed;
    }

    public void setDecelerationSpeed(float decelerationSpeed) {
        this.decelerationSpeed = decelerationSpeed;
    }

    public float getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(float maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public float getPlayerWidth() {
        return playerWidth;
    }

    public void setPlayerWidth(float playerWidth) {
        this.playerWidth = playerWidth;
    }

    public float getPlayerHeight() {
        return playerHeight;
    }

    public void setPlayerHeight(float playerHeight) {
        this.playerHeight = playerHeight;
    }

    public float getJumpIndicatorTransp() {
        return jumpIndicatorTransp;
    }

    public void setJumpIndicatorTransp(float jumpIndicatorTransp) {
        this.jumpIndicatorTransp = jumpIndicatorTransp;
    }

    public float getAttackIndicatorTransp() {
        return attackIndicatorTransp;
    }

    public void setAttackIndicatorTransp(float attackIndicatorTransp) {
        this.attackIndicatorTransp = attackIndicatorTransp;
    }

    public float getSweepXStart() {
        return sweepXStart;
    }

    public void setSweepXStart(float sweepXStart) {
        this.sweepXStart = sweepXStart;
    }

    public float getSweepYStart() {
        return sweepYStart;
    }

    public void setSweepYStart(float sweepYStart) {
        this.sweepYStart = sweepYStart;
    }

    public float getSweepXEnd() {
        return sweepXEnd;
    }

    public void setSweepXEnd(float sweepXEnd) {
        this.sweepXEnd = sweepXEnd;
    }

    public float getSweepYEnd() {
        return sweepYEnd;
    }

    public void setSweepYEnd(float sweepYEnd) {
        this.sweepYEnd = sweepYEnd;
    }

    public float getSweepSpeed() {
        return sweepSpeed;
    }

    public void setSweepSpeed(float sweepSpeed) {
        this.sweepSpeed = sweepSpeed;
    }

    public float getOldRotate() {
        return oldRotate;
    }

    public void setOldRotate(float oldRotate) {
        this.oldRotate = oldRotate;
    }

    public float getRotateDirection() {
        return rotateDirection;
    }

    public void setRotateDirection(float rotateDirection) {
        this.rotateDirection = rotateDirection;
    }

    public float getCalculatedXAttack() {
        return calculatedXAttack;
    }

    public void setCalculatedXAttack(float calculatedXAttack) {
        this.calculatedXAttack = calculatedXAttack;
    }

    public float getCalculatedYAttack() {
        return calculatedYAttack;
    }

    public void setCalculatedYAttack(float calculatedYAttack) {
        this.calculatedYAttack = calculatedYAttack;
    }

    public float getAttackVelocity() {
        return attackVelocity;
    }

    public void setAttackVelocity(float attackVelocity) {
        this.attackVelocity = attackVelocity;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getStoredAttacks() {
        return storedAttacks;
    }

    @Override
    public void setStoredAttacks(int storedAttacks) {
        this.storedAttacks = storedAttacks;
    }

    @Override
    public int getStoredJumps() {
        return storedJumps;
    }

    @Override
    public void setStoredJumps(int storedJumps) {
        this.storedJumps = storedJumps;
    }

    public Vector2f getAttackDirection() {
        return attackDirection;
    }

    public void setAttackDirection(Vector2f attackDirection) {
        this.attackDirection = attackDirection;
    }

    public Vector2f getAttackStart() {
        return attackStart;
    }

    public void setAttackStart(Vector2f attackStart) {
        this.attackStart = attackStart;
    }

    public Vector2f getCurrent() {
        return current;
    }

    public void setCurrent(Vector2f current) {
        this.current = current;
    }

    public HealthSystem getHealthSystem() {
        return healthSystem;
    }

    public void setHealthSystem(HealthSystem healthSystem) {
        this.healthSystem = healthSystem;
    }

    public MovementSystem getMovementSystem() {
        return movementSystem;
    }

    public void setMovementSystem(MovementSystem movementSystem) {
        this.movementSystem = movementSystem;
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

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public Audio getJumpSound() {
        return jumpSound;
    }

    public void setJumpSound(Audio jumpSound) {
        this.jumpSound = jumpSound;
    }

    public Audio getAttackSound() {
        return attackSound;
    }

    public void setAttackSound(Audio attackSound) {
        this.attackSound = attackSound;
    }

    public Shape getJumpIndicator() {
        return jumpIndicator;
    }

    public void setJumpIndicator(Shape jumpIndicator) {
        this.jumpIndicator = jumpIndicator;
    }

    public Polygon getAttackIndicator() {
        return attackIndicator;
    }

    public void setAttackIndicator(Polygon attackIndicator) {
        this.attackIndicator = attackIndicator;
    }
}
