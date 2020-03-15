package de.linuscs.GameObjects;

import java.awt.Color;
import java.awt.Graphics;

import de.linuscs.Game.Game;

public class Border {

	private int x;
	private int y;

	private int length;

	public void init() {
		x = Game.WIDTH - (Game.WIDTH - 50);
		y = Game.HEIGHT - (Game.HEIGHT - 20);

		length = 1000;
	}

	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(x, y, 10, length);
		g.fillRect(x, y, length, 10);
		g.fillRect(x - 10 + length, y, 10, length);
		g.fillRect(x, y - 10 + length, length, 10);
	}
	
	public int getLength() {
		return length;
	}
}
