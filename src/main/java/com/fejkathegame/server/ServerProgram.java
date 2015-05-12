package com.fejkathegame.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Filip
 */
public class ServerProgram  extends Listener{
    
    static Server server;
    static int updPort = 27960, tcpPort = 27960;
    //Player will prob. change to charracter or something equal to it.
    static Map<Integer, Player > players = new HashMap<>();
    
    public static void main(String[] args) throws Exception{
        server = new Server();
        
        server.getKryo().register(PacketUpdateY.class);
        server.getKryo().register(PacketUpdateX.class);
        server.getKryo().register(PacketAddPlayer.class);
        server.getKryo().register(PacketRemovePlayer.class);
        
        server.bind(tcpPort, updPort);
        server.start();
        server.addListener(new ServerProgram()); 
        System.out.println("Server is operational");
    }
    
    @Override
    public void connected(Connection connection) { 
        Player player = new Player();
        player.x = 256;
        player.y = 256;
        player.c = connection;
        
        PacketAddPlayer packet = new PacketAddPlayer();
        packet.id = connection.getID();
        server.sendToAllExceptTCP(connection.getID(), packet);
        
        for (Player p : players.values()) {
            PacketAddPlayer packet2 = new PacketAddPlayer();
            packet2.id = p.c.getID();
            connection.sendTCP(packet2);
        }
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof PacketUpdateX){
			PacketUpdateX packet = (PacketUpdateX) object;
			players.get(connection.getID()).x = packet.x;
			
			packet.id = connection.getID();
			server.sendToAllExceptUDP(connection.getID(), packet);
			
		}else if(object instanceof PacketUpdateY){
			PacketUpdateY paket2 = (PacketUpdateY) object;
			players.get(connection.getID()).y = paket2.y;
			
			paket2.id = connection.getID();
			server.sendToAllExceptUDP(connection.getID(), paket2);
			
		}
    }

    @Override
    public void disconnected(Connection connection) {
        players.remove(connection.getID());
        PacketRemovePlayer packet = new PacketRemovePlayer();
        packet.id = connection.getID();
        server.sendToAllExceptTCP(connection.getID(), packet);
        System.out.println("A client disconnected");
    }
    
    
}
