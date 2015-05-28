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
 * 
 * This class is used to store importet stuff for later use within the state with a similar name.
 * Here a directory is chosen and from there all the files are processed and 
 * used to create buttons for selecting wich map to practice on.
 */
public class LevelSelect {
    
    private static ArrayList<LevelSelectButton> levelButtons;
    private final File levelDirr;
    
    public LevelSelect() throws SlickException {
        levelDirr = new File("src/main/resources/data/levels/singelplayer");
        levelButtons = new ArrayList<>();
        ArrayList<String> listOfNames = arrayToArrayListAdapter(levelDirr.list());
        for(int i = 0; i < listOfNames.size(); i++){
            int mapId = i + 4;
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
        
    }

    public static ArrayList<LevelSelectButton> getLevelButtons() {
        return levelButtons;
    }
    
}
