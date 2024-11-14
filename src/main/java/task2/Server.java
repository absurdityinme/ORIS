package task2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static int SERVER_PORT = 5000;

    public static void main (String[] args){
        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            System.out.println("start server");
            Socket clientSocket = server.accept();

            System.out.println("connected " + clientSocket.getInetAddress() +
                    ":" + clientSocket.getPort());
            RequestHandler.handle(clientSocket);
            System.out.println("close server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
