package com.khamekaze.fejkathegame.Character;


import com.khamekaze.fejkathegame.arena.LevelObject;
import com.khamekaze.fejkathegame.collision.AABoundingRect;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;

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
    protected float maximumSpeed = 1;
    protected boolean moving = false;
    protected Image sprite;

    /**
     * Constructor for creating a character, gives it the default values for a character
     */
    public Character(float x, float y) throws SlickException {
        super(x, y);

        new AABoundingRect(x, y, 25, 25);

        accelerationSpeed = 0.001f;
        maximumSpeed = 0.15f;
        maxFallSpeed = 0.3f;
        decelerationSpeed = 0.001f;
        sprite = new Image("data/img/placeholder.png");

        grounded = false;
        health = 5;
        attackCoolDown = 1;
        storedAttacks = 2;
        storedJumps = 2;
        size = 40;
        velocityY = 0;
        velocityX = 0;
        isAlive = true;
        this.healthSystem = new HealthSystem(this);
        this.movementSystem = new MovementSystem(this);
        gravity = 0.5f;
        jumpStrength = -15;
        updateRate = 5;
        jumpCoolDownTick = 0;
        jumpCoolDownDefault = 500;
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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }


    /**
     * initializes all variables the character needs when added to the games arena.
     *
     * @param gc
     * @throws SlickException
     */
    public void init(GameContainer gc) throws SlickException {
        currentPositionX = player.getCenterX();
        mousePositionX = gc.getWidth() / 2;
        previousMousePositionX = mousePositionX;
        player = new Circle(200, 200, size);
    }

    /**
     * updates the state of character, update is called every frame
     *
     * @param gc
     * @param i
     * @throws SlickException
     */
    public void update(GameContainer gc, int i) throws SlickException {
        Input input = gc.getInput();

        jumpCoolDownTick -= i;

       /* movementSystem.gravity();*/

        mousePositionX = input.getMouseX();


        if (jumpCoolDownTick <= 0 && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            movementSystem.jump();
            jumpCoolDownTick = jumpCoolDownDefault;
        }

        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            previousMousePositionX = input.getMouseX();
        }

        if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
            movementSystem.move();
        }

    }

    /**
     * renders the character
     * @throws SlickException
     */
    @Override
    public void render() throws SlickException {
        sprite.draw(x - 2, y - 2);
        ArrayList<Heart> hearts = healthSystem.getHearts();
        for (int i = 0; i < hearts.size(); i++) {
            hearts.get(i).getGrahpicImage().draw(hearts.get(i).positionX, hearts.get(i).getPositionY());
        }
    }
}
