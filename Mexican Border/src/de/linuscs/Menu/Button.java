package de.linuscs.Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.linuscs.Game.Game;
import de.linuscs.Game.GameObject;
import de.linuscs.Game.State;

public class Button extends GameObject {

	Game game;
	State state;

	private int width, height;
	private int x, y;
	private Image img;

	private String name;
	private int nameWidth;
	private Font font;

	public Button(Image img, int width, int height, int x, int y, String name, State state, Game game) {
		this.img = img;

		this.width = width;
		this.height = height;

		this.x = x;
		this.y = y;

		this.name = name;
		this.state = state;
		this.game = game;
	}

	public Button(String imgName, int width, int height, int x, int y, String name, State state, Game game) {
		loadImage(imgName);

		this.width = width;
		this.height = height;

		this.x = x;
		this.y = y;

		this.name = name;
		this.state = state;
		this.game = game;
	}

	public Button(int width, int height, int x, int y, String name, State state, Game game) {
		this.width = width;
		this.height = height;

		this.x = x;
		this.y = y;

		this.name = name;
		this.state = state;
		this.game = game;
	}

	public Button(Image img, int width, int height, int x, int y, String name, State state, Font font, Game game) {
		this.img = img;

		this.width = width;
		this.height = height;

		this.x = x;
		this.y = y;

		this.name = name;
		this.state = state;
		this.game = game;
		this.font = font;
	}

	public Button(String imgName, int width, int height, int x, int y, String name, State state, Font font, Game game) {
		loadImage(imgName);

		this.width = width;
		this.height = height;

		this.x = x;
		this.y = y;

		this.name = name;
		this.state = state;
		this.game = game;
		this.font = font;
	}

	public Button(int width, int height, int x, int y, String name, State state, Font font, Game game) {
		this.width = width;
		this.height = height;

		this.x = x;
		this.y = y;

		this.name = name;
		this.state = state;
		this.game = game;
		this.font = font;
	}

	public Button(int width, int height, int x, int y, String name) {
		this.width = width;
		this.height = height;

		this.x = x;
		this.y = y;

		this.name = name;
	}

	private void loadImage(String imgName) {
		try {
			img = ImageIO.read(getClass().getResourceAsStream(imgName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		if (img == null)
			loadImage("/defaultButton.png");
		if (font == null)
			font = new Font("Times New Roman", Font.BOLD, (int) (32 / 100f * height));

		img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(font);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		nameWidth = g2d.getFontMetrics().stringWidth(name);

		g2d.drawImage(img, x, y, null);
		g2d.drawString(name, x + width / 2 - nameWidth / 2, y + height / 2);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getX() < x)
			return;
		if (e.getX() > x + width)
			return;
		if (e.getY() < y)
			return;
		if (e.getY() > y + height)
			return;

		if (state != null)
			game.setState(state);
		else {
			try {
				Runtime.getRuntime().exec("java -jar ./BorderPatrol.jar");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}

	}

}
