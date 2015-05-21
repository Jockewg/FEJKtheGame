package com.fejkathegame.game.arena.maps.multiplayer.versus01;

import com.fejkathegame.client.*;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.arena.maps.UIHelper;
import com.fejkathegame.game.arena.physics.Physics;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.game.entities.logic.MovementSystem;
import com.fejkathegame.game.multiplayer.lobby.LobbyState;
import com.fejkathegame.game.multiplayer.stats.StatsState;
import com.fejkathegame.game.timer.Timer;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class VersusState extends State {

    private ClientProgram client;
    private Versus arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private Character localPlayer;

    private boolean hasUpdated = true;
    private int percent = 0;

    private ArrayList<Character> characters;

    private Timer timer;
    private Timer powerUpTimer;

    //Camera stuff
    private float cameraX, cameraY;
    private float cameraWidth = 900;
    private float cameraHeight = cameraWidth * 0.55f;
    private float cameraScale = 1.0f;

    private Line line;

    private Polygon playerIndicator;

    private UIHelper vsUI;

    /**
     * Constructor for ArenaState
     *
     * @param name of the stage
     * @param client
     * @param localPlayer
     * @param characters
     */
    public VersusState(String name, ClientProgram client, Character localPlayer, ArrayList<Character> characters) {
        this.name = name;
        this.client = client;
        this.localPlayer = localPlayer;
        this.characters = characters;
    }

    @Override
    public int getID() {
        return Main.VERSUSSTATE;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        System.out.println("Creating arraylist");

        line = new Line(0, 0, 450, 250);

        playerIndicator = new Polygon();
        playerIndicator.addPoint(0, 0);
        playerIndicator.addPoint(20, 0);
        playerIndicator.addPoint(10, 10);

        arena = new Versus(name);

        for (Character c : characters) {
            arena.players.add(c);
        }
        vsUI = new UIHelper(this);

        movementSystem = new MovementSystem(localPlayer);

        physics = new Physics();
        powerUpTimer = new Timer();
        timer = new Timer();
        timer.startCountdown(3);
        powerUpTimer.startTimer();
        sbg.addState(new StatsState("Stats", client, localPlayer, characters));

    }

    public void checkCollisionWithTarget(MPPlayer mp) {
        if (localPlayer.getAttackIndicator().intersects(mp.character.getHitBox()) && localPlayer.getIsAttacking()) {
            if (mp.character.isAlive()) {
                localPlayer.getHealthSystem().playHurtSound();
                localPlayer.setNumberOfHits(localPlayer.getNumberOfHits() + 1);
            }
        }
        if (mp.character.getAttackIndicator().intersects(localPlayer.getHitBox()) && mp.isAttacking
                || mp.character.getIsFullyCharged() && mp.character.getSuperAttackIndicator().intersects(localPlayer.getHitBox())) {
            if (mp.character.isAlive()) {
                localPlayer.getHealthSystem().dealDamage(1);
                localPlayer.setIsCharging(false);
            }
            PacketHpPlayer packet = new PacketHpPlayer();
            packet.hp = localPlayer.getHealth();
            client.getClient().sendUDP(packet);
            System.out.println(mp.hp);
        }
        if (mp.hp == 0 && mp.character.isAlive()) {
            mp.character.setAlive(false);
            arena.players.remove(mp.character);
        }

        if (localPlayer.getHealth() <= 0) {
            localPlayer.setAlive(false);
            arena.players.remove(localPlayer);
        }
        
        if(localPlayer.getHitBox().intersects(arena.getPowerUp().getHitBox())){
            if (arena.getPowerUp().isAlive() && localPlayer.getHealth()<5){
                arena.getPowerUp().boost(localPlayer);
                arena.getPowerUp().setAlive(false);
                arena.getPowerUp().changePositionRandom();
                powerUpTimer.startTimer();
            }
        }
            
    }

    public void updatePowerUp(int delta){
        if(powerUpTimer.getTimerDuration() == 10){
            arena.getPowerUp().setAlive(true);
            powerUpTimer.stopTimer();
            powerUpTimer.resetTimer();
        }
        powerUpTimer.calculateSecond(delta);
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
        if (arena.players.isEmpty()) {
            line = new Line(0, 0, 900, 500);
        } else if (arena.players.size() == 2) {
            for (MPPlayer mp : client.getPlayers().values()) {
                if (mp.hp >= 1 && localPlayer.getHealth() >= 1) {
                    Vector2f objVector = new Vector2f(localPlayer.getX(), localPlayer.getY());
                    Vector2f player2Vector = new Vector2f(mp.x, mp.y);
                    line = new Line(objVector, player2Vector);
                }
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

    public void updateMpPlayer(MPPlayer mp, int i) {
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
//            if (mp.isJumping) {
//                mp.character.playJumpSound();
//            }
            mp.character.setIsFalling(mp.isFalling);
            mp.character.setGrounded(mp.isGrounded);
            if (mp.isAttacking && hasUpdated) {
                mp.character.setRotateDirection(mp.direction);
                mp.character.updateAttackIndicator();
                hasUpdated = false;
            } else if (!mp.isAttacking) {
                hasUpdated = true;
            }

            if (mp.isChargeing && mp.character.getHealth() >= 1) {
                mp.character.chargeSuperAttack(i);
            } else if (mp.isFullyCharged && mp.character.getHealth() >= 1) {
                mp.character.activateSuperAttack(i);
            } else {
                mp.character.stopChargeSound();
            }

            if (!mp.connected) {
                arena.players.remove(mp.character);
                characters.remove(mp.character);
            }
        }
    }

    public void renderCountdown(float x, float y, Graphics g) {
        g.setColor(Color.green);
        g.drawString(String.valueOf(timer.getCurrentCountdownTime()), x, y);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.scale(cameraScale, cameraScale);
        g.translate(-cameraX, -cameraY);
        arena.render();
        renderPlayerIndicator(g);

        g.resetTransform();

        g.translate(-cameraX, -cameraY);
        if (timer.getCurrentCountdownTime() > 0) {
            renderCountdown(450, 250, g);
        }
        vsUI.renderVersusUI(localPlayer, cameraX, cameraY);
        g.resetTransform();
    }

    private void checkWinLose(StateBasedGame sbg, GameContainer gc) throws SlickException {
        if (arena.players.size() == 1) {
            sbg.getState(Main.STATSSTATE).init(gc, sbg);
            sbg.enterState(Main.STATSSTATE);
//            calculateHitPercent();
        } else if (arena.players.isEmpty()) {
            sbg.getState(Main.STATSSTATE).init(gc, sbg);
            sbg.enterState(Main.STATSSTATE);
//            calculateHitPercent();
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        updateVectorLine();
        updateCameraRect();
        timer.calculateSecond(i);
        if (!timer.isCountdownRunning()) {
            movementSystem.handleInput(gc.getInput(), i);
            for (MPPlayer mp : client.getPlayers().values()) {
                updateMpPlayer(mp, i);
                checkCollisionWithTarget(mp);
                if (mp.character.getHealth() >= 1) {
                    if (mp.isAttacking) {
                        mp.character.renderAttackIndicator();
                        mp.character.setAttackIndicatorTransp(1.0f);
                    }
                    mp.character.renderCharacterAnimation();
                }
            }
            updatePowerUp(i);
            physics.handlePhysics(arena, i);
            localPlayer.update(i);
            updatePlayerIndicator();
            checkWinLose(sbg, gc);
        }
        sendClientData();
    }
    
//    public void calculateHitPercent() {
//        percent = (localPlayer.getNumberOfAttacks()/localPlayer.getNumberOfHits());
//    }

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
