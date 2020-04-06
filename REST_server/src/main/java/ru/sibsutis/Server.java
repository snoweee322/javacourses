package ru.sibsutis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(8081);
        Socket client;
        int i = 0;
        while (i < 100) {
            System.out.println("\n\n>>>Request #"+i);
            client = server.accept();
            new Thread(new Thr(client)).start();
            i++;
        }
        server.close();
    }
}
