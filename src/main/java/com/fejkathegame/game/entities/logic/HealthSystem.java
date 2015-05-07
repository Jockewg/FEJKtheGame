package com.fejkathegame.game.entities.logic;

import com.fejkathegame.game.entities.LevelObject;
import java.io.IOException;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Created by Swartt on 2015-04-28.
 */
public class HealthSystem {
    LevelObject object;
    private Image heartImage = new Image("src/main/resources/data/img/heartcontainer/health2.png");
    ArrayList<Heart> hearts = new ArrayList<>();
    private Audio hurtSound;

    /**
     * Constructor for the health system, Requires a {@code LevelObject} to attach to, initializes {@code healthBar}
     * based on the amount of health of the {@code LevelObject}
     * @param levelObj
     * @throws SlickException
     * @throws IOException
     */
    public HealthSystem(LevelObject levelObj) throws SlickException, IOException {
        this.object = levelObj;
        setHealthBar();
        hurtSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("src/main/resources/data/sound/Hurt.wav"));
    }

    /**
     * Subtracts health from the player if he takes damage, does an {@code isCharacterAlive} check
     *
     * @param damage - the damage that should be dealt
     */
    public void dealDamage(int damage) {
        int currentHealth = object.getHealth();
        if (object.isAlive()) {
            int newHealth = currentHealth - damage;
            object.setHealth(newHealth);
            isCharacterAlive();
            updateHealthBar();
            hurtSound.playAsSoundEffect(1.0f, 1.0f, false);
        }
    }

    /**
     * initiates the {@code healthBar} for the {@code LevelObject}
     * @throws SlickException
     */
    public void setHealthBar() throws SlickException {
        for (int i = 0; i < object.getHealth(); i++) {
            //Old dot health system
//            hearts.add(new Heart(heartImage, object.getX() + (i * 10), object.getY()));
            if(i == 0) {
                hearts.add(new Heart(heartImage, object.getX(), object.getY()));
            } else if(i == 1) {
                hearts.add(new Heart(heartImage, object.getX() + 16, object.getY()));
            } else if(i == 2) {
                hearts.add(new Heart(heartImage, object.getX(), object.getY() + 16));
            } else if(i == 3) {
                hearts.add(new Heart(heartImage, object.getX() + 16, object.getY() + 16));
            } else if(i == 4) {
                hearts.add(new Heart(heartImage, object.getX() + 8, object.getY() + 8));
            }
        }
    }
    /**
     * updates the HealthBar based on the current health of the {@code LevelObject}
     */
    public void updateHealthBar() {
        int health = object.getHealth();
        if(getHearts().size() > health) {
            getHearts().remove(health);
        }

    }

    public ArrayList<Heart> getHearts() {
        return hearts;
    }

    /**
     * checks the players health, if the players health is 0 or less it changes the
     * {@code isAlive} boolean to false
     */
    public boolean isCharacterAlive() {
        int currentHealth = object.getHealth();
        if (currentHealth <= 0) {
            object.setAlive(false);
            return false;
        }
        return true;
    }
    
    public void render() {
        for(int i = 0; i < hearts.size(); i ++) {
            //Dot health system
//            hearts.get(i).getGraphicImage().draw((object.getX() - 9) + (i * 10), object.getY() - 16);
            if(hearts.get(i) == null)
                break;
            if(i == 0)
            hearts.get(i).getGraphicImage().draw(object.getX(), object.getY());
            else if(i == 1)
                hearts.get(i).getGraphicImage().draw(object.getX() + 16, object.getY());
            else if(i == 2)
                hearts.get(i).getGraphicImage().draw(object.getX(), object.getY() + 16);
            else if(i == 3)
                hearts.get(i).getGraphicImage().draw(object.getX() + 16, object.getY() + 16);
            else if(i == 4)
                hearts.get(i).getGraphicImage().draw(object.getX() + 8, object.getY() + 8);
        }
    }
}

class Heart {
    Image graphicImage;
    float positionX;
    float positionY;

    public Heart(Image graphicImage, float positionX, float positionY) {
        this.graphicImage = graphicImage;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Image getGraphicImage() {
        return graphicImage;
    }

    public void setGraphicImage(Image graphicImage) {
        this.graphicImage = graphicImage;
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
