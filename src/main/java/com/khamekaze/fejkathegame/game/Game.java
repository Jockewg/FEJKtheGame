package com.khamekaze.fejkathegame.game;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Game extends BasicGame implements MouseListener {

    private Circle indicator;
    private float mouseX, mouseXBefore,
            mouseY, mouseYBefore,
            sweepXStart, sweepYStart,
            sweepXEnd,sweepYEnd,
            sweepSpeed;
    private long cooldownTime = 0;
    private final long defaultCooldown = 500;

    public Game(String gameName) {
        super(gameName);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {

        mouseY = gc.getHeight() / 2;
        mouseX = gc.getWidth() / 2;

        mouseXBefore = mouseX;
        mouseYBefore = mouseY;

        indicator = new Circle(mouseX, gc.getWidth() / 2, 30);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        Input input = gc.getInput();
        cooldownTime -= i;
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();
        
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            mouseXBefore = input.getMouseX();
            mouseYBefore = input.getMouseY();
            
            indicator.setCenterX(mouseXBefore);
            indicator.setCenterY(mouseYBefore);
            System.out.println("MouseX: " + mouseX + " MouseY: " + mouseY);
        }
        
        if (sweepXStart != mouseX && sweepYStart != mouseY) {
            sweepSpeed =(float) Math.sqrt(Math.pow(mouseX - sweepXStart, 2) +
                    Math.pow(mouseY - sweepYStart, 2));
            System.out.println("speed: " + sweepSpeed);
        }
        sweepXStart = mouseX;
        sweepYStart = mouseY;
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
