package de.linuscs.Game;

import java.awt.Graphics;

import javax.swing.JPanel;

import de.linuscs.Connection.ConnectionHandler;
import de.linuscs.GameObjects.Board;

public class Game extends JPanel implements Runnable {

	private static final long serialVersionUID = 2027343941048492992L;

	public static final int WIDTH = 1100, HEIGHT = 1100;

	ConnectionHandler connectionHandler;
	Window window;
	Thread thread;
	public Handler handler;

	private boolean running = false;

	public Game() {
		connectionHandler = new ConnectionHandler();
		window = new Window(WIDTH, HEIGHT, this);
		handler = new Handler();

		handler.addGameObject(new Board());
		
		handler.init();

		start();
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
				System.out.println("FPS: " + frames);
				frames = 0;
			}
			
			connectionHandler.waitForRequest();
		}
		stop();
	}

	private void update() {
		handler.update();
	}

	public void render(Graphics g) {
		handler.render(g);
	}
}
