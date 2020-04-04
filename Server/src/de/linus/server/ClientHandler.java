package de.linus.server;

import java.util.ArrayList;

public abstract class ClientHandler {

	protected ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	
	public boolean borderGameLimit() {
		return false;
	}
	
	public void start() {
		for(ClientThread client : clients) {
			client.start();
		}
	}
	
	public void connectClients(){
		
	}
	
	public void addClient(ClientThread cT) {
		clients.add(cT);
	}
}
