package de.linuscs.Menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import de.linuscs.Game.Game;
import de.linuscs.Game.GameObject;

public class InputField extends GameObject {

	private String text;
	private String label;
	private int labelWidth;

	private double x;
	private double y;
	private double width;
	private double height;

	private int lineX;
	private int baseX;
	private int lineY;
	private int lineHeight;

	private int textX;
	private int textY;
	private int textSize;

	private boolean selected;
	private boolean blink;
	private int count;

	private StringBuilder stringBuilder;

	private FontMetrics fontMetrics;
	private boolean hasNotGotFM;

	private int stringWidth;

	public InputField(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void init() {
		lineX = (int) (x + (10 / 1100f * Game.WIDTH));
		baseX = lineX;
		lineY = (int) (y + (10 / 1100f * Game.WIDTH));
		lineHeight = (int) (height - (20 / 1100f * Game.WIDTH));

		textX = lineX;
		textY = lineY + lineHeight;
		textSize = lineHeight;

		hasNotGotFM = true;

		stringBuilder = new StringBuilder();
	}

	@Override
	public void update() {
		if (selected)
			count++;
		if (count == 30) {
			count = 0;
			if (blink)
				blink = false;
			else
				blink = true;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setFont(new Font("Times New Roman", Font.BOLD, textSize));
		if (hasNotGotFM) {
			fontMetrics = g.getFontMetrics();
			hasNotGotFM = false;
			if (label != null)
				labelWidth = getStringWidth(label);
		}

		g.setColor(Color.black);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(4));
		g.drawRect((int) x, (int) y, (int) width, (int) height);

		if (text != null) {
			g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.drawString(text, textX, textY);
		}

		if (blink)
			g.drawLine(lineX, lineY, lineX, lineY + lineHeight);

		if (label != null) {
			g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.drawString(label, (int) (x - labelWidth - (10 / 1100f * Game.WIDTH)), (int) (lineY + lineHeight));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (selected) {
			if (e.getKeyCode() != 8) {
				stringBuilder.append(e.getKeyChar());
				text = stringBuilder.toString();
				stringWidth = getStringWidth(text);
			} else {
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
				text = stringBuilder.toString();
				stringWidth = getStringWidth(text);
			}
			blinkReLocate();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getX() < x || e.getX() > x + width || e.getY() < y || e.getY() > y + height) {
			blink = false;
			selected = false;
			return;
		}

		selected = true;
	}

	private int getStringWidth(String s) {
		return fontMetrics.stringWidth(s);
	}

	private void blinkReLocate() {
		if (text != null) {
			lineX = baseX;
			lineX += stringWidth;
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
