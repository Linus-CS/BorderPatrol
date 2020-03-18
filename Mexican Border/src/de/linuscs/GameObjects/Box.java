package de.linuscs.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Box {

	private int x;
	private int y;
	private int id;

	private ArrayList<Line> verNeighbors;
	private ArrayList<Line> horNeighbors;

	private boolean drawCross;
	private boolean activated;
	private boolean opponentColor;

	Color color;

	public Box() {
		x = 0;
		y = 0;

		color = Color.GREEN;
		
		activated = false;
		drawCross = false;
		opponentColor = false;
		
	}

	public void update() {
		if (!drawCross)
			drawCross = checkForCross();
	}

	private boolean checkForCross() {
		boolean verLinesActivated = false;
		int count = 0;

		if (verNeighbors != null && verNeighbors.size() != 0) {
			for (Line line : verNeighbors) {
				if (line.isOpponentColor() || line.isLightsOn()) {
					count++;
				}
			}
			if (count == verNeighbors.size())
				verLinesActivated = true;
		}

		boolean horLinesActivated = false;
		count = 0;

		if (horNeighbors != null && horNeighbors.size() != 0) {
			for (Line line : horNeighbors) {
				if (line.isOpponentColor() || line.isLightsOn()) {
					count++;
				}
			}

			if (count == horNeighbors.size())
				horLinesActivated = true;
		}

		if (horLinesActivated && verLinesActivated) {
			activated = true;
			return true;
		} else
			return false;
	}

	public void render(Graphics g) {
		if (opponentColor)
			color = Color.ORANGE;
		
		g.setColor(color);

		// cross
		if (drawCross) {
			g.drawLine(x, y, x + 100, y + 100);
			g.drawLine(x + 100, y, x, y + 100);
		}
	}

	public void resetNeighbors() {
		verNeighbors = new ArrayList<Line>();
		horNeighbors = new ArrayList<Line>();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void addVerNeigbour(Line line) {
		verNeighbors.add(line);
	}

	public void addHorNeigbours(Line line) {
		horNeighbors.add(line);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
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
