package com.fejkathegame.game.arena.maps;

import com.fejkathegame.game.arena.PracticeLevel;
import com.fejkathegame.game.entities.Character;


/**
 * Created by Swartt on 2015-05-11.
 */
public class PracticeStateHelper {
    PracticeLevel arena;
    Character obj;
    PracticeCamera camera;

    public PracticeStateHelper(PracticeLevel arena, Character obj, PracticeCamera camera) {
        this.arena = arena;
        this.obj = obj;
        this.camera = camera;
    }

    public void checkCollisionWithTarget(int map) {
        for (int i = 0; i < arena.getTargets().size(); i++) {
            if (obj.getAttackIndicator().intersects(arena.getTargets().get(i).getHitbox()) && obj.getIsAttacking()
                    || obj.getIsFullyCharged() && obj.getSuperAttackIndicator().intersects(arena.getTargets().get(i).getHitbox())) {
                arena.getTargets().get(i).getHealthSystem().dealDamage(1);
                arena.getTargets().remove(i);
                arena.getMapHelper().updateScore(map);
            }
        }
    }


    public void checkCameraOffset() {
        if (obj.getX() <= camera.getOffsetMinX() + 450)
            camera.setCamX(camera.getOffsetMinX());
        else if (obj.getX() >= camera.getOffsetMaxX())
            camera.setCamX(camera.getOffsetMaxX() - 450);
        else
            camera.setCamX(obj.getX() - 450.0f);

        if (obj.getY() <= camera.getOffsetMinY() + 250)
            camera.setCamY(camera.getOffsetMinY());
        else if (obj.getY() >= camera.getOffsetMaxY())
            camera.setCamY(camera.getOffsetMaxY() - 250);
        else
            camera.setCamY(obj.getY() - 250.0f);
    }

}
