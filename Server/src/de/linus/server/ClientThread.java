package de.linus.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

	private Socket socket;
	private ClientHandler clientHandler;
	
	private DataOutputStream dos;
	private DataInputStream dis;

	public ClientThread(Socket clientSocket, ClientHandler clientHandler) {
		this.socket = clientSocket;
		this.clientHandler = clientHandler;
	}

	@Override
	public void run() {
		try {
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			return;
		}
		
		while(true) {
			clientHandler.connectClients();
		}
	}
	
	public DataOutputStream getDos() {
		return dos;
	}
	
	public DataInputStream getDis() {
		return dis;
	}
}
