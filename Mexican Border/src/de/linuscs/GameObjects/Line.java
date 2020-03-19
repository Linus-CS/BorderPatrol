package de.linuscs.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.Scanner;

import de.linuscs.Entity.Player;

public class Line {

	private int x;
	private int y;

	private int height;
	private int width;

	private int id;

	Color color;

	Player player;

	private boolean activated;
	private boolean lightsOn;
	private boolean opponentColor;

	public Line(Player player) {
		this.player = player;

		x = 0;
		y = 0;
		height = 90;
		width = 10;

		activated = false;
		lightsOn = false;
		opponentColor = false;

		color = Color.WHITE;
	}

	public void render(Graphics g) {
		if (opponentColor)
			color = Color.RED;

		g.setColor(color);

		g.fillRect(x, y, width, height);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	public void rotate() {
		int oldHeight = height;
		int oldWidth = width;

		y = y - 10;
		x = x + 10;

		width = oldHeight;
		height = oldWidth;

	}

	public void mouseMoved(MouseEvent e) {
		if (e.getX() > this.x + width && !lightsOn && !opponentColor) {
			color = Color.WHITE;
			return;
		}
		if (e.getX() < this.x && !lightsOn && !opponentColor) {
			color = Color.WHITE;
			return;
		}
		if (e.getY() > this.y + height && !lightsOn && !opponentColor) {
			color = Color.WHITE;
			return;
		}
		if (e.getY() < this.y && !lightsOn && !opponentColor) {
			color = Color.WHITE;
			return;
		}

		if (!lightsOn) {
			color = Color.pink;
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getX() > this.x + width) {
			activated = false;
			return;
		}
		if (e.getX() < this.x) {
			activated = false;
			return;
		}
		if (e.getY() > this.y + height) {
			activated = false;
			return;
		}
		if (e.getY() < this.y) {
			activated = false;
			return;
		}

		if (player.isYourTurn()) {
			lightsOn = true;
			activated = true;

			color = Color.BLUE;
		}
	}

	public void setX(int x) {
		this.x = x - (width / 2);
	}

	public void setY(int y) {
		this.y = y + 5;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLightsOn() {
		return lightsOn;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean isOpponentColor() {
		return opponentColor;
	}

	public void setOpponentColor(boolean opponentColor) {
		this.opponentColor = opponentColor;
	}
}
