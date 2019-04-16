package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

public class ServerController {

	private static ServerController controller;
		
	public static ServerController getInstance() {
		if(controller == null){
			controller = new ServerController();
		}
		return controller;
	}
	
	public void informarHora(Socket client) {
		System.out.println("Um cliente quer que eu informe a hora");
		this.enviarResposta(client, new Date().toString());
	}
	
	public void informarTemperatura(Socket client) {
		System.out.println("Um cliente quer que eu informe a temperatura");
		Integer temp = new Random().nextInt(40);
		this.enviarResposta(client, temp.toString() + " graus Celsius");
	}
	
	public void informarRoupa(Socket client) {
		System.out.println("Um cliente quer que eu indique uma roupa");
		
		String roupas[] = {"Roupa de Frio", "Roupa de Praia", "Roupa Esportiva", "Roupa Formal"};
		this.enviarResposta(client, roupas[new Random().nextInt(roupas.length)]);
		
	}
	
	public void informarPrato(Socket client) {
		System.out.println("Um cliente quer que eu indique um prato");
		
		String pratos[] = {"Lasanha", "Pizza", "Strogonoff", "Feijoada"};
		this.enviarResposta(client, pratos[new Random().nextInt(pratos.length)]);
	}
	
	private void enviarResposta (Socket client, String resposta) {
		try {
			DataOutputStream saida = new DataOutputStream(client.getOutputStream());
			saida.write(resposta.getBytes());
			saida.flush();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}