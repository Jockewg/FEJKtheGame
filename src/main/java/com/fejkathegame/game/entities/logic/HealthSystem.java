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
    private Image[] healthBar = new Image[6];
    ArrayList<Heart> hearts = new ArrayList<>();
    private Audio hurtSound;
    
    private boolean isDamaged = false;
    private int damageCooldown = 0;

    /**
     * Constructor for the health system, Requires a {@code LevelObject} to attach to, initializes {@code healthBar}
     * based on the amount of health of the {@code LevelObject}
     * @param levelObj
     * @throws SlickException
     * @throws IOException
     */
    public HealthSystem(LevelObject levelObj) throws SlickException, IOException {
        this.object = levelObj;
        initHealthBar();
        hurtSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("src/main/resources/data/sound/Hurt.wav"));
    }
    
    public void initHealthBar() throws SlickException {
        healthBar[0] = new Image("src/main/resources/data/img/statusBar/health/health0.png");
        healthBar[1] = new Image("src/main/resources/data/img/statusBar/health/health1.png");
        healthBar[2] = new Image("src/main/resources/data/img/statusBar/health/health2.png");
        healthBar[3] = new Image("src/main/resources/data/img/statusBar/health/health3.png");
        healthBar[4] = new Image("src/main/resources/data/img/statusBar/health/health4.png");
        healthBar[5] = new Image("src/main/resources/data/img/statusBar/health/health5.png");
    }

    /**
     * Subtracts health from the player if he takes damage, does an {@code isCharacterAlive} check
     *
     * @param damage - the damage that should be dealt
     */
    public void dealDamage(int damage) {
        int currentHealth = object.getHealth();
        if (object.isAlive() && !isDamaged) {
            int newHealth = currentHealth - damage;
            object.setHealth(newHealth);
            isCharacterAlive();
            hurtSound.playAsSoundEffect(1.0f, 1.0f, false);
            isDamaged = true;
            damageCooldown = 1000;
        }
    }
    
    public void damageCooldown(int delta) {
        if(damageCooldown != 0) {
            damageCooldown -= delta;
            if(damageCooldown < 0) {
                damageCooldown = 0;
            }
        }
        
        if(damageCooldown == 0) {
            isDamaged = false;
        }
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
    
    public void render(float x, float y) {
        healthBar[object.getHealth()].getScaledCopy(0.15f).draw(x, y);
    }
    
    public void setIsDamaged(boolean isDamaged) {
        this.isDamaged = isDamaged;
    }
    
    public boolean getIsDamaged() {
        return isDamaged;
    }
    
    public Image getHealthBar(int life) {
        return healthBar[life];
    }

    public ArrayList<Heart> getHearts() {
        return hearts;
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
