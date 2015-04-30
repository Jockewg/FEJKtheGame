package com.fejkathegame.client;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Swartt on 2015-04-30.
 */
public class Client {
    protected static Socket socket;
    protected static PrintWriter printWriter;
    protected static int port = 6112;

    public static void main(String[] args) {
        connect();
    }

    public static void connect() {
        try {
            socket = new Socket("localhost", port);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("Hello Socket");
            printWriter.println("EYYYYYAAAAAAAA!!!!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
