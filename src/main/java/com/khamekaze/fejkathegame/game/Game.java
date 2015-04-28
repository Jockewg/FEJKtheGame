package com.khamekaze.fejkathegame.game;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class Game extends BasicGame implements MouseListener {

    private Circle indicator;
    private float mouseX, mouseXCircle, mouseXBefore,
            mouseY, mouseYCircle, mouseYBefore;
    private long time = 0;
    private final long defultTimer = 500;

    public Game(String gameName) {
        super(gameName);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {

        mouseY = gc.getHeight() / 2;
        mouseX = gc.getWidth() / 2;

        mouseXCircle = mouseX;
        mouseYCircle = mouseY;

        indicator = new Circle(mouseX, gc.getWidth() / 2, 30);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        Input input = gc.getInput();
        time -= i;
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();

        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            mouseXCircle = input.getMouseX();
            mouseYCircle = input.getMouseY();

            indicator.setCenterX(mouseXCircle);
            indicator.setCenterY(mouseYCircle);
            System.out.println("MouseX: " + mouseX + " MouseY: " + mouseY);
        }

        if (time <= 0 && input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
            if (mouseX + 200 < mouseXBefore) {
                System.out.println("attack left" + time);
                time = defultTimer;
            }
        }
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        drawOriginIndicator(g, gc);
    }

    public void drawOriginIndicator(Graphics g, GameContainer gc) {
        if (gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
            g.draw(indicator);
        }
    }
}
