package com.fejkathegame.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Swartt on 2015-04-30.
 */
public class ClientProgram extends Listener implements Runnable {

    Client client;
    String serverIp, playerName;
    int tcpPort = 27960, updPort = 27960;
    boolean messageReceived = false;
    public Kryo getKryo;

    public ClientProgram(String serverIp, String playerName) {
        this.serverIp = serverIp;
        this.playerName = playerName;
    }

    public ClientProgram() {
    }

    @Override
    public void run() {
        System.out.println("Connecting to the server...");
        System.out.println("playername: " + playerName);
        client = new Client();

        client.getKryo().register(PacketMessage.class);
        client.getKryo().register(Message.class);

        client.start();

        try {
            client.connect(5000, serverIp, tcpPort, updPort);
        } catch (IOException ex) {
            Logger.getLogger(ClientProgram.class.getName()).log(Level.SEVERE, null, ex);
        }

        client.addListener(new ClientProgram());

        System.out.println("Connected! The client program is now waiting for a packet...\n");

        while (!messageReceived) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientProgram.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("Client will now exit");
        System.exit(0);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof PacketMessage) {
            PacketMessage packet = (PacketMessage) object;
            System.out.println("recived a packade from the host: " + packet.getMessage());

            messageReceived = true;
            System.out.println(messageReceived);
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("Lost connection to the server");
    }

    @Override
    public void connected(Connection connection) {
        System.out.println("Received a connection from server at: " + connection.getRemoteAddressTCP().getHostString());

        Message message = new Message();
        message.setMessage(playerName);

        connection.sendTCP(message);
    }
}
