package com.fejkathegame;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.newdawn.slick.SlickException;

import java.io.IOException;

public class HealthTest {

    @Test
    public void testMaxHealth() throws SlickException, IOException {
        TestObject testObject = new TestObject(50,50);
        //heals the object
        testObject.getHealthSystem().dealDamage(-10);
        assertEquals("Health must always be max 5", 5, testObject.getHealth());
}

    @Test
    public void testIfObjectIsAlive() throws SlickException, IOException {
        TestObject testObject = new TestObject(40,40);
        testObject.isAlive = true;
        testObject.getHealthSystem().dealDamage(10);
        assertEquals("Alive must be false if object has 0 or less health", false, testObject.isAlive);
    }



}
