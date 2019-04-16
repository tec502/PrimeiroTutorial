package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controller.ServerController;
import model.Request;

public class ProtocolProcessor implements Runnable{
	
	private Socket connection;
	private Object message;

	public ProtocolProcessor (Socket connection) {
		this.connection = connection;
		this.message = deserializeObject(); // linha adicionada ao construtor
	}
	
	private Object deserializeObject () {
		try {
			InputStream input = new ObjectInputStream(this.connection.getInputStream());

			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			ObjectOutputStream obj = new ObjectOutputStream(bytes);
			
			try {
				obj.writeObject(((ObjectInputStream) input).readObject());
				bytes.toByteArray();
				return this.deserializeMessage(bytes.toByteArray());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Object deserializeMessage(byte[] data) {
		ByteArrayInputStream message =
		                        new ByteArrayInputStream(data);
		
		try {
			ObjectInput reader = new ObjectInputStream(message);
			return (Object)reader.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public void run() {
		if (this.message instanceof Request) {
			Request request = (Request) this.message;

			if (request.getTag().equals("horario")) {
				ServerController.getInstance().informarHora(this.connection);
			} else if (request.getTag().equals("temperatura")) {
				ServerController.getInstance().informarTemperatura(this.connection);
			} else if (request.getTag().equals("roupa")) {
				ServerController.getInstance().informarRoupa(this.connection);
			} else if (request.getTag().equals("refeicao")) {
				ServerController.getInstance().informarPrato(this.connection);
			}
		}
	}
}