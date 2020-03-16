package de.linuscs.Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler{

	Thread thread;

	private String ip = "localhost";
	private int port = 22222;

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ServerSocket serverSocket;

	private boolean yourTurn = false;
	private boolean enemy = false;
	private boolean accepted = false;
	private boolean unableToCommunicateWithOpponent = false;

	private String[] lines = new String[180];

	private int errors = 0;

	private String waiting = "Waiting for another player";

	public ConnectionHandler() {
		if (!connect())
			initServer();
	}

	public void waitForRequest() {
		// Waiting for request
		while (true) {
			update();
			if (!enemy && !accepted) {
				listenForServerRequest();
			}
		}
	}

	private void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
			System.out.println("Client has accepted request join and we have accepetd");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean connect() {
		try {
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;

		} catch (IOException e) {
			System.out.println("Unable to connect to the ip address: " + ip + ":" + port + " | Starting a Server");
			return false;
		}
		System.out.println("Successfuly connected to the server");
		return true;
	}

	private void initServer() {

		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch (IOException e) {
			e.printStackTrace();
		}
		yourTurn = true;
		enemy = false;
	}

	private void update() {
		if (errors >= 10)
			unableToCommunicateWithOpponent = true;
		if (!yourTurn && !unableToCommunicateWithOpponent) {
			try {
				int line = dis.readInt();
				if (enemy)
					lines[line] = "enemy";
				else
					lines[line] = "player";
				yourTurn = true;

			} catch (IOException e) {
				e.printStackTrace();
				errors++;
			}
		}
	}

	public void writeInt(int num) {
		try {
			dos.writeInt(num);
			dos.flush();
		} catch (IOException e) {
			errors++;
			e.printStackTrace();
		}	
	}

	public String[] getLines() {
		return lines;
	}
}
