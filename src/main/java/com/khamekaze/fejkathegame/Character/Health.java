package com.khamekaze.fejkathegame.Character;

/**
 * Created by Swartt on 2015-04-28.
 */
public class Health extends Model{
    /**
     * Subtracts health from the player if he takes damage, does an {@code isCharacterAlive} check
     *
     * @param damage - the damage that should be dealt
     */
    public void dealDamage (int damage) {
        int currentHealth = getHealth();
        if (currentHealth > 0) {
            int newHealth = currentHealth - damage;
            setHealth(newHealth);
            isCharacterAlive();
            updateHealthBar(newHealth);
        }
    }

    /**
     *
     * @param newHealth the new value that the health bar should display
     */
    public void updateHealthBar(int newHealth) {
        //TODO: Implement graphical representation of a healthbar
    }

    /**
     * checks the players health, if the players health is 0 or less it changes the
     * {@code isAlive} boolean to false
     */
    public void isCharacterAlive() {
        int currentHealth = getHealth();
        if (currentHealth < 0) {
            setAlive(false);
        }
    }
}
