package com.fejkathegame.client;

import java.net.*;
import java.io.*;

/**
 * Created by Swartt on 2015-04-30.
 */
public class Client implements Runnable {

    String serverIp, playerName;
    int port = 6112;

    public Client(String ip, String playerName) {
        this.serverIp = ip;
        this.playerName = playerName;
    }

    @Override
    public void run() {

        try {
            System.out.println("Connecting to " + serverIp
                    + " on port " + port);

            Socket client = new Socket(serverIp, port);

            System.out.println("Just connected to "
                    + client.getRemoteSocketAddress());

            OutputStream outToServer = client.getOutputStream();

            DataOutputStream out
                    = new DataOutputStream(outToServer);

            out.writeUTF("Hello from " + playerName + " at "
                    + client.getLocalSocketAddress());

            InputStream inFromServer = client.getInputStream();

            DataInputStream in
                    = new DataInputStream(inFromServer);

            System.out.println("Server says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
