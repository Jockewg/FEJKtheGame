package com.fejkathegame.game.arena.physics;

import com.fejkathegame.game.arena.versus.Versus;
import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.entities.LevelObject;
import com.fejkathegame.game.arena.tiles.Tile;

import java.util.ArrayList;

/**
 * Handles collisiondetection and gravity.
 * 
 * @author Kim Göransson
 */
public class Physics {
    
    private final float gravity = 0.0015f;
    
    /**
     * Handles physics for a level.
     * 
     * @param arena the level that will utilize physics
     * @param delta the time between two updates 
     */
    public void handlePhysics(Level arena, int delta) {
        handleCharacters(arena, delta);
    }
    
    /**
     * Handles physics for objects in the game.
     * 
     * @param level the level that the objects are in
     * @param delta the time between two updates
     */
    private void handleCharacters(Level level, int delta) {
        for(LevelObject p : level.getPlayers()) {
            if(!p.isMoving()) {
                p.decelerate(delta);
            }
            handleGameObject(p, level, delta);
        }
    }
    
    /**
     * Checks if a hitbox is colliding with a tile.
     * 
     * If an object is occupying a solid tile, this method will return true,
     * indicating a collision between an object and a tile.
     * 
     * @param obj the player
     * @param mapTiles the tiles that make up the level
     * @return true if a collision is detected, else false
     */
    private boolean checkCollision(LevelObject obj, Tile[][] mapTiles) {
        ArrayList<Tile> tiles = obj.getBoundingShape().getTilesOccupying(mapTiles);
        for(Tile t : tiles) {
            if(t.getBoundingShape() != null) {
                if(t.getBoundingShape().checkCollision(obj.getBoundingShape())) {
                        obj.setIsAttacking(false);
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if an object is touching a ground tile.
     * 
     * If an object is located directly above a tile, that tile is considered
     * a ground tile.
     * 
     * @param obj the player
     * @param mapTiles the tiles that make up the level
     * @return true if the player is directly above a ground tile, else false
     */
    private boolean isOnGround(LevelObject obj, Tile[][] mapTiles) {
        ArrayList<Tile> tiles = obj.getBoundingShape().getGroundTiles(mapTiles);
        
        obj.getBoundingShape().movePosition(0, 1);
        
        for(Tile t : tiles) {
            if(t.getBoundingShape() != null) {
                if(t.getBoundingShape().checkCollision(obj.getBoundingShape())) {
                    obj.getBoundingShape().movePosition(0, -1);
                    return true;
                }
            }
        }
        obj.getBoundingShape().movePosition(0, -1);
        return false;
    }
    
    /**
     * Handles the gravity for players.
     * 
     * If a player is not touching a ground tile, this method applies
     * gravity on the player.
     * 
     * @param obj the player
     * @param level the level that the player is in
     * @param delta the time between two updates
     */
    private void handleGameObject(LevelObject obj, Level level, int delta) {
        obj.setOnGround(isOnGround(obj, level.getTiles()));
        
        if(!obj.isOnGround() || obj.getY_velocity() < 0) {
            obj.applyGravity(gravity * delta);
        } else {
            obj.setY_velocity(0);
            obj.setStoredJumps(2);
            obj.setStoredAttacks(2);
        }
        
        float x_movement = obj.getX_velocity() * delta;
        float y_movement = obj.getY_velocity() * delta;
        
        float step_y = 0;
        float step_x = 0;
        
        if(x_movement != 0) {
            step_y = Math.abs(y_movement)/Math.abs(x_movement);
            if(y_movement < 0)
                step_y = -step_y;
            
            if(x_movement > 0)
                step_x = 1;
            else
                step_x = -1;
            
            if((step_y > 1 || step_y < -1) && step_y != 0) {
                step_x = Math.abs(step_x)/Math.abs(step_y);
                if(x_movement < 0)
                    step_x = -step_x;
                if(y_movement < 0)
                    step_y = -1;
                else
                    step_y = 1;
            }
        } else if(y_movement != 0) {
            if(y_movement > 0)
                step_y = 1;
            else
                step_y = -1;
        }
        
        while(x_movement != 0 || y_movement != 0) {
            if(x_movement != 0) {
                if((x_movement > 0 && x_movement < step_x) || (x_movement > step_x && x_movement < 0)) {
                    step_x = x_movement;
                    x_movement = 0;
                } else 
                    x_movement -= step_x;
                
                obj.setX(obj.getX() + step_x);
                
                if(checkCollision(obj, level.getTiles())) {
                    obj.setX(obj.getX() - step_x);
                    obj.setX_velocity(0);
                    x_movement = 0;
                }
            }
            
            if(y_movement != 0) {
                if((y_movement > 0 && y_movement < step_y) || (y_movement > step_y  && y_movement < 0)) {
                    step_y = y_movement;
                    y_movement = 0;
                } else
                    y_movement -= step_y;
                
                obj.setY(obj.getY() + step_y);
                
                if(checkCollision(obj, level.getTiles())) {
                    obj.setY(obj.getY() - step_y);
                    obj.setY_velocity(0);
                    y_movement = 0;
                    break;
                }
            }
        }
    }

}
