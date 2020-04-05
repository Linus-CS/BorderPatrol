package de.linus.mexicanBorder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.linus.server.ClientHandler;

public class MBHandler extends ClientHandler {

	@Override
	public void connectClients() {
		DataOutputStream dos1 = clients.get(0).getDos();
		DataOutputStream dos2 = clients.get(1).getDos();

		DataInputStream dis1 = clients.get(0).getDis();
		DataInputStream dis2 = clients.get(1).getDis();

		try {
			dos1.writeChars("player1");
			dos2.writeChars("player2");

			dos1.flush();
			dos2.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			int intValue1 = 0;
			char charValue1 = ' ';
			
			int intValue2 = 0;
			char charValue2 = ' ';
			
			try {
				intValue1 = dis1.readInt();
				charValue1 = dis1.readChar(); 
				
				intValue2 = dis1.readInt();
				charValue2 = dis1.readChar(); 
				
				dos1.writeInt(intValue2);
				dos1.writeChar(charValue2);

				dos2.writeInt(intValue1);
				dos2.writeChar(charValue1);

				dos1.flush();
				dos2.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean limit() {
		if (clients.size() == 2) {
			return true;
		}
		return false;
	}

}
