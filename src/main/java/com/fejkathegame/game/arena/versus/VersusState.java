package com.fejkathegame.game.arena.versus;

import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.client.MPPlayer;
import com.fejkathegame.client.PacketAttackPlayer;
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

public class VersusState extends BasicGameState {

    private Versus arena;
    private String name;
    private MovementSystem movementSystem;
    private Physics physics;
    private Character obj;
    private Character player2;
    ClientProgram client = new ClientProgram();

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
        return 1;
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

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.scale(Main.SCALE, Main.SCALE);
        arena.getAnimation().draw(200, 50);
        arena.render();
        for(MPPlayer mpPlayer : client.getPlayers().values()){
			g.drawRect(mpPlayer.x, mpPlayer.y, 32, 32);
                        
		}

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        movementSystem.handleInput(gc.getInput(), i);
        physics.handlePhysics(arena, i);
        player2.update(i);
        obj.update(i);
        checkCollisionWithTarget();

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
        if (obj.getIsAttacking() || !obj.getIsAttacking()) {
            PacketAttackPlayer packet = new PacketAttackPlayer();
            packet.direction = (float) obj.getAttackDirection().getTheta();
            packet.isAttacking = obj.getIsAttacking();
            client.getClient().sendTCP(packet);
        }
    }

}
