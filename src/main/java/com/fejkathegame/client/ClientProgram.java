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
    Map<Integer,MPPlayer> players = new HashMap<>(); 

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
    public void received(Connection connection, Object object) {
        if(object instanceof PacketAddPlayer){
			PacketAddPlayer packet = (PacketAddPlayer) object;
			MPPlayer newPlayer = new MPPlayer();
			players.put(packet.id, newPlayer);
			
		}else if(object instanceof PacketRemovePlayer){
			PacketRemovePlayer packet2 = (PacketRemovePlayer) object;
			players.remove(packet2.id);
			
		}else if(object instanceof PacketUpdateX){
			PacketUpdateX packet3 = (PacketUpdateX) object;
			players.get(packet3.id).x = packet3.x;
			
		}else if(object instanceof PacketUpdateY){
			PacketUpdateY packet4 = (PacketUpdateY) object;
			players.get(packet4.id).y = packet4.y;
			
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
