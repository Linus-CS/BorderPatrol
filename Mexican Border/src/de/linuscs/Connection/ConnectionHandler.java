package de.linuscs.Connection;

import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import de.linuscs.Entity.Entity;
import de.linuscs.Entity.Player;
import de.linuscs.Game.Game;

public class ConnectionHandler {

	Game game;

	private String ip = "localhost";
	private int port = 22222;

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ServerSocket serverSocket;

	Player player;

	public ConnectionHandler(Game game, Player player, int port, String ip) {
		this.game = game;
		this.player = player;

		if (port != 0)
			this.port = port;
		if (ip != null)
			this.ip = ip;

		if (!connect())
			initServer();
	}

	public void waitForRequest() {
		// Waiting for request
		update();
		if (player.getSide() == Entity.PLAYER2 && !player.isAccepted() && player.isHost()) {
			listenForServerRequest();
		}
	}

	private void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			player.setAccepted(true);
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
		} catch (IOException e) {
			System.out.println("Unable to connect to the ip address: " + ip + ":" + port);
			return false;
		}
		System.out.println("Successfuly connected to the server");
		player.setAccepted(true);
		return true;
	}

	private void initServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch (IOException e) {
			e.printStackTrace();
		}
		player.setYourTurn(true);
		player.setSide(Entity.PLAYER2);
	}

	private void update() {
		
		if (!player.isYourTurn()) {
			if (dis != null)
				readIntToDis();
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (player.isYourTurn())
			writeIntFromDOS();
	}

	private void readIntToDis() {
		game.readIntToDis(dis);
	}

	private void writeIntFromDOS() {
		game.writeIntFromDOS(dos);
	}
}
