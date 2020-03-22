package de.linuscs.GameObjects;

import java.awt.Color;
import java.awt.Graphics;

import de.linuscs.Game.Game;

public class Border {

	private double x;
	private double y;

	private double length;

	public void init() {
		x = Game.WIDTH - (Game.WIDTH - (45 / 1100f) * Game.WIDTH);
		y = Game.HEIGHT - (Game.HEIGHT - (3 / 110f) * Game.WIDTH);

		length = (10.0 / 11.0f) * Game.WIDTH;
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, (int) length / 100, (int) length);
		g.fillRect((int) x, (int) y, (int) length, (int) length / 100);
		g.fillRect((int) x - (int) length / 100 + (int) length, (int) y, (int) length / 100, (int) length);
		g.fillRect((int) x, (int) y - (int) length / 100 + (int) length, (int) length, (int) length / 100);
	}
}
