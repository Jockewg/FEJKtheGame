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
    private float mouseX, mouseXBefore, y, mouseY, mouseYBefore, mouseSpeed;
    private boolean attack;

    public Game(String gameName) {
        super(gameName);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {

        mouseY = gc.getHeight() / 2;
        mouseX = gc.getWidth() / 2;

        attack = false;
        mouseSpeed = 0;

        mouseXBefore = mouseX;
        mouseYBefore = mouseY;

        indicator = new Circle(mouseX, gc.getWidth() / 2, 30);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        Input input = gc.getInput();

        mouseX = input.getMouseX();
        mouseY = input.getMouseY();

        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            mouseXBefore = input.getMouseX();
            mouseYBefore = input.getMouseY();

            indicator.setCenterX(mouseXBefore);
            indicator.setCenterY(mouseYBefore);
            System.out.println("MouseX: " + mouseX + " MouseY: " + mouseY);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        drawOriginIndicator(g, gc);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        if (newx < oldx) {
            System.out.println("moved left");
        }
        if (newx > oldx) {
            System.out.println("moved right");
        }
        if (newy < oldy) {
            System.out.println("moved up");
        }
        if (newy > oldy) {
            System.out.println("moved down");
        }
    }
    
    public void drawOriginIndicator(Graphics g, GameContainer gc) {
        if (gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
            g.draw(indicator);
        }
    }
}
