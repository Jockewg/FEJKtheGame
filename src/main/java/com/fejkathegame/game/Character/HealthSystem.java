package com.fejkathegame.game.Character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Created by Swartt on 2015-04-28.
 */
public class HealthSystem {
    Character character;
    private Image heartImage = new Image("data/img/heartcontainer/health2.png");
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
        for (int i = 0; i < character.getHealth(); i++) {
            //Old dot health system
//            hearts.add(new Heart(heartImage, character.getX() + (i * 10), character.getY()));
            if(i == 0) {
                hearts.add(new Heart(heartImage, character.getX(), character.getY()));
            } else if(i == 1) {
                hearts.add(new Heart(heartImage, character.getX() + 16, character.getY()));
            } else if(i == 2) {
                hearts.add(new Heart(heartImage, character.getX(), character.getY() + 16));
            } else if(i == 3) {
                hearts.add(new Heart(heartImage, character.getX() + 16, character.getY() + 16));
            } else if(i == 4) {
                hearts.add(new Heart(heartImage, character.getX() + 8, character.getY() + 8));
            }
        }
    }
    /**
     *
     */
    public void updateHealthBar() {
        //TODO: Implement graphical representation of a healthbar
        int health = character.getHealth();
        if(getHearts().size() - 1 > health) {
            getHearts().remove(health + 1);
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
    
    public void render() {
        for(int i = 0; i < hearts.size(); i ++) {
            //Dot health system
//            hearts.get(i).getGraphicImage().draw((character.getX() - 9) + (i * 10), character.getY() - 16);
            if(hearts.get(i) == null)
                break;
            if(i == 0)
            hearts.get(i).getGraphicImage().draw(character.getX(), character.getY());
            else if(i == 1)
                hearts.get(i).getGraphicImage().draw(character.getX() + 16, character.getY());
            else if(i == 2)
                hearts.get(i).getGraphicImage().draw(character.getX(), character.getY() + 16);
            else if(i == 3)
                hearts.get(i).getGraphicImage().draw(character.getX() + 16, character.getY() + 16);
            else if(i == 4)
                hearts.get(i).getGraphicImage().draw(character.getX() + 8, character.getY() + 8);
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
