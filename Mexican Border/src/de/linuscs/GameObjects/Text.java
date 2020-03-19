package de.linuscs.GameObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Text{

	private int x;
	private int y;
	private String text;
	private int size;

	public Text(int x, int y, int size, String text) {
		this.x = x;
		this.y = y;
		this.text = text;

		this.size = size;
	}
	
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Impact", Font.PLAIN, size));
		g2d.drawString(text, x, y);
	}
	
	public void setText(String text) {
		this.text = text;
	}

}
