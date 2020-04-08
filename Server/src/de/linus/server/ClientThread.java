package de.linus.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

	private Socket socket;

	private DataOutputStream dos;
	private DataInputStream dis;

	private int inNumber;
	private char inCharakter;
	
	private int sendAmount;

	public ClientThread(Socket clientSocket) {
		this.socket = clientSocket;
		
		inNumber = 404;
		inCharakter = 'n';
		
		System.out.println(clientSocket.getInetAddress().getHostName() + " has joined");

		this.initDataStrm();
		this.start();
	}

	private void initDataStrm() {
		try {
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		while (true) {
			getValues();
			sendAmount++;
		}
//		dos.close();
//		dis.close();
//		socket.close();
	}

	private void getValues() {
		try {
			inCharakter = dis.readChar();
			inNumber = dis.readInt();
			System.out.println(inNumber);
			System.out.println(inCharakter);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("worked");
	}

	public void sendNumber(int i) {
		try {
			dos.writeInt(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendChar(char c) {
		try {
			dos.writeChar(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendBoolean(boolean b) {
		try {
			dos.writeBoolean(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void flush() {
		try {
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNumber() {
		return inNumber;
	}

	public char getChar() {
		return inCharakter;
	}
	
	public int getSendAmount() {
		return sendAmount;
	}
}
