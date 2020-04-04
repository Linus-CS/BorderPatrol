package de.linuscs.Game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.swing.JPanel;

import de.linuscs.Connection.ConnectionHandler;
import de.linuscs.Entity.Player;
import de.linuscs.GameObjects.BoardMP;
import de.linuscs.GameObjects.BoardSP;
import de.linuscs.Menu.Background;
import de.linuscs.Menu.Button;
import de.linuscs.Menu.DropDownMenu;
import de.linuscs.Menu.InputField;
import de.linuscs.Settings.Settings;

public class Game extends JPanel implements Runnable {

	private static final long serialVersionUID = 2027343941048492992L;

	private State state;

	public static final int WIDTH = generateWidth(), HEIGHT = WIDTH;

	ConnectionHandler connectionHandler;
	Window window;
	Player player;
	public Handler handler;

	Image img;

	Thread thread;

	private String ip;
	private int port;
	
	InputField ipInputField;
	InputField portInputField;

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
			handler.addGameObject(new Background("/MAINMENU.png"));
			handler.addGameObject(new Button("/Play.png", (int) (23 / 110f * WIDTH), (int) (2 / 11f * WIDTH), (int) (78 / 110f * WIDTH), (int) (6 / 11f * WIDTH), "", State.ADRESS, this));
			handler.addGameObject(new Button("/Resolutions.png", (int) (325 / 1100f * WIDTH), (int) (2 / 11f * WIDTH), (int) (72 / 110f * WIDTH), (int) (74 / 110f * WIDTH), "", State.RESO, this));
			handler.addGameObject(new Button("/COOP.png", (int) (325 / 1100f * WIDTH), (int) (2 / 11f * WIDTH), (int) (72 / 110f * WIDTH), (int) (85 / 110f * WIDTH), "", State.COOP, this));

			handler.init();
		}

		if (state == State.ADRESS) {
			handler = new Handler();
			ipInputField = new InputField((int) (400 / 1100f * WIDTH), (int) (300 / 1100f * WIDTH), (int) (400 / 1100f * WIDTH), (int) (60 / 1100f * WIDTH));
			portInputField = new InputField((int) (400 / 1100f * WIDTH), (int) (500 / 1100f * WIDTH), (int) (400 / 1100f * WIDTH), (int) (60 / 1100f * WIDTH));
			
			ipInputField.setLabel("Input IP:");
			portInputField.setLabel("Input port:");
			
			handler.addGameObject(ipInputField);
			handler.addGameObject(portInputField);
			handler.addGameObject(new Button((int) (2 / 11f * WIDTH), (int) (1 / 11f * WIDTH), (int) (42 / 110f * WIDTH), (int) (90 / 110f * WIDTH), "Continue", State.GAME, this));

			handler.init();
		}
		if (state == State.GAME) {
			player = new Player();
			connectionHandler = new ConnectionHandler(this, player, port, ip);

			handler = new Handler();
			handler.addGameObject(new BoardMP(player));
			handler.init();
		}
		
		if (state == State.COOP) {
			player = new Player();

			handler = new Handler();
			handler.addGameObject(new BoardSP(player));
			handler.init();
		}
		if (state == State.RESO) {
			handler = new Handler();
			DropDownMenu ddMenu = new DropDownMenu((int) (25 / 110f * WIDTH), (int) (3 / 11f * WIDTH), (int) (55 / 110f * WIDTH), (int) (1 / 11f * WIDTH), "resolutions");
			ddMenu.addOption("700x700");
			ddMenu.addOption("800x800");
			ddMenu.addOption("900x900");
			ddMenu.addOption("1000x1000");
			ddMenu.addOption("1100x1100");

			handler.addGameObject(new Button((int) (2 / 11f * WIDTH), (int) (1 / 11f * WIDTH), (int) (42 / 110f * WIDTH), (int) (90 / 110f * WIDTH), "Restart"));
			handler.addGameObject(ddMenu);

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

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}

			if (state == State.GAME && connectionHandler != null)
				connectionHandler.waitForRequest();
		}
		stop();
	}

	private void update() {
		if(ipInputField != null && portInputField != null && ipInputField.getText() != null && portInputField.getText() != null) {
			ip = ipInputField.getText();
			port = Integer.parseInt(portInputField.getText());
		}
		if (handler != null)
			handler.update();
	}

	public void render(Graphics g) {
		if (handler != null)
			handler.render(g);
	}

	private static int generateWidth() {
		new Settings("settings");
		if(Settings.instance.getSetting("resolutions") == "1") {
			Settings.instance.addSetting("resolutions", "1000x1000");
		}
		String generateWidth = Settings.instance.getSetting("resolutions");
		StringBuilder tempStringBuilder = new StringBuilder();
		for (int i = 0; i < generateWidth.length(); i++) {
			if (generateWidth.charAt(i) == 'x')
				break;
			else
				tempStringBuilder.append(generateWidth.charAt(i));
		}
		generateWidth = tempStringBuilder.toString();

		return Integer.parseInt(generateWidth);
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
