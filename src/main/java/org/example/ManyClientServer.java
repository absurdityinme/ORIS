package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ManyClientServer {
    public static final int SERVER_PORT = 50000;
    private static ServerSocket server;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(SERVER_PORT);
            System.out.println("start server");
            while (true) {
                try {
                    Socket clientSocket = server.accept();
                    ClientHandler handler = new ClientHandler(clientSocket);
                    new Thread(handler).start();
                }
                catch (IOException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serverClose() {
        if (server != null || !server.isClosed()) {
            try {
                server.close();
                System.out.println("close server");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
