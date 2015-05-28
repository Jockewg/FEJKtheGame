package com.fejkathegame.server;

import com.esotericsoftware.kryonet.Connection;

/**
 *
 * @author Filip
 */
public class Player {
    public String name;
    public float x, y;
    public Connection c;
    public float direction;
    public boolean isAttacking;
    public boolean isChargeing;
    public boolean isFullyCharged;
    public boolean moveingLeft;
    public boolean moveingRight;
    public boolean isJumping;
    public boolean isGrounded;
    public boolean isFalling;
    public int hp;
    public boolean ready;
}
