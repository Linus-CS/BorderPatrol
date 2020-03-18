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

	public ArrayList<Line> verLines = new ArrayList<Line>();
	public ArrayList<Line> horLines = new ArrayList<Line>();
	ArrayList<Box> boxes = new ArrayList<Box>();

	String[] lineIds = new String[180];

	int boardX;
	int boardY;

	private Border border;

	private ArrayList<Box> activatedBoxes = new ArrayList<Box>();

	@Override
	public void init() {
		boardX = 50;
		boardY = 20;

		border = new Border();

		border.init();

		createBoxesAndLines();
		addNeigboursToBoxes();
	}

	@Override
	public void update() {
		for (Box box : boxes) {
			box.update();
		}
	}

	@Override
	public void checkForVictory(Player player) {
		
	}
	
	private void createBoxesAndLines() {

		int id = 1;

		for (int y = 0; y < 1000; y += 100) {
			for (int x = 0; x < 1000; x += 100) {
				Box tempBox = new Box();

				tempBox.setX(x + boardX);
				tempBox.setY(y + boardY);
				tempBox.setId(id++);

				boxes.add(tempBox);
			}
		}

		id = 1;

		for (int y = 0; y < 1000; y += 100) {
			for (int x = 100; x < 1000; x += 100) {
				Line tempLineVe = new Line();

				tempLineVe.setX(x + boardX);
				tempLineVe.setY(y + boardY);
				tempLineVe.setId(id++);

				verLines.add(tempLineVe);
			}
		}

		id = 1;

		for (int y = 100; y < 1000; y += 100) {
			for (int x = 0; x < 1000; x += 100) {
				Line tempLineHo = new Line();

				tempLineHo.setX(x + boardX);
				tempLineHo.setY(y + boardY);
				tempLineHo.rotate();
				tempLineHo.setId(id++);

				horLines.add(tempLineHo);
			}
		}
	}

	private void addNeigboursToBoxes() {
		for (Box box : boxes) {
			box.resetNeighbors();
			int boxRow = 0;
			if (box.getId() % 10 != 0)
				boxRow = box.getId() / 10;
			else
				boxRow = box.getId() / 10 - 1;

			for (Line line : verLines) {
				if (line.getId() == (box.getId() - 1) - boxRow || line.getId() == box.getId() - boxRow) {
					if (box.getId() % 10 == 0 && line.getId() == box.getId() - boxRow)
						continue;
					else if (box.getId() % 10 == 1 && line.getId() == (box.getId() - 1) - boxRow)
						continue;
					else
						box.addVerNeigbour(line);
				}
			}
			for (Line line : horLines) {
				if (line.getId() == box.getId() || line.getId() == (box.getId() - 10)) {
					box.addHorNeigbours(line);
				}
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
		for (Line line : verLines) {
			line.render(g);
		}
		for (Line line : horLines) {
			line.render(g);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (Line line : verLines) {
			line.mouseMoved(e);
		}
		for (Line line : horLines) {
			line.mouseMoved(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (Line line : verLines) {
			line.mouseClicked(e);
		}
		for (Line line : horLines) {
			line.mouseClicked(e);
		}
	}

	private boolean checkForActivation() {
		for (Box box : boxes) {
			box.update();
			if (box.isActivated()) {
				box.setActivated(false);
				activatedBoxes.add(box);
			}
		}
		if (activatedBoxes.size() != 0)
			return true;
		else
			return false;
	}

	@Override
	public void readIntToDis(DataInputStream dis, Player player) {
		try {
			int lineID = dis.readInt();
			char rotation = dis.readChar();

			for (Line line : verLines) {
				if (line.getId() == lineID && rotation == 'v') {
					line.setOpponentColor(true);
				}
			}
			for (Line line : horLines) {
				if (line.getId() == lineID && rotation == 'h') {
					line.setOpponentColor(true);
				}
			}
			if (!checkForActivation()) {
				player.setYourTurn(true);
			} else {
				for (Box activBox : activatedBoxes) {
					activBox.setOpponentColor(true);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeIntFromDOS(DataOutputStream dos, Player player) {
		boolean missedLine = true;
		try {
			for (Line line : verLines) {
				if (line.isActivated()) {
					dos.writeInt(line.getId());
					dos.writeChar('v');
					missedLine = false;
					line.setActivated(false);
				}
			}
			for (Line line : horLines) {
				if (line.isActivated()) {
					dos.writeInt(line.getId());
					dos.writeChar('h');
					missedLine = false;
					line.setActivated(false);
				}
			}
			if (!missedLine) {
				dos.flush();
				if (!checkForActivation()) {
					player.setYourTurn(false);
				}
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}
}
