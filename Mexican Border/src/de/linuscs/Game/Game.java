package de.linuscs.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.linuscs.Connection.ConnectionHandler;
import de.linuscs.Entity.Player;
import de.linuscs.GameObjects.Board;
import de.linuscs.Menu.Button;

public class Game extends JPanel implements Runnable {

	private static final long serialVersionUID = 2027343941048492992L;

	private State state;

	public static final int WIDTH = 1100, HEIGHT = 1100;

	ConnectionHandler connectionHandler;
	Window window;
	Player player;
	public Handler handler;

	Thread thread;
	
	private String ip;
	private int port;

	private boolean running = false;

	public Game() {
		state = State.MENU;
		window = new Window(WIDTH, HEIGHT, this);

		init();
		start();
	}

	private void init() {
		if (state == State.MENU) {
			handler = new Handler();
			handler.addGameObject(new Button(200, 100, 400, 400, "Play", State.GAME, this));
			handler.init();
		}
		if (state == State.GAME) {
			System.out.println("Input IP: ");
			ip = System.console().readLine();
			System.out.println("Input port: ");
			port = Integer.parseInt(System.console().readLine());
			
			player = new Player();
			connectionHandler = new ConnectionHandler(this, player, port, ip);

			handler = new Handler();
			handler.addGameObject(new Board(player));
			handler.init();
		}
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		running = true;
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			if (running)
				window.repaint();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				System.out.println("FPS: " + frames);
				frames = 0;
			}

			if (state == State.GAME && connectionHandler != null)
				connectionHandler.waitForRequest();
		}
		stop();
	}

	private void update() {
		if (handler != null)
			handler.update();
	}

	public void render(Graphics g) {
		if (handler != null)
			handler.render(g);
	}

	public void writeIntFromDOS(DataOutputStream dos) {
		if (handler != null)
			handler.writeIntFromDOS(dos);
	}

	public void readIntToDis(DataInputStream dis) {
		if (handler != null)
			handler.readIntToDis(dis);
	}

	public void setState(State state) {
		this.state = state;
		init();
	}
}
