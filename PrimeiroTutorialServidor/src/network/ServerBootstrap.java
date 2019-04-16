package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBootstrap {

	public static void main(String[] args) {
		ServerSocket socket;
		try {
			socket = new ServerSocket(25356);
			while(true) {
				System.out.println("Server running at port: " + socket.getLocalPort());
				Socket received = socket.accept();
				new Thread(new ProtocolProcessor(received)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}