
package com.fejkathegame.game.entities;

import com.fejkathegame.game.timer.PracticeTimer;
import java.io.IOException;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Joakim
 */
public class HPPowerUp extends LevelObject{

   private final int boostAmount;
   private final Image sprite;
   private final Shape hitBox;
   private PracticeTimer timer;
    
    public HPPowerUp(float width, float height) throws SlickException, IOException {
        super(0, 0);
        
        boostAmount = 1;
        sprite = new Image("src/main/resources/data/img/placeholder.png");
        hitBox = new Rectangle(x, y, width, height);
        timer = new PracticeTimer();
        setAlive(false);
        changePositionRandom();
        
    }
    
//    public void update(){
//        if(isAlive()){
//            hitBox.setX(x);
//            hitBox.setY(y);
//        }
//        
//    }
    
    public void render() throws SlickException {
        if(isAlive()){
            //g.setColor(Color.red);
            //g.draw(hitBox);
            sprite.draw(x, y, hitBox.getWidth(), hitBox.getHeight());
        }
    }
    
    public void changePositionRandom(){
        Random r = new Random();
        x = r.nextInt(840) + 30;
        y = r.nextInt(440) + 30;
    }
    
    public void boost(Character player){
        player.setHealth(player.getHealth() + boostAmount);
    }
    
}
