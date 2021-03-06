package com.fejkathegame.game.arena.maps.singelplayer.bigblue03;

import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.arena.maps.PracticeCamera;
import com.fejkathegame.game.arena.maps.PracticeStateHelper;
import com.fejkathegame.game.arena.physics.Physics;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

/**
 * @author Swartt
 */
public class BigBlue02State extends State {

    private String name;
    private PracticeStateHelper stateHelper;

    private float offsetMaxX;
    private float offsetMaxY;

    private float scale = 0.24f;
    private float scaleSmoothing = 0;

    /**
     * Constructor for ArenaState
     *
     * @param name of the stage
     */
    public BigBlue02State(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return Main.BIG_BlUE03;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        player = null;
        try {
            player = new com.fejkathegame.game.entities.Character(50, 1100);
        } catch (IOException e) {
            e.printStackTrace();
        }

        level = new BigBlue02(name, player);

        setCameraBoundaries();

        physics = new Physics();

        camera = new PracticeCamera(offsetMaxX, offsetMaxY);

        stateHelper = new PracticeStateHelper(this);

    }

    public void setCameraBoundaries() {
        offsetMaxX = level.map.getWidth() * 22;
        offsetMaxY = level.map.getHeight() * 20;
    }

    public void cameraAnimation() {
        if (scale < 0.25f) {
            scale += 0.00005f;
            camera.setCamY(0);
        } else {
            scaleSmoothing += 0.00005f;
            scale += (0.005f + scaleSmoothing);
            camera.setCamY(0);
            float newCamY = (750 * scale);
            camera.setCamY(newCamY);
         /*System.out.println("cameraY: " + camera.getCamY());*/
        }

        if (scale >= 1) {
            scale = 1;
            isCameraAnimationRunning = false;
        }

      /*System.out.println(scale);*/
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        stateHelper.render(g);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        stateHelper.update(gc, sbg, i, 50, 1100);
    }
}
