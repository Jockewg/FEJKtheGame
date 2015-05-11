package com.fejkathegame.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created by Swartt on 2015-04-30.
 */
public class ClientProgram extends Listener {

    static Client client;
    static String ip = "localhost";
    static int tcpPort = 27960, updPort = 27960;
    static boolean messageReceived = false;

    public static void main(String[] args) throws Exception {
        System.out.println("Connecting to the server...");
        client = new Client();

        client.getKryo().register(PacketMessage.class);
        
        client.start();

        client.connect(5000, ip, tcpPort, updPort);

        client.addListener(new ClientProgram());

        System.out.println("Connected! The client program is now waiting for a packet...\n");
        
        while(!messageReceived) {
            Thread.sleep(1000);
        }
        System.out.println("Client will now exit");
        System.exit(0);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof PacketMessage) {
            PacketMessage packet = (PacketMessage) object;
            System.out.println("recived a packade from the host: " + packet.message);
            
            messageReceived = true;
        }
    }

    @Override
    public void disconnected(Connection connection) {
    }

    @Override
    public void connected(Connection connection) {
    }

}
