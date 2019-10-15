package ApplicationChat;

import java.net.*;
import java.io.*;
import java.util.Scanner;
public class ChatClient { 
	private String hote;
	private int port;
	public ChatClient(String hote_, int port_) {
		hote = hote_;
		port = port_;
	}
	public void execute() {
		Scanner sc = new Scanner(System.in);
		Socket socket = null;
		try {
			socket = new Socket(hote, port);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			String msg;
			while(true){
				msg = reader.readLine();
				System.out.println(msg);
				writer.println(sc.nextLine());
			}
		} catch (IOException e) {
			System.out.format("Probleme de connection avec serveur fontionne : %s", e);
			System.exit(-1);
		}
	}
	public static void main(String[] args) {
		if (args.length < 2)
			return;
		ChatClient test = new ChatClient(args[0], Integer.parseInt(args[1]));
		test.execute();
	}
}
