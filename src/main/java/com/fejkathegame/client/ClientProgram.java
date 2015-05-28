package com.fejkathegame.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Handles the connection between the server and client as well as receiving information
 *
 * @author Filip.
 */
public class ClientProgram extends Listener {

    Client client;
    String serverIp, playerName;
    int tcpPort = 27960, updPort = 27960;
    Map<Integer, MPPlayer> players = new HashMap<>();
    boolean clientStarted = false;
    boolean serverIsPlaying = false;

    public ClientProgram(String serverIp, String playerName) {
        this.serverIp = serverIp;
        this.playerName = playerName;
    }

    public ClientProgram() {
    }

    /**
     *
     * Starts the client and registers the packets to send/receive and tries to connect to server
     *
     * @param ip The servers ip
     */
    public void network(String ip) {
        System.out.println("Connecting to the server...");
        client = new Client();

        client.getKryo().register(PacketUpdateX.class);
        client.getKryo().register(PacketUpdateY.class);
        client.getKryo().register(PacketAddPlayer.class);
        client.getKryo().register(PacketRemovePlayer.class);
        client.getKryo().register(PacketAttackPlayer.class);
        client.getKryo().register(PacketChargePlayer.class);
        client.getKryo().register(PacketFullyChargedPlayer.class);
        client.getKryo().register(PacketAttackDirectionPlayer.class);
        client.getKryo().register(PacketMoveLeftPlayer.class);
        client.getKryo().register(PacketMoveRightPlayer.class);
        client.getKryo().register(PacketJumpPlayer.class);
        client.getKryo().register(PacketGroundedPlayer.class);
        client.getKryo().register(PacketFallingPlayer.class);
        client.getKryo().register(PacketHpPlayer.class);
        client.getKryo().register(PacketNamePlayer.class);
        client.getKryo().register(PacketReadyPlayer.class);
        client.getKryo().register(PacketServerIsPlaying.class);
        client.addListener(this);

        client.start();
        clientStarted = true;
        try {
            client.connect(5000, ip, tcpPort, updPort);
        } catch (IOException ex) {
            Logger.getLogger(ClientProgram.class.getName()).log(Level.SEVERE, null, ex);
        }

        client.addListener(new ClientProgram());
        System.out.println("Connected! The client program is now waiting for a packet...\n");

    }
  
    /**
     *
     * Determines what type of package is received and handles the logical response for that type of package
     *
     * @param c the connection to the server
     * @param o the objec the client receives
     */
    @Override
    public void received(Connection c, Object o) {
        if (o instanceof PacketAddPlayer) {
            PacketAddPlayer packet = (PacketAddPlayer) o;
            MPPlayer newPlayer = new MPPlayer();
            players.put(packet.id, newPlayer);
            players.get(packet.id).name = packet.name;
            players.get(packet.id).connected = true;
            players.get(packet.id).ready = false;
        } else if (o instanceof PacketRemovePlayer) {
            PacketRemovePlayer packet = (PacketRemovePlayer) o;
            players.get(packet.id).connected = false;
        } else if (o instanceof PacketUpdateX) {
            PacketUpdateX packet = (PacketUpdateX) o;
            players.get(packet.id).x = packet.x;
        } else if (o instanceof PacketUpdateY) {
            PacketUpdateY packet = (PacketUpdateY) o;
            players.get(packet.id).y = packet.y;
        } else if (o instanceof PacketAttackPlayer) {
            PacketAttackPlayer packet = (PacketAttackPlayer) o;
            players.get(packet.id).isAttacking = packet.isAttacking;
        } else if (o instanceof PacketAttackDirectionPlayer) {
            PacketAttackDirectionPlayer packet = (PacketAttackDirectionPlayer) o;
            players.get(packet.id).direction = packet.direction;
        } else if (o instanceof PacketChargePlayer) {
            PacketChargePlayer packet = (PacketChargePlayer) o;
            players.get(packet.id).isChargeing = packet.isChargeing;
        } else if (o instanceof PacketFullyChargedPlayer) {
            PacketFullyChargedPlayer packet = (PacketFullyChargedPlayer) o;
            players.get(packet.id).isFullyCharged = packet.isFullyCharged;
        } else if (o instanceof PacketMoveLeftPlayer) {
            PacketMoveLeftPlayer packet = (PacketMoveLeftPlayer) o;
            players.get(packet.id).moveingLeft = packet.moveingLeft;
        } else if (o instanceof PacketMoveRightPlayer) {
            PacketMoveRightPlayer packet = (PacketMoveRightPlayer) o;
            players.get(packet.id).moveingRight = packet.moveingRight;
        } else if (o instanceof PacketJumpPlayer) {
            PacketJumpPlayer packet = (PacketJumpPlayer) o;
            players.get(packet.id).isJumping = packet.isJumping;
        } else if (o instanceof PacketFallingPlayer) {
            PacketFallingPlayer packet = (PacketFallingPlayer) o;
            players.get(packet.id).isFalling = packet.isFalling;
        } else if (o instanceof PacketGroundedPlayer) {
            PacketGroundedPlayer packet = (PacketGroundedPlayer) o;
            players.get(packet.id).isGrounded = packet.isGrounded;
        } else if (o instanceof PacketHpPlayer) {
            PacketHpPlayer packet = (PacketHpPlayer) o;
            players.get(packet.id).hp = packet.hp;
        } else if (o instanceof PacketNamePlayer) {
            PacketNamePlayer packet = (PacketNamePlayer) o;
            players.get(packet.id).name = packet.name;
        } else if (o instanceof PacketReadyPlayer) {
            PacketReadyPlayer packet = (PacketReadyPlayer) o;
            players.get(packet.id).ready = packet.ready;
        } else if (o instanceof PacketServerIsPlaying) {
            PacketServerIsPlaying packet = (PacketServerIsPlaying) o;
            serverIsPlaying = packet.serverIsPlaying;
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Map<Integer, MPPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Integer, MPPlayer> players) {
        this.players = players;
    }
    
    public boolean serverIsPlaying() {
        return serverIsPlaying;
    }
}
