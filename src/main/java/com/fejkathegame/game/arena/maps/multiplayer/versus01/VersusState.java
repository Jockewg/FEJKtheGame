package com.fejkathegame.game.arena.maps.multiplayer.versus01;

import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.client.MPPlayer;
import com.fejkathegame.client.PacketAttackDirectionPlayer;
import com.fejkathegame.client.PacketAttackPlayer;
import com.fejkathegame.client.PacketChargePlayer;
import com.fejkathegame.client.PacketFallingPlayer;
import com.fejkathegame.client.PacketFullyChargedPlayer;
import com.fejkathegame.client.PacketGroundedPlayer;
import com.fejkathegame.client.PacketHpPlayer;
import com.fejkathegame.client.PacketJumpPlayer;
import com.fejkathegame.client.PacketMoveLeftPlayer;
import com.fejkathegame.client.PacketMoveRightPlayer;
import com.fejkathegame.client.PacketUpdateX;
import com.fejkathegame.client.PacketUpdateY;
import com.fejkathegame.game.entities.logic.MovementSystem;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.entities.Character;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

public class VersusState extends BasicGameState {

    ClientProgram client = new ClientProgram();

    private Versus arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private Character localPlayer;

    private boolean hasUpdated = true;

    //Camera stuff
    private float cameraX, cameraY;
    private float cameraWidth = 900;
    private float cameraHeight = cameraWidth * 0.55f;
    private float cameraScale = 1.0f;

    private Line line;

    private Polygon playerIndicator;

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

        localPlayer = null;
        try {
            localPlayer = new Character(800, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }

        characters = new ArrayList<>();
        characters.add(localPlayer);

        line = new Line(0, 0, 450, 250);

        playerIndicator = new Polygon();
        playerIndicator.addPoint(0, 0);
        playerIndicator.addPoint(20, 0);
        playerIndicator.addPoint(10, 10);

        arena = new Versus(name, localPlayer);

        movementSystem = new MovementSystem(localPlayer);

        physics = new Physics();
        client.network();

    }

