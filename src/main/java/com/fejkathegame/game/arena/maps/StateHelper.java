package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.arena.Level;
import com.fejkathegame.game.entities.Character;


/**
 * Created by Swartt on 2015-05-11.
 */
public class StateHelper {
    Level arena;
    Character obj;
/*    private float offsetMaxX;
    private float offsetMaxY;
    private float offsetMinX = 0;
    private float offsetMinY = 0;
    private float camX, camY = 0;*/

    public StateHelper (Level arena, Character obj) {
        this.arena = arena;
        this.obj = obj;
    }

    public void checkCollisionWithTarget() {
        for (int i = 0; i < arena.getTargets().size(); i++) {
            if (obj.getAttackIndicator().intersects(arena.getTargets().get(i).getHitbox()) && obj.getIsAttacking()
                    || obj.getIsFullyCharged() && obj.getSuperAttackIndicator().intersects(arena.getTargets().get(i).getHitbox())) {
                System.out.println("HIT");
                arena.getTargets().get(i).getHealthSystem().dealDamage(1);
                arena.getTargets().remove(i);
                arena.getMapHelper().updateScore();
            }
        }
    }

    //Waiting for Kim to finish his dynamic camera
    /*public void checkCameraOffset() {
        if (obj.getX() <= offsetMinX + 450)
            camX = offsetMinX;
        else if (obj.getX() >= offsetMaxX)
            camX = offsetMaxX - 450;
        else
            camX = obj.getX() - 450.0f;

        if (obj.getY() <= offsetMinY + 250)
            camY = offsetMinY;
        else if (obj.getY() >= offsetMaxY)
            camY = offsetMaxY - 250;
        else
            camY = obj.getY() - 250.0f;
    }*/

}
