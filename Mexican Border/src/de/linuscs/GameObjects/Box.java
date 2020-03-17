package de.linuscs.GameObjects;

import java.awt.Color;
import java.awt.Graphics;

public class Box {

	private int x;
	private int y;

	Color color;

	public Box() {
		x = 0;
		y = 0;
	}

	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.drawRect(x, y, 100, 100);

		// cross
		g.drawLine(x, y, x + 100, y + 100);
		g.drawLine(x + 100, y, x, y + 100);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
