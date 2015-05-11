package com.fejkathegame.menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Khamekaze
 */
public class LevelSelect {
    
    private int numberOfLevels = 3;
    private Image[] levelImages;
    
    public LevelSelect() throws SlickException {
        loadImages();
    }
    
    public void loadImages() throws SlickException {
        levelImages = new Image[numberOfLevels];
        for(int i = 0; i < numberOfLevels; i++) {
            levelImages[i] = new Image("src/main/resources/data/img/levelImages/level" + i + ".png");
        }
        
    }
    
    public void render(Graphics g) {
        int x = 150;
        int y = 150;
        int levelNum = 1;
        for(Image i : levelImages) {
            if(x <= 150) {
                i.draw(x, y);
                g.drawString("Level " + levelNum, x, y + 170);
            } else {
                i.draw(x, y);
                g.drawString("Level " + levelNum, x, y + 170);
            }
            levelNum++;
            x = x + 235;
        }
    }
}
