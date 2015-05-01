package com.fejkathegame.client;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Swartt on 2015-04-30.
 */
public class Client {
    protected Socket socket;
    protected PrintWriter printWriter;
    protected InetAddress address;
    protected String playerName;
    protected int port = 6112;

    /**
     * Constructor, requires A correct Ip address in order to connect
     * @param ipAddress
     * @param playerName
     * @throws UnknownHostException
     */
    public Client(String ipAddress, String playerName) {
        try {
            address = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("incorrect IP address");
        }
        this.playerName = playerName;
    }

    /**
     * Connects a user to the server
     */
    public void connect() {
        try {
            System.out.println("creating new socket");
            socket = new Socket(address, port);
            /*socket.setKeepAlive(true);*/
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            sendUserName();
            printWriter.println("has connected");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void sendUserName () {
        printWriter.println(playerName);
    }
}
