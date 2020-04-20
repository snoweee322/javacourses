import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[]args) throws IOException {
        // create devs/mans tables
        Interaction inter = new Interaction();
        inter.createDevelopers(20);
        inter.createManagers(10);

        // MySQL tables to .json format
        //System.out.println(ManInteract.fJd(config, sessionFactory));
        //System.out.println(DevInteract.fJm(config, sessionFactory));

        // server functionality
        ServerSocket server = new ServerSocket(8081);
        Socket client;
        int i = 0;
        while (i < 100) {
            System.out.println("\n\n>>>Request #"+i);
            client = server.accept();
            new Thread(new Functionality(client, inter)).start();
            i++;
        }
        server.close();
    }
}
