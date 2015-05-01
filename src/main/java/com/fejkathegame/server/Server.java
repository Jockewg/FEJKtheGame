package com.fejkathegame.server;

import com.fejkathegame.game.Character.Character;

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
    protected ArrayList<Character> players = new ArrayList<>();


    public void connect() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

          /*  bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));*/
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e);
        }
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println(e);
            }
            new ServerThread(clientSocket).start();
        }


    }

}

class ServerThread extends Thread {
    protected Socket clientSocket;

    public ServerThread (Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
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
