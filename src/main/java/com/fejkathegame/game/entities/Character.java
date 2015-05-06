package com.fejkathegame.game.entities;

import com.fejkathegame.game.entities.logic.HealthSystem;
import com.fejkathegame.game.entities.logic.MovementSystem;
import com.fejkathegame.game.arena.collision.AABoundingRect;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;

import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Created by Swartt on 2015-04-28.
 */
public class Character extends LevelObject {

    private boolean grounded;
    private int health;
    private float attackCoolDown;
    private int storedAttacks;
    private int storedJumps;
    private float jumpStrength;
    private float size;
    private float velocityY;
    private float velocityX;
    private float currentPositionX;
    private float currentPositionY;
    private boolean isAlive;
    private HealthSystem healthSystem;
    private MovementSystem movementSystem;
    private Shape player;
    private float gravity;
    private Color color;
    private static int updateRate;
    private float mousePositionX;
    private float previousMousePositionX;
    private long jumpCoolDownTick;
    private long jumpCoolDownDefault;
    protected float accelerationSpeed = 1;
    protected float decelerationSpeed = 1;
    protected float maximumSpeed = 2;
    protected boolean moving = false;
    protected Image sprite;
    protected Audio jumpSound, attackSound;
    private Shape jumpIndicator, attackIndicator;
    private float jumpIndicatorTransp = 0.0f;
    private float attackIndicatorTransp = 0.0f;
    private float sweepXStart, sweepYStart, sweepXEnd, sweepYEnd, sweepSpeed;
    private double sweepAttack, sweepLimit;
    private float attackVelocity;
    private Vector2f direction;
    private float rotateDirection;

    private float playerWidth, playerHeight;

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
        sprite = new Image("data/img/placeholder.png");
        
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
        updateRate = 5;
        jumpCoolDownTick = 60;
        jumpCoolDownDefault = 60;
        jumpSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("data/sound/Jump5.wav"));
        attackSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("data/sound/Attack.wav"));
        sweepAttack = 4;
        sweepLimit = 10;
        attackVelocity = 1.4f;
        attackIndicator = new Rectangle(x+15, y+15, sprite.getWidth() + 20, 4);
    }

    public Shape getAttackIndicator() {
        return attackIndicator;
    }
    
    public void setAttackIndicator(Shape shape) {
        this.attackIndicator = shape;
    }
    
    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getAttackCoolDown() {
        return attackCoolDown;
    }

    public void setAttackCoolDown(float attackCoolDown) {
        this.attackCoolDown = attackCoolDown;
    }

    public int getStoredAttacks() {
        return storedAttacks;
    }

    public void setStoredAttacks(int storedAttacks) {
        this.storedAttacks = storedAttacks;
    }

    public int getStoredJumps() {
        return storedJumps;
    }

    public void setStoredJumps(int storedJumps) {
        this.storedJumps = storedJumps;
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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public HealthSystem getHealthSystem() {
        return healthSystem;
    }

    public MovementSystem getMovementSystem() {
        return movementSystem;
    }

    public Shape getPlayer() {
        return player;
    }

    public void setPlayer(Shape player) {
        this.player = player;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getJumpStrength() {
        return jumpStrength;
    }

    public void setJumpStrength(float jumpStrength) {
        this.jumpStrength = jumpStrength;
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

    @Override
    public boolean isMoving() {
        return moving;
    }

    @Override
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public float getPlayerWidth() {
        return playerWidth;
    }

    public void setPlayerWidth(float width) {
        this.playerWidth = width;
    }

    public float getPlayerHeight() {
        return playerHeight;
    }

    public void setPlayerHeight(float height) {
        this.playerHeight = height;
    }

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

    public void jump(int delta) {
        currentPositionX = getX() - 2;
        currentPositionY = getY() + 32;

        if (!isOnGround()) {
            jumpIndicatorTransp = 1.0f;
        }

        y_velocity = -0.055f * (float) Math.sqrt(Math.pow(delta, 2));
        storedJumps--;
        jumpSound.playAsSoundEffect(1.0f, 1.0f, false);

        System.out.println(storedJumps);
        System.out.println("X: " + currentPositionX);
        System.out.println("Y: " + currentPositionY);
    }

    public void moveLeft(int delta) {
        if (x_velocity > -maximumSpeed) {
            x_velocity -= accelerationSpeed * delta;
            if (x_velocity < -maximumSpeed) {
                x_velocity = -maximumSpeed;
            }
        }
        moving = true;
    }

    public void moveRight(int delta) {
        if (x_velocity < maximumSpeed) {
            x_velocity += accelerationSpeed * delta;
            if (x_velocity > maximumSpeed) {
                x_velocity = maximumSpeed;
            }
        }
        moving = true;
    }

    public void attack(Input i, int delta) {
        sweepXStart = i.getMouseX();
        sweepYStart = i.getMouseY();
        
        if (sweepXEnd != sweepXStart && sweepYStart != sweepYEnd) {
            sweepSpeed = (float) Math.sqrt(Math.pow(sweepXStart - sweepXEnd, 2)
                    + Math.pow(sweepYStart - sweepYEnd, 2));
        }

        direction = new Vector2f(sweepXStart - sweepXEnd,
                sweepYStart - sweepYEnd);

        if (sweepSpeed >= sweepAttack && sweepSpeed <= sweepLimit
                && attackCoolDown <= 0) { // Attack movement here
            System.out.println("attack " + sweepSpeed);
//            x_velocity = (float) (attackVelocity * Math.cos(Math.toRadians(direction.getTheta())));
//            y_velocity = (float) (attackVelocity * Math.sin(Math.toRadians(direction.getTheta())));
            float angleX = (float) Math.cos(Math.toRadians(direction.getTheta()));
            float angleY = (float) Math.sin(Math.toRadians(direction.getTheta()));
            x_velocity = angleX * attackVelocity;
            y_velocity = angleY * (attackVelocity * gravity);
            rotateDirection = (float) direction.getTheta();
            attackIndicatorTransp = 1.0f;
            attackCoolDown = 1000;
            storedAttacks--;
            attackSound.playAsSoundEffect(1.0f, 1.0f, false);
        }
        sweepXEnd = sweepXStart;
        sweepYEnd = sweepYStart;
    }

    /**
     * renders the character
     *
     * @throws SlickException
     */
    @Override
    public void render() throws SlickException {
        sprite.draw(x, y);

        healthSystem.render();

        renderJumpIndicator(currentPositionX, currentPositionY);
        renderAttackIndicator();
    } 

    public void renderJumpIndicator(float x, float y) {
        jumpIndicator = new Rectangle(x, y, sprite.getWidth() + 4, 2);
        g.setColor(new Color(1.0f, 1.0f, 1.0f, jumpIndicatorTransp));

        g.fill(jumpIndicator);
        jumpIndicatorTransp -= 0.002f;
    }

    public void renderAttackIndicator() {
        attackIndicator = new Rectangle(x+15, y+15, sprite.getWidth() + 20, 4);
        g.setColor(new Color(1.0f, 1.0f, 1.0f, attackIndicatorTransp));
        g.fill(attackIndicator.transform(Transform.createRotateTransform((float) Math.toRadians(rotateDirection), x+15, y+15)));
        attackIndicatorTransp -= 0.001f;
    }
}
