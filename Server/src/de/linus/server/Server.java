package de.linus.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import de.linus.mexicanBorder.Game;

public class Server {

	private ServerSocket serverSocket;
	private Socket socket;

	private String ip;
	private int port;

	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private ArrayList<Game> games = new ArrayList<Game>();

	private int counterGames;
	private int counterClients;

	public Server() throws UnknownHostException {

		ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println("Starting a Server on " + ip);
		System.out.println("Input port on which server should listen for request: ");

		while (port == 0) {
			try {
				port = Integer.parseInt(System.console().readLine());
				if (port < 350 || port > 6666) {
					port = 0;
					System.out.println("Please input a port inbetween 350 and 6666: ");
				}
			} catch (NumberFormatException eNum) {
				System.out.println("Please only input numbers: ");
			}
		}

		initServer();
		listenForServerRequest();
	}

	private void initServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
			System.out.println("Started server on " + ip + ":" + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listenForServerRequest() {
		while (true) {
			socket = null;
			try {
				System.out.println("Waiting for someone to connect. Request to join send.");
				socket = serverSocket.accept();
				System.out.println("Client " + socket.getInetAddress().getHostName() + " has accepted request to join. We have accepeted client.");
				clients.add(new ClientThread(socket));
			} catch (IOException e) {
				e.printStackTrace();
			}
			clients.get(counterClients).sendChar('s');
			if (clients.size() % 2 == 0) {
				counterGames++;
				int clientID = counterGames * 2;

				clients.get(counterClients).sendBoolean(false);
				clients.get(counterClients).flush();
				clients.get(counterClients - 1).sendBoolean(true);
				clients.get(counterClients - 1).flush();

				games.add(new Game(clients.get(clientID - 2), clients.get(clientID - 1)));
				System.out.println("Game " + counterGames + " has been created");
			} else {
				clients.get(counterClients).sendBoolean(true);
				clients.get(counterClients).flush();
			}
			counterClients++;
		}
	}

	private void commandInput() {
		InputStream inp = null;
		BufferedReader brinp = null;

		try {
			inp = socket.getInputStream();
			brinp = new BufferedReader(new InputStreamReader(inp));
		} catch (Exception e) {
			// TODO: handle exception
		}

		String line;
		while (true) {
			try {
				line = brinp.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
