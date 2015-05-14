package com.fejkathegame.menu;

import com.fejkathegame.menu.button.LevelSelectButton;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Khamekaze
 */
public class LevelSelect {
    
    //private int numberOfLevels = 3;
    //private Image[] levelImages;
    private ArrayList<LevelSelectButton> levelButtons;
    private final File levelDirr;
    
    public LevelSelect() throws SlickException {
        levelDirr = new File("src/main/resources/data/levels/singelplayer");
        levelButtons = new ArrayList<>();
        ArrayList<String> listOfNames = arrayToArrayListAdapter(levelDirr.list());
        for(int i = 0; i < listOfNames.size(); i++){
            int mapId = i + 4;
            System.out.println("id of map in LevelSelect is: " + mapId);
            levelButtons.add(new LevelSelectButton(100, 100 + (i * 60), 400, 50, listOfNames.get(i), mapId));
        }
    }

    public ArrayList<String> arrayToArrayListAdapter(String[] toConvert) {
        ArrayList<String> listOfMapNames = new ArrayList<>();
        for (int i = 0; i < toConvert.length; i++) {
            listOfMapNames.add(toConvert[i]);
        }
        Collections.sort(listOfMapNames);
        return listOfMapNames;

    }
    
    public void render(Graphics g) {
        
        for(LevelSelectButton buttons : levelButtons){
            
            buttons.render(g);
            
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
        return levelButtons;
    }
    
}
