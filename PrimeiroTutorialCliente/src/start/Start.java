package start;

import controller.ClientController;

public class Start {
	
	private static String serverIp = "localhost";
	private static int serverPort = 25356;
	
	public static void main(String[] args) {
		ClientController client = new ClientController(serverIp, serverPort);
		
		client.getHora();
		client.getRoupa();
		client.getTemperatura();
		client.getPrato();
	}
}