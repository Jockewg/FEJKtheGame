package com.fejkathegame.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Swartt on 2015-04-30.
 */
public class Server {
    protected ServerSocket serverSocket;
    protected Socket clientSocket;
    protected BufferedReader bufferedReader;
    protected String inputLine;
    protected int port = 6112;
    protected ArrayList<Socket> players = new ArrayList<>();


    public Server() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("server says: " + e);
        }
    }

    public void handleConnection() {
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                new ServerThread(clientSocket).run();
            } catch (IOException e) {
                System.out.println("derp");
                System.out.println("handleConnection says: "+e);
            }


        }
    }

    public void sendToAllPlayers() {

        for (Socket player : players) {
            try {
                DataOutputStream out = new DataOutputStream(player.getOutputStream());
                out.writeChars("Test");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


class ServerThread implements Runnable {
    protected Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;

        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        InputStream inp;
        BufferedReader brinp;
        DataOutputStream out;
        try {
            inp = clientSocket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    clientSocket.close();
                    return;
                } else {
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}

