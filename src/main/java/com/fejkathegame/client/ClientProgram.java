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
 * Created by Swartt on 2015-04-30.
 */
public class ClientProgram extends Listener {

    Client client;
    String serverIp, playerName;
    int tcpPort = 27960, updPort = 27960;
    Map<Integer, MPPlayer> players = new HashMap<>();

    public ClientProgram(String serverIp, String playerName) {
        this.serverIp = serverIp;
        this.playerName = playerName;
    }

    public ClientProgram() {
    }

    public void network() {
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
        client.addListener(this);

        client.start();
        try {
            client.connect(5000, "localhost", tcpPort, updPort);
        } catch (IOException ex) {
            Logger.getLogger(ClientProgram.class.getName()).log(Level.SEVERE, null, ex);
        }

        client.addListener(new ClientProgram());
        System.out.println("Connected! The client program is now waiting for a packet...\n");

    }

    @Override
    public void received(Connection c, Object o) {
        if (o instanceof PacketAddPlayer) {
            PacketAddPlayer packet = (PacketAddPlayer) o;
            MPPlayer newPlayer = new MPPlayer();
            players.put(packet.id, newPlayer);
        } else if (o instanceof PacketRemovePlayer) {
            PacketRemovePlayer packet2 = (PacketRemovePlayer) o;
            players.remove(packet2.id);
        } else if (o instanceof PacketUpdateX) {
            PacketUpdateX packet = (PacketUpdateX) o;
            players.get(packet.id).x = packet.x;
        } else if (o instanceof PacketUpdateY) {
            PacketUpdateY packet = (PacketUpdateY) o;
            players.get(packet.id).y = packet.y;
        } else if (o instanceof PacketAttackPlayer) {
            PacketAttackPlayer packet = (PacketAttackPlayer) o;
            players.get(packet.id).isAttacking = packet.isAttacking;
            if (players.get(c.getID()).isAttacking == true) {
                System.out.println("client " + packet.id + "  is attacking");
            }
        } else if (o instanceof PacketAttackDirectionPlayer) {
            PacketAttackDirectionPlayer packet = (PacketAttackDirectionPlayer) o;
            players.get(c.getID()).direction = packet.direction;
            if (players.get(c.getID()).isAttacking == true) {
                System.out.println("direction: " + packet.direction);
            }
        } else if (o instanceof PacketChargePlayer) {
            PacketChargePlayer packet = (PacketChargePlayer) o;
            players.get(packet.id).isChargeing = packet.isChargeing;
            if (players.get(c.getID()).isChargeing == true) {
                System.out.println("client " + packet.id + "  is chargeing");
            }
        } else if (o instanceof PacketFullyChargedPlayer) {
            PacketFullyChargedPlayer packet = (PacketFullyChargedPlayer) o;
            players.get(packet.id).isFullyCharged = packet.isFullyCharged;
            if (players.get(c.getID()).isFullyCharged == true) {
                System.out.println("client " + packet.id + "  it fully charged");
            }
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

}
