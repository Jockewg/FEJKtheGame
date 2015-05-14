package com.fejkathegame.menu;

import com.fejkathegame.menu.button.LevelSelectButton;
import java.io.File;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Khamekaze
 */
public class LevelSelect {
    
    //private int numberOfLevels = 3;
    //private Image[] levelImages;
    private ArrayList<LevelSelectButton> levels;
    private final File levelDirr;
    
    public LevelSelect() throws SlickException {
        levelDirr = new File("src/main/resources/data/levels/singelplayer");
        levels = new ArrayList<>();
        for(int i = 0;i<levelDirr.list().length;i++){
            System.out.println(levelDirr.list()[i] + " id of map: " +(i));
            int mapId = i = i +4;
            levels.add(new LevelSelectButton(100, 100 + (i*60), 400, 50, levelDirr.list()[i], mapId));
        }
    }
    
    public void render(Graphics g) {
        
        for(LevelSelectButton lvlbtn : levels){
            
            lvlbtn.render(g);
            
        }
        
//        int x = 150;
//        int y = 150;
//        int levelNum = 1;
//        for(Image i : levelImages) {
//            if(x <= 150) {
//                i.draw(x, y);
//                g.drawString("Level " + levelNum, x, y + 170);
//            } else {
//                i.draw(x, y);
//                g.drawString("Level " + levelNum, x, y + 170);
//            }
//            levelNum++;
//            x = x + 235;
//        }
    }

    public ArrayList<LevelSelectButton> getLevelButtons() {
        return levels;
    }
    
}