    public void checkCollisionWithTarget() {

        for (MPPlayer mp : client.getPlayers().values()) {
            if (mp.character.getAttackIndicator().intersects(localPlayer.getHitBox()) && mp.isAttacking && mp.character.getHealth() >= 1
                    || mp.character.getIsFullyCharged() && mp.character.getSuperAttackIndicator().intersects(localPlayer.getHitBox())
                    && mp.character.getHealth() >= 1) {
                System.out.println("you got hit!");
                localPlayer.getHealthSystem().dealDamage(1);
                PacketHpPlayer packet = new PacketHpPlayer();
                packet.hp = localPlayer.getHealth();
                client.getClient().sendUDP(packet);
                System.out.println(mp.hp);
            }
            if (mp.hp == 0 && mp.character.isAlive()) {
                System.out.println("remove");
                mp.character.setAlive(false);
                arena.getPlayers().remove(mp.character);
                characters.remove(mp.character);
            }
        }

        if (localPlayer.getHealth() <= 0) {
            localPlayer.setAlive(false);
            arena.getPlayers().remove(localPlayer);
            characters.remove(localPlayer);
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
        if (characters.isEmpty()) {
            line = new Line(0, 0, 900, 500);
        } else if (characters.size() == 2) {
            for (MPPlayer mp : client.getPlayers().values()) {
                Vector2f objVector = new Vector2f(localPlayer.getX(), localPlayer.getY());
                Vector2f player2Vector = new Vector2f(mp.x, mp.y);
                line = new Line(objVector, player2Vector);
            }
        } else {
            line = new Line(0, 0, 900, 500);
        }
    }

    public void updatePlayerIndicator() {
        playerIndicator.setX(localPlayer.getX() - 4);
        playerIndicator.setY(localPlayer.getY() - 17);
    }

    public void renderPlayerIndicator(Graphics g) {
        g.setColor(new Color(1.0f, 0.0f, 0.0f, 0.5f));
        g.fill(playerIndicator);
    }

    public void updateMpPlayer(int i) {
        for (MPPlayer mp : client.getPlayers().values()) { //other player render here.
            if (mp.character.getHealth() > 0) {
                mp.character.update(i);
                mp.character.setX(mp.x);
                mp.character.setY(mp.y);
                mp.character.setMovingLeft(mp.moveingLeft);
                if (mp.moveingLeft) {
                    mp.character.setFlipped(true);
                }
                mp.character.setMovingRight(mp.moveingRight);
                if (mp.moveingRight) {
                    mp.character.setFlipped(false);
                }
                mp.character.setIsCharging(mp.isChargeing);
                mp.character.setIsFullyCharged(mp.isFullyCharged);
                mp.character.setIsJumping(mp.isJumping);
                mp.character.setIsFalling(mp.isFalling);
                mp.character.setGrounded(mp.isGrounded);
                if (mp.isAttacking && hasUpdated) {
                    mp.character.setRotateDirection(mp.direction);
                    mp.character.updateAttackIndicator();
                    hasUpdated = false;
                } else if (!mp.isAttacking) {
                    hasUpdated = true;
                }

                if (mp.isChargeing) {
                    mp.character.chargeSuperAttack(i);
                } else if (mp.isFullyCharged) {
                    mp.character.activateSuperAttack(i);
                }
            }
        }
    }

    public void checkIfNewPlayerConnected() {
        for (MPPlayer mpPlayer : client.getPlayers().values()) { //other player render here.
            if (mpPlayer.character == null) {
                try {
                    mpPlayer.character = new Character(300, 40);
                } catch (SlickException | IOException ex) {
                    Logger.getLogger(VersusState.class.getName()).log(Level.SEVERE, null, ex);
                }
                mpPlayer.character.setHealth(5);
                mpPlayer.hp = 5;
                characters.add(mpPlayer.character);
                arena.addPlayer(mpPlayer.character);
            }
            if (mpPlayer.connected == false) {
                arena.getPlayers().remove(mpPlayer.character);
                characters.remove(mpPlayer.character);
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.scale(cameraScale, cameraScale);
        g.translate(-cameraX, -cameraY);
        arena.render();
        renderPlayerIndicator(g);
        for (MPPlayer mp : client.getPlayers().values()) {
            if (mp.character.getHealth() >= 1) {
                if (mp.isAttacking) {
                    mp.character.renderAttackIndicator();
                    mp.character.setAttackIndicatorTransp(1.0f);
                }

                mp.character.renderCharacterAnimation();
            }
        }
        g.resetTransform();

        g.translate(-cameraX, -cameraY);
        localPlayer.getHealthSystem().render(cameraX + 450 - 135 - 60, cameraY + 473);
        localPlayer.renderStoredJumpsIndicator(cameraX + 450 - 135 - 60 - 10, cameraY + 473);
        localPlayer.renderAttackCharge(cameraX + 450 - 134.5f - 60, cameraY + 465);
        g.resetTransform();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        checkIfNewPlayerConnected();
        updateVectorLine();
        updateCameraRect();
        movementSystem.handleInput(gc.getInput(), i);
        updateMpPlayer(i);
        physics.handlePhysics(arena, i);
        localPlayer.update(i);
        updatePlayerIndicator();
        checkCollisionWithTarget();
        sendClientData();
    }

    private void sendClientData() {
        if (localPlayer.networkPosition.x != localPlayer.getCurrentX()) {
            //Send the player's X value
            PacketUpdateX packet = new PacketUpdateX();
            packet.x = localPlayer.getCurrentX();
            client.getClient().sendUDP(packet);

            localPlayer.networkPosition.x = localPlayer.getCurrentX();
        }
        if (localPlayer.networkPosition.y != localPlayer.getCurrentY()) {
            //Send the player's Y value
            PacketUpdateY packet = new PacketUpdateY();
            packet.y = localPlayer.getCurrentY();
            client.getClient().sendUDP(packet);

            localPlayer.networkPosition.y = localPlayer.getCurrentY();
        }
        if (localPlayer.getIsAttacking()) {
            PacketAttackPlayer packet = new PacketAttackPlayer();
            PacketAttackDirectionPlayer packet2 = new PacketAttackDirectionPlayer();
            packet2.direction = (float) localPlayer.getAttackDirection().getTheta();
            packet.isAttacking = localPlayer.getIsAttacking();
            client.getClient().sendUDP(packet);
            client.getClient().sendUDP(packet2);
        } else {
            PacketAttackPlayer packet = new PacketAttackPlayer();
            PacketAttackDirectionPlayer packet2 = new PacketAttackDirectionPlayer();
            packet2.direction = (float) localPlayer.getAttackDirection().getTheta();
            packet.isAttacking = localPlayer.getIsAttacking();
            client.getClient().sendUDP(packet);
            client.getClient().sendUDP(packet2);
        }
        if (localPlayer.getIsCharging()) {
            PacketChargePlayer packet = new PacketChargePlayer();
            packet.isChargeing = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketChargePlayer packet = new PacketChargePlayer();
            packet.isChargeing = false;
            client.getClient().sendUDP(packet);
        }
        if (localPlayer.getIsFullyCharged()) {
            PacketFullyChargedPlayer packet = new PacketFullyChargedPlayer();
            packet.isFullyCharged = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketFullyChargedPlayer packet = new PacketFullyChargedPlayer();
            packet.isFullyCharged = false;
            client.getClient().sendUDP(packet);
        }
        if (localPlayer.getMovingLeft()) {
            PacketMoveLeftPlayer packet = new PacketMoveLeftPlayer();
            packet.moveingLeft = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketMoveLeftPlayer packet = new PacketMoveLeftPlayer();
            packet.moveingLeft = false;
            client.getClient().sendUDP(packet);
        }
        if (localPlayer.getMovingRight()) {
            PacketMoveRightPlayer packet = new PacketMoveRightPlayer();
            packet.moveingRight = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketMoveRightPlayer packet = new PacketMoveRightPlayer();
            packet.moveingRight = false;
            client.getClient().sendUDP(packet);
        }
        if (localPlayer.isJumping()) {
            PacketJumpPlayer packet = new PacketJumpPlayer();
            packet.isJumping = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketJumpPlayer packet = new PacketJumpPlayer();
            packet.isJumping = false;
            client.getClient().sendUDP(packet);
        }
        if (localPlayer.isFalling()) {
            PacketFallingPlayer packet = new PacketFallingPlayer();
            packet.isFalling = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketFallingPlayer packet = new PacketFallingPlayer();
            packet.isFalling = false;
            client.getClient().sendUDP(packet);
        }
        if (localPlayer.getGrounded()) {
            PacketGroundedPlayer packet = new PacketGroundedPlayer();
            packet.isGrounded = true;
            client.getClient().sendUDP(packet);
        } else {
            PacketGroundedPlayer packet = new PacketGroundedPlayer();
            packet.isGrounded = false;
            client.getClient().sendUDP(packet);
        }
    }
}
