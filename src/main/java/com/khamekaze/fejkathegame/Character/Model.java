package com.khamekaze.fejkathegame.Character;

import org.newdawn.slick.geom.Rectangle;

/**
 * Created by Swartt on 2015-04-28.
 */
public class Model {
    private boolean grounded;
    private int health;
    private float attackCoolDown;
    private int storedAttacks;
    private int storedJumps;
    private int jumpCoolDown;
    private float size;

    /**
     * Constructor for creating a character, gives it the default values for a character
     */
    public Model ()  {
    grounded = false;
        health = 5;
        attackCoolDown = 1;
        storedAttacks = 2;
        storedJumps = 2;
        jumpCoolDown = 1;
        size = 40;
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

    public int getJumpCoolDown() {
        return jumpCoolDown;
    }

    public void setJumpCoolDown(int jumpCoolDown) {
        this.jumpCoolDown = jumpCoolDown;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
