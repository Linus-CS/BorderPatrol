package de.linuscs.Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class BoxOption {

	private int x;
	private int y;
	private int width;
	private int height;

	private Color color;
	private Color baseColor;
	private Color hoverColor;
	private Color textColor;

	private String option;

	private boolean choice;

	Font font;
	FontMetrics fontM;

	public BoxOption(String option, int x, int y, int width, int height) {
		this.option = option;
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;
	}

	public void init() {
		if (baseColor == null) {
			baseColor = Color.ORANGE;
		}
		if (hoverColor == null) {
			hoverColor = Color.PINK;
		}

		if (textColor == null) {
			textColor = Color.BLACK;
		}
		color = baseColor;
		choice = false;
		font = new Font("Minecraft", Font.BOLD, height);
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(textColor);
		g.setFont(font);
		g.drawString(option, (int) Math.round(x + (width * 0.028571)), (int) Math.round(y + (height * 0.9)));
		fontM = g.getFontMetrics();
	}

	public void onMoving(MouseEvent e) {
		if (e.getX() < this.x || e.getX() > this.x + this.width || e.getY() < this.y
				|| e.getY() > this.y + this.height) {
			color = baseColor;
			return;
		} else {
			color = hoverColor;
		}
	}

	public void onClick(MouseEvent e) {
		if (e.getX() < this.x || e.getX() > this.x + this.width || e.getY() < this.y
				|| e.getY() > this.y + this.height) {
			return;
		}
		choice = true;
	}

	public FontMetrics getFontM() {
		return fontM;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return option;
	}

	public void setName(String name) {
		this.option = name;
	}

	public void setBaseColor(Color color) {
		this.baseColor = color;
	}

	public void setHoverColor(Color color) {
		this.hoverColor = color;
	}

	public void setTextColor(Color color) {
		this.textColor = color;
	}

	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}
}
