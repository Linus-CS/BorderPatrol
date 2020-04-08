package de.linus.mexicanBorder;

import de.linus.server.ClientThread;

public class Game extends Thread {

	private ClientThread client1;
	private ClientThread client2;

	private int numberForClient1;
	private int numberForClient2;

	private char charakterForClient1;
	private char charakterForClient2;

	private int amountClient1;
	private int amountClient2;

	public Game(ClientThread client1, ClientThread client2) {
		this.client1 = client1;
		this.client2 = client2;
		
		amountClient1 = 1;
		amountClient2 = 1;
		
		this.start();
	}

	@Override
	public void run() {
		while (true) {

			if (client2.getSendAmount() != amountClient2) {
				numberForClient1 = client2.getNumber();
				charakterForClient1 = client2.getChar();
				amountClient2 = client2.getSendAmount();
			}else {
				numberForClient1 = 404;
				charakterForClient1 = 'n';
			}

			if (client1.getSendAmount() != amountClient1) {
				numberForClient2 = client1.getNumber(); // 404
				charakterForClient2 = client1.getChar(); // 'n'
				amountClient1 = client1.getSendAmount();
			}else {
				numberForClient2 = 404; 
				charakterForClient2 = 'n';
			}

			if (hasInput(numberForClient2, charakterForClient2)) {
				client2.sendNumber(numberForClient2);
				client2.sendChar(charakterForClient2);
				client2.flush();
			}

			if (hasInput(numberForClient1, charakterForClient1)) {
				client1.sendNumber(numberForClient1);
				client1.sendChar(charakterForClient1);
				client1.flush();
			}
		}
	}

	private boolean hasInput(int i, char c) {
		if (i != 404 && c != 'n') {
			return true;
		}
		return false;
	}

}
