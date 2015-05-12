package com.fejkathegame.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.fejkathegame.client.Message;
import java.util.Date;
/**
 *
 * @author Filip
 */
public class ServerProgram  extends Listener{
    
    static Server server;
    static int updPort = 27960, tcpPort = 27960;
    static String player;
    
    public static void main(String[] args) throws Exception{
        
        server = new Server();
        
        server.getKryo().register(PacketMessage.class);
        server.getKryo().register(Message.class);
        
        server.bind(tcpPort, updPort);
        
        server.start();
        
        server.addListener(new ServerProgram()); 
        System.out.println("Server is operational");
    }
    
    @Override
    public void connected(Connection c) {
        System.out.println("Received a connection from: " + c.getRemoteAddressTCP().getHostString());
        
        PacketMessage packetMessage = new PacketMessage();
        packetMessage.setMessage("Hello friend! the time is: " + new Date().toString());
        
        c.sendTCP(packetMessage);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Message) {
            Message message = (Message) object;
            player = message.getMessage();
            System.out.println(message.getMessage());
            System.out.println("recived a packade from the client: " + player); //Playername here. somehow its value us null
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("A client disconnected");
    }
    
    
}
