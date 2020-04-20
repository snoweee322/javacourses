import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Functionality implements Runnable {
    private final Socket client;
    public String tmp;
    public Interaction inter;

    Functionality(Socket client, Interaction inter) {
        this.client = client;
        this.inter = inter;
    }

    public void run_() throws IOException {

        Scanner in = new Scanner(client.getInputStream());
        PrintWriter out = new PrintWriter(client.getOutputStream());
        String str = in.nextLine();
        System.out.println("CLIENT REQUEST: " + str);
        String second;
        Matcher matcher;
        Integer num;
        Integer num2;

        String JSON = null;
        int sw = -1;
        if (str.contains("managers")) {
            JSON = inter.fJm();
            sw = 0;
        } else if (str.contains("developers")) {
            JSON = inter.fJd();
            sw = 0;
        }

        if (sw == 0) { // managers || developers
            tmp = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + JSON.length() + "\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" +
                    JSON;
            System.out.println(tmp);
            out.print(tmp);
            out.flush();

            if (str.contains("managers")) {
                if (str.contains("delete")) {
                    str = str.replace("/managers/delete/","");
                    matcher = Pattern.compile("\\d+").matcher(str); // deletion
                    matcher.find();
                    num = Integer.parseInt(matcher.group()); // id
                    inter.del("m", num); // (manager, id)
                }
                if (str.contains("update")) {
                    str = str.replace("/managers/update/","");
                    matcher = Pattern.compile("\\d+").matcher(str); // deletion
                    matcher.find();
                    num = Integer.parseInt(matcher.group()); // id
                    matcher.find();
                    num2 = Integer.parseInt(matcher.group()); // KPI
                    inter.updName("m", num, num2);
                }
            } else if (str.contains("developers")) {
                if (str.contains("delete")) {
                    str = str.replace("/developers/delete/","");
                    matcher = Pattern.compile("\\d+").matcher(str); // deletion
                    matcher.find();
                    num = Integer.parseInt(matcher.group()); // id
                    inter.del("d", num); // (manager, id)
                }
                if (str.contains("update")) {
                    str = str.replace("/developers/update/","");
                    matcher = Pattern.compile("\\d+").matcher(str); // deletion
                    matcher.find();
                    num = Integer.parseInt(matcher.group()); // id
                    matcher.find();
                    num2 = Integer.parseInt(matcher.group()); // KPI
                    inter.updName("d", num, num2);
                }
            }

        } else {
            tmp = "HTTP/1.1 200 OK\r\n" +
                    "Accept-Ranges: bytes\r\n" +
                    "Content-Length: 52\r\n" +
                    "Connection: close\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n" +
                    "<html><body><h1>This is a server!</h1></body></html>";
            System.out.println(tmp);
            out.print(tmp);
            out.flush();
        }
    }

    @Override
    public void run() {
        try {
            run_();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
