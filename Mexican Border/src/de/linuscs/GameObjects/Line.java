package de.linuscs.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import de.linuscs.Entity.Player;

public class Line {

	private int x;
	private int y;

	private int height;
	private int width;

	private int id;

	Color color;
	private boolean activated;
	private boolean changeColor;
	private boolean opponentColor;

	Player player;

	public Line() {
		x = 0;
		y = 0;
		height = 90;
		width = 10;

		activated = false;
		changeColor = false;
		opponentColor = false;

		color = Color.blue;
	}

	public void render(Graphics g) {
		if (opponentColor)
			color = Color.ORANGE;

		g.setColor(color);

		g.fillRect(x, y, width, height);
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
		if (e.getX() > this.x + width && !changeColor && !opponentColor) {
			color = Color.blue;
			return;
		}
		if (e.getX() < this.x && !changeColor && !opponentColor) {
			color = Color.blue;
			return;
		}
		if (e.getY() > this.y + height && !changeColor && !opponentColor) {
			color = Color.blue;
			return;
		}
		if (e.getY() < this.y && !changeColor && !opponentColor) {
			color = Color.blue;
			return;
		}

		if (!changeColor) {
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

		activated = true;
		changeColor = true;

		color = Color.GREEN;
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

	public boolean getActivated() {
		return activated;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setOpponentColor(boolean opponentColor) {
		this.opponentColor = opponentColor;
	}
}
