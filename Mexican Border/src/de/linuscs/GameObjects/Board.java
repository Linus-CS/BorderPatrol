package de.linuscs.GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.linuscs.Entity.Player;
import de.linuscs.Game.GameObject;

public class Board extends GameObject {

	public ArrayList<Line> lines = new ArrayList<Line>();
	ArrayList<Box> boxes = new ArrayList<Box>();

	String[] lineIds = new String[180];

	int boardX;
	int boardY;

	private Border border;

	@Override
	public void init() {
		boardX = 50;
		boardY = 20;

		border = new Border();

		border.init();

		for (int y = 0; y < 1000; y += 100) {
			for (int x = 0; x < 1000; x += 100) {
				Box tempBox = new Box();

				tempBox.setX(x + boardX);
				tempBox.setY(y + boardY);

				boxes.add(tempBox);
			}
		}

		int ids = 0;

		for (int y = 0; y < 1000; y += 100) {
			for (int x = 100; x < 1000; x += 100) {
				Line tempLineVe = new Line();

				tempLineVe.setX(x + boardX);
				tempLineVe.setY(y + boardY);
				tempLineVe.setId(ids++);

				lines.add(tempLineVe);
			}
		}

		for (int y = 100; y < 1000; y += 100) {
			for (int x = 0; x < 1000; x += 100) {
				Line tempLineHo = new Line();

				tempLineHo.setX(x + boardX);
				tempLineHo.setY(y + boardY);
				tempLineHo.rotate();
				tempLineHo.setId(ids++);

				lines.add(tempLineHo);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect(boardX, boardY, 1000, 1000);
		border.render(g);
		for (Box box : boxes) {
			box.render(g);
		}
		for (Line line : lines) {
			line.render(g);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (Line line : lines) {
			line.mouseMoved(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (Line line : lines) {
			line.mouseClicked(e);
		}
	}

	@Override
	public void readIntToDis(DataInputStream dis, Player player) {
		try {
			int lineID;
			lineID = dis.readInt();
			for (Line line : lines) {
				if (line.getId() == lineID) {
					line.setOpponentColor(true);
					line.setPlayer(player);
				}
			}
			player.setYourTurn(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeIntFromDOS(DataOutputStream dos, Player player) {
		boolean missedLine = true;
		try {
			for (Line line : lines) {
				if (line.getActivated()) {
					dos.writeInt(line.getId());
					missedLine = false;
				}
			}
			if (!missedLine) {
				dos.flush();
				player.setYourTurn(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
