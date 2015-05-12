/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.client;

import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Filip
 */
public class Player {
    
    float speed = 2f; // not right yet;
    float gravity = 0.1f;
    Vector2f position = new Vector2f(256,256);
    Vector2f networkPosition = new Vector2f(0,0);
    
    public void update(int delta) {
        gravity = 0.0015f * delta;
        position.y -= gravity;
        
        
    }
}
