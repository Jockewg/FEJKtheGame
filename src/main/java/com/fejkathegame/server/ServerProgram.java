package com.fejkathegame.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.util.Date;
/**
 *
 * @author Filip
 */
public class ServerProgram  extends Listener{
    
    static Server server;
    static int updPort = 27960, tcpPort = 27960;
    
    public static void main(String[] args) throws Exception{
        
        server = new Server();
        
        server.getKryo().register(PacketMessage.class);
        
        server.bind(tcpPort, updPort);
        
        server.start();
        
        server.addListener(new ServerProgram()); 
        System.out.println("Server is operational");
    }
    
    @Override
    public void connected(Connection c) {
        System.out.println("Received a connection from: " + c.getRemoteAddressTCP().getHostString());
        
        PacketMessage packetMessage = new PacketMessage();
        packetMessage.message = "Hello friend! the tome is: " + new Date().toString();
        
        c.sendTCP(packetMessage);
    }

    @Override
    public void received(Connection connection, Object object) {
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("A client disconnected");
    }
    
    
}
