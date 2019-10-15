package ApplicationChat;
import java.io.*;
import java.net.*;
public class ChatServer {
	public static void main(String[] args) {
		if (args.length < 1)
			return;
		int port = Integer.parseInt(args[0]);
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server is listening on port " + port);
			Message msg = new Message();
			ChatServerThread cst1 = new ChatServerThread(serverSocket.accept(), msg);
			ChatServerThread cst2 = new ChatServerThread(serverSocket.accept(), msg);
			cst1.start();
			cst2.start();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
		}
	}
}
	class ChatServerThread extends Thread {
		private BufferedReader reader;
		private PrintWriter writer;
		private Message message;
		public ChatServerThread(Socket socket, Message msg) throws IOException {
			reader = new BufferedReader(new
					InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			message = msg;
		}
		public void run() {
			try {
				while (true) {
					synchronized(message){
						writer.println(message.getMessage());
						message.setMessage((reader.readLine()));
					}
					System.out.println(message.getMessage());
				}
			} catch (Exception ex) {
				System.out.println("Server exception: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	class Message {
		public void setMessage(String str) {
			msg = str;
		}
		public String getMessage() {
			return msg;
		}
		String msg = "HELLO LP2I";
}