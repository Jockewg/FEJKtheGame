/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
