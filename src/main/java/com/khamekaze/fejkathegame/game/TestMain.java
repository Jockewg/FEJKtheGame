package com.khamekaze.fejkathegame.game;

import com.khamekaze.fejkathegame.Character.Character;
import com.khamekaze.fejkathegame.Character.HealthSystem;

/**
 * Created by Swartt on 2015-04-28.
 */
public class TestMain {
    Character character = new Character();


    public static void main(String[] args) {

    }
    public void test() {
       character.getHealthSystem().dealDamage(1);
}
}
