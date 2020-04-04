package de.linus.mexicanBorder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.linus.server.ClientHandler;

public class MBHandler extends ClientHandler{

	@Override
	public void connectClients(){
		DataOutputStream dos1 = clients.get(0).getDos();
		DataOutputStream dos2 = clients.get(1).getDos();
		
		DataInputStream dis1 = clients.get(0).getDis();
		DataInputStream dis2 = clients.get(1).getDis();
		
		try {
			dos1.writeChars("player1");
			dos1.writeInt(dis2.readInt());
			dos1.writeChar(dis2.readChar());
			
			dos1.writeChars("player2");
			dos2.writeInt(dis1.readInt());
			dos2.writeChar(dis1.readChar());
			
			dos1.flush();
			dos2.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override 
	public boolean borderGameLimit() {
		if(clients.size() == 2) {
			return true;
		}
		return false;
	}
	
}
