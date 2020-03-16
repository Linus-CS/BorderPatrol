package de.linuscs.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Line {

	private int x;
	private int y;

	private int height;
	private int width;
	
	private int id;

	Color color;
	private Boolean activated;

	public Line() {
		x = 0;
		y = 0;
		height = 90;
		width = 10;

		activated = false;

		color = Color.blue;
	}

	public void render(Graphics g) {
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
		if (e.getX() > this.x + width && !activated) {
			color = Color.blue;
			return;
		}
		if (e.getX() < this.x && !activated) {
			color = Color.blue;
			return;
		}
		if (e.getY() > this.y + height && !activated) {
			color = Color.blue;
			return;
		}
		if (e.getY() < this.y && !activated) {
			color = Color.blue;
			return;
		}

		if (!activated) {
			color = Color.pink;
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getX() > this.x + width && !activated) {
			color = Color.blue;
			return;
		}
		if (e.getX() < this.x && !activated) {
			color = Color.blue;
			return;
		}
		if (e.getY() > this.y + height && !activated) {
			color = Color.blue;
			return;
		}
		if (e.getY() < this.y && !activated) {
			color = Color.blue;
			return;
		}

		activated = true;
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
}
