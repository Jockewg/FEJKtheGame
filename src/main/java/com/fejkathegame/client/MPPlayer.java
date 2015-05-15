package com.fejkathegame.client;

import com.fejkathegame.game.entities.Character;

/**
 * the data all players have.
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
    public boolean connected;
}
