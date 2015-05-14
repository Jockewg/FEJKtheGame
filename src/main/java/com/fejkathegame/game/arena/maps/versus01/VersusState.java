package com.fejkathegame.game.arena.maps.versus01;

import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.client.MPPlayer;
import com.fejkathegame.client.PacketAttackDirectionPlayer;
import com.fejkathegame.client.PacketAttackPlayer;
import com.fejkathegame.client.PacketChargePlayer;
import com.fejkathegame.client.PacketFullyChargedPlayer;
import com.fejkathegame.client.PacketMoveLeftPlayer;
import com.fejkathegame.client.PacketMoveRightPlayer;
import com.fejkathegame.client.PacketUpdateX;
import com.fejkathegame.client.PacketUpdateY;
import com.fejkathegame.game.entities.logic.MovementSystem;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.entities.LevelObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class VersusState extends BasicGameState {

    ClientProgram client = new ClientProgram();

    private Versus arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private Character obj;
    private Character player2;

    //Camera stuff
    private float cameraX, cameraY;
    private float cameraWidth = 900;
    private float cameraHeight = cameraWidth * 0.55f;
    private float cameraScale = 1.0f;

    private Line line;

    private ArrayList<Character> characters;

    /**
     * Constructor for ArenaState
     *
     * @param name of the stage
     */
    public VersusState(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return Main.VERSUSSTATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        obj = null;
        player2 = null;
        try {
            obj = new Character(800, 40);
            player2 = new Character(200, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }

        characters = new ArrayList<>();
        characters.add(obj);
        characters.add(player2);

        line = new Line(obj.getX(), obj.getY(), player2.getX(), player2.getY());

        arena = new Versus(name, obj);
        arena.addPlayer(player2);

        movementSystem = new MovementSystem(obj);

        physics = new Physics();
        client.network();

    }

    public void checkCollisionWithTarget() {

        if (obj.getAttackIndicator().intersects(player2.getHitBox()) && obj.getIsAttacking()
                || obj.getIsFullyCharged() && obj.getSuperAttackIndicator().intersects(player2.getHitBox())) {
            System.out.println("Player 1 hit Player 2 omfg");
            player2.getHealthSystem().dealDamage(1);
            if (player2.getHealth() <= 0) {
                arena.getPlayers().remove(player2);
            }
        }

        if (player2.getAttackIndicator().intersects(obj.getHitBox()) && player2.getIsAttacking()
                || player2.getIsFullyCharged() && player2.getSuperAttackIndicator().intersects(obj.getHitBox())) {
            System.out.println("Player 2 hit Player 1 omfg");
            obj.getHealthSystem().dealDamage(1);
            if (obj.getHealth() <= 0) {
                arena.getPlayers().remove(obj);
            }
        }

    }

    public void updateCameraRect() {
        float dY = line.getDY();
        float dX = +line.getDX();
        if (dX < 0) {
            dX = dX * -1;
        }
        if (dY < 0) {
            dY = dY * -1;
        }
        cameraWidth = dX + 100;
        if (cameraWidth < 404) {
            cameraWidth = 404;
        }
        cameraHeight = cameraWidth * 0.55f;
        if (cameraHeight < dY + 100) {
            cameraHeight = dY + 100;
            cameraWidth = cameraHeight / 0.55f;
        }

        cameraX = line.getCenterX() - (cameraWidth / 2);
        cameraY = line.getCenterY() - (cameraHeight / 2);

        if ((cameraX + cameraWidth) > 900) {
            float newX = (cameraX + cameraWidth) - 900;
            cameraX -= newX;
        } else if (cameraX < 0) {
            cameraX = 0;
        }

        if (cameraWidth > 900) {
            cameraWidth = 900;
            cameraHeight = cameraWidth * 0.55f;
            cameraX = 0;
            cameraY = 0;
        } else if (cameraHeight > 500) {
            cameraHeight = 500;
            cameraWidth = cameraHeight / 0.55f;
            cameraY = 0;
        }

        if ((cameraY + cameraHeight) > 495) {
            float newY = (cameraY + cameraHeight) - 495;
            cameraY -= newY;
        } else if (cameraY < 0) {
            cameraY = 0;
        }

        cameraScale = 900 / cameraWidth;

    }

    public void updateVectorLine() {
        Vector2f objVector = new Vector2f(obj.getX(), obj.getY());
        Vector2f player2Vector = new Vector2f(player2.getX(), player2.getY());
        line = new Line(objVector, player2Vector);
    }

    public void movePlayer2() {
        for (MPPlayer mpPlayer : client.getPlayers().values()) { //other player render here.
            player2.setX(mpPlayer.x);
            player2.setY(mpPlayer.y);
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.scale(cameraScale, cameraScale);
        g.translate(-cameraX, -cameraY);
        arena.getAnimation().draw(200, 50);
        arena.render();
        g.resetTransform();

        g.translate(-cameraX, -cameraY);
        obj.getHealthSystem().render(cameraX + 450 - 135 - 60, cameraY + 473);
        obj.renderStoredJumpsIndicator(cameraX + 450 - 135 - 60 - 10, cameraY + 473);
        obj.renderAttackCharge(cameraX + 450 - 134.5f - 60, cameraY + 465);
        player2.getHealthSystem().render(cameraX + 450 + 60, cameraY + 473);
        player2.renderStoredJumpsIndicator(cameraX + 450 + 135 + 60 + 2, cameraY + 473);
        player2.renderAttackChargeReversed(cameraX + 450 + 60 + 135.5f, cameraY + 465);
        g.resetTransform();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        updateVectorLine();
        updateCameraRect();
        movementSystem.handleInput(gc.getInput(), i);
        movePlayer2();
        physics.handlePhysics(arena, i);
        player2.update(i);
        obj.update(i);
        checkCollisionWithTarget();
        sendClientData();
    }

    private void sendClientData() {
        if (obj.networkPosition.x != obj.getCurrentX()) {
            //Send the player's X value
            PacketUpdateX packet = new PacketUpdateX();
            packet.x = obj.getCurrentX();
            client.getClient().sendUDP(packet);

            obj.networkPosition.x = obj.getCurrentX();
        }
        if (obj.networkPosition.y != obj.getCurrentY()) {
            //Send the player's Y value
            PacketUpdateY packet = new PacketUpdateY();
            packet.y = obj.getCurrentY();
            client.getClient().sendUDP(packet);

            obj.networkPosition.y = obj.getCurrentY();
        }
        if (obj.getIsAttacking()) {
            PacketAttackPlayer packet = new PacketAttackPlayer();
            PacketAttackDirectionPlayer packet2 = new PacketAttackDirectionPlayer();
            packet2.direction = (float) obj.getAttackDirection().getTheta();
            packet.isAttacking = obj.getIsAttacking();
            client.getClient().sendUDP(packet);
            client.getClient().sendUDP(packet2);
        } else {
            PacketAttackPlayer packet = new PacketAttackPlayer();
            PacketAttackDirectionPlayer packet2 = new PacketAttackDirectionPlayer();
            packet2.direction = (float) obj.getAttackDirection().getTheta();
            packet.isAttacking = obj.getIsAttacking();
            client.getClient().sendUDP(packet);
            client.getClient().sendUDP(packet2);
        }
        if (obj.getIsCharging()) {
            PacketChargePlayer packet = new PacketChargePlayer();
            packet.isChargeing = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketChargePlayer packet = new PacketChargePlayer();
            packet.isChargeing = false;
            client.getClient().sendUDP(packet);
        }
        if (obj.getIsFullyCharged()) {
            PacketFullyChargedPlayer packet = new PacketFullyChargedPlayer();
            packet.isFullyCharged = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketFullyChargedPlayer packet = new PacketFullyChargedPlayer();
            packet.isFullyCharged = false;
            client.getClient().sendUDP(packet);
        }
        if (obj.isMoving() && obj.getXVelocity() < 0) {
            PacketMoveLeftPlayer packet = new PacketMoveLeftPlayer();
            packet.moveingLeft = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketMoveLeftPlayer packet = new PacketMoveLeftPlayer();
            packet.moveingLeft = false;
            client.getClient().sendUDP(packet);
        }
        if (obj.isMoving() && obj.getXVelocity() > 0) {
            PacketMoveRightPlayer packet = new PacketMoveRightPlayer();
            packet.moveingRight = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketMoveRightPlayer packet = new PacketMoveRightPlayer();
            packet.moveingRight = false;
            client.getClient().sendUDP(packet);
        }
    }

}
