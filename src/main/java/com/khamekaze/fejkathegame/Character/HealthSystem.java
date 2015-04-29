package com.khamekaze.fejkathegame.Character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Created by Swartt on 2015-04-28.
 */
public class HealthSystem {
    Character character;
    ArrayList<Heart> hearts = new ArrayList<>();

    public HealthSystem(Character character) throws SlickException {
        this.character = character;
        setHealthbar();
    }

    /**
     * Subtracts health from the player if he takes damage, does an {@code isCharacterAlive} check
     *
     * @param damage - the damage that should be dealt
     */
    public void dealDamage(int damage) {
        int currentHealth = character.getHealth();
        if (currentHealth > 0 && character.isAlive()) {
            int newHealth = currentHealth - damage;
            character.setHealth(newHealth);
            isCharacterAlive();
            updateHealthBar();
        }
    }

    public void setHealthbar() throws SlickException {
        Image heartImage = new Image("C:\\Users\\Swartt\\Desktop\\FEJKA game placeholder art\\HeartContainerFull");
        for (int i = 0; i < character.getHealth(); i++) {
            hearts.add(new Heart(heartImage,50 + i*50, 50));
        }
    }
    /**
     *
     */
    public void updateHealthBar() {
        //TODO: Implement graphical representation of a healthbar
        for (int i = character.getHealth(); i < hearts.size() - 1; i++) {
            hearts.remove(i);
        }

    }

    public ArrayList<Heart> getHearts() {
        return hearts;
    }

    /**
     * checks the players health, if the players health is 0 or less it changes the
     * {@code isAlive} boolean to false
     */
    public void isCharacterAlive() {
        int currentHealth = character.getHealth();
        if (currentHealth < 0) {
            character.setAlive(false);
        }
    }
}

class Heart {
    Image grahpicImage;
    float positionX;
    float positionY;

    public Heart(Image grahpicImage, float positionX, float positionY) {
        this.grahpicImage = grahpicImage;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Image getGrahpicImage() {
        return grahpicImage;
    }

    public void setGrahpicImage(Image grahpicImage) {
        this.grahpicImage = grahpicImage;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
}
