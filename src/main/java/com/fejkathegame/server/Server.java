package com.fejkathegame.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Swartt on 2015-04-30.
 */
public class Server {
    protected static ServerSocket serverSocket;
    protected static Socket clientSocket;
    protected static BufferedReader bufferedReader;
    protected static String inputLine;
    protected static int port = 6112;


    public static void main(String[] args) {
        connect();
    }


    public static void connect() {
        try {
            serverSocket = new ServerSocket(port);

            clientSocket = serverSocket.accept();

            // Create a reader

            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Get the client message

            while ((inputLine = bufferedReader.readLine()) != null)

                System.out.println(inputLine);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


}