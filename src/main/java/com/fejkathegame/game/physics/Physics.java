package com.fejkathegame.game.physics;

import com.fejkathegame.game.arena.Arena;
import com.fejkathegame.game.arena.LevelObject;
import com.fejkathegame.game.tiles.Tile;

import java.util.ArrayList;

public class Physics {
    
    private final float gravity = 0.0015f;
    
    public void handlePhysics(Arena arena, int delta) {
        handleCharacters(arena, delta);
    }
    
    private void handleCharacters(Arena arena, int delta) {
        for(LevelObject p : arena.getPlayers()) {
            if(!p.isMoving()) {
                p.decelerate(delta);
            }
            handleGameObject(p, arena, delta);
        }
    }
    
    private boolean checkCollision(LevelObject obj, Tile[][] mapTiles) {
        ArrayList<Tile> tiles = obj.getBoundingShape().getTilesOccupying(mapTiles);
        for(Tile t : tiles) {
            if(t.getBoundingShape() != null) {
                if(t.getBoundingShape().checkCollision(obj.getBoundingShape())) {
                    return true;
                }
            }
        }
        return false;
    }
    
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
    
    private void handleGameObject(LevelObject obj, Arena arena, int delta) {
        obj.setOnGround(isOnGround(obj, arena.getTiles()));
        
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
                
                if(checkCollision(obj, arena.getTiles())) {
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
                
                if(checkCollision(obj, arena.getTiles())) {
                    obj.setY(obj.getY() - step_y);
                    obj.setY_velocity(0);
                    y_movement = 0;
                    break;
                }
            }
        }
    }

}
