/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fejkathegame.client;

import com.fejkathegame.game.entities.Character;

/**
 *
 * @author Filip
 */
public class MPPlayer {
    public float x = 256f, y = 256f;
    public int id;
    public float direction;
    public boolean isAttacking;
    public boolean isChargeing;
    public boolean isFullyCharged;
    public boolean moveingLeft;
    public boolean moveingRight;
    public Character character;
}
