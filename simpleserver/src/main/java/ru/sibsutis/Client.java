package ru.sibsutis;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1", 8081);
        Scanner in = new Scanner(client.getInputStream());
        PrintWriter out = new PrintWriter(client.getOutputStream());
        out.println("Hello Java");
        out.flush();
        String str = in.nextLine();
        System.out.println(str);
        client.close();
    }
}
