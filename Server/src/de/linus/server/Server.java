package de.linus.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

	private ServerSocket serverSocket;
	private Socket socket;

	ClientHandler clientHandler;

	private String ip;
	private int port;

	public Server(ClientHandler clientHandler) throws UnknownHostException {
		this.clientHandler = (ClientHandler) clientHandler;

		ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println("Starting a Server on " + ip);
		System.out.println("Input port on which server is listening for request: ");

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
		socket = null;

		while (!clientHandler.borderGameLimit()) {
			try {
				System.out.println("Waiting for someone to connect. Request to join send.");
				socket = serverSocket.accept();
				System.out.println("Client " + socket.getInetAddress().getHostName()
						+ " has accepted request to join. We have accepeted client.");
			} catch (IOException e) {
				e.printStackTrace();
			}

			clientHandler.addClient(new ClientThread(socket, clientHandler));
		}
		clientHandler.start();
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
