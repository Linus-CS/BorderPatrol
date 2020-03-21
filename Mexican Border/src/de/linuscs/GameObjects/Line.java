package de.linuscs.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import de.linuscs.Entity.Player;
import de.linuscs.Game.Game;

public class Line {

	private int x;
	private int y;

	private double height;
	private double width;

	private int id;

	Color color;

	Player player;

	private boolean activated;
	private boolean lightsOn;
	private boolean opponentColor;
	private boolean glow;

	public Line(Player player) {
		this.player = player;

		x = 0;
		y = 0;
		height = 9.0 / 110.0f * Game.WIDTH;
		width = 1.0 / 110.0f * Game.WIDTH;

		activated = false;
		lightsOn = false;
		opponentColor = false;

		color = Color.WHITE;
	}

	public void render(Graphics g) {
		if (opponentColor)
			color = Color.RED;

		g.setColor(color);

		g.fillRect(x, y, (int) width, (int) height);

		if (glow) {
			g.setColor(Color.yellow);
			g.drawRect(x, y, (int) width, (int) height);
		}
	}

	public void rotate() {
		double oldHeight = height;
		double oldWidth = width;

		y = y - (int) (10 / 1100f * Game.WIDTH);
		x = x + (int) (10 / 1100f * Game.WIDTH);

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
		this.x = x - ((int) width / 2);
	}

	public void setY(int y) {
		this.y = y + (int) (5 / 1100f * Game.WIDTH);
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

	public boolean isGlow() {
		return glow;
	}

	public void setGlow(boolean glow) {
		this.glow = glow;
	}
}
