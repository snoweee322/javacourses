import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class Thr implements Runnable {
	Socket client;
	Thr (Socket client) {
		this.client = client;
	}
	@Override
	public void run() {
		Scanner in = null;
		try {
			in = new Scanner(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter out = null;
		try {
			out = new PrintWriter(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str = in.nextLine();
		System.out.print(str);
		String tmp = "HTTP/1.1 200 OK\r\n" +
				"Accept-Ranges: bytes\r\n" +
				"Content-Length: 40\r\n" +
				"Connection: close\r\n" +
				"Content-Type: text/html\r\n" +
				"\r\n" +
				"<html><body><h1>Hello</h1></body></html>";
		System.out.println(tmp);
		out.print(tmp);
		out.flush();
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

public class ServerWithThreads {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8081);
		Socket client;
		int i = 0;
		while (i < 100) {
			client = server.accept();
			System.out.println("\n\nRequest #" + i);
			new Thread(new Thr(client)).start();
			i++;
		}
		server.close();
	}
}