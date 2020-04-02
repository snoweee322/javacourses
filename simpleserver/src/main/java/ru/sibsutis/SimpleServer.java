package ru.sibsutis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8081);
        Socket client;
        int i = 0;
        while (i < 100) { // 100 requests max
            client = server.accept();
            System.out.println("\n\nRequest #" + i);
            new Thread(new Thr(client)).start();
            i++;
        }
        server.close();
    }
}