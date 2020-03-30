package de.linuscs.GameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.linuscs.Entity.Player;
import de.linuscs.Game.Game;
import de.linuscs.Game.GameObject;

public class BoardMP extends GameObject {

	public ArrayList<Line> verLines = new ArrayList<Line>();
	public ArrayList<Line> horLines = new ArrayList<Line>();
	ArrayList<Box> boxes = new ArrayList<Box>();

	String[] lineIds = new String[180];

	double boardX;
	double boardY;
	double boardWidth;
	double boardHeight;

	private boolean gameEnd;

	private Border border;
	private Text text;
	private Player player;

	private ArrayList<Box> activatedBoxes = new ArrayList<Box>();

	public BoardMP(Player player) {
		this.player = player;
	}

	@Override
	public void init() {
		boardX = (45 / 1100f) * Game.WIDTH;
		boardY = (3 / 110f) * Game.WIDTH;
		boardWidth = (10 / 11f) * Game.WIDTH;
		boardHeight = (10 / 11f) * Game.HEIGHT;
		gameEnd = false;

		text = new Text((int) (15 / 110f * Game.WIDTH), (int) (5 / 11f * Game.WIDTH), (int) (6 / 110f *  Game.WIDTH), "Waiting for someone to connect ...");

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
	public void render(Graphics g) {

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

		if (!player.isAccepted()) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			text.render(g2d);
		}

		if (gameEnd) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			text.render(g2d);
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

		checkForVictory();
	}

	@Override
	public void readIntToDis(DataInputStream dis) {
		try {
			int lineID = dis.readInt();
			char rotation = dis.readChar();

			for (Line line : verLines) {
				line.setGlow(false);
				if (line.getId() == lineID && rotation == 'v') {
					line.setOpponentColor(true);
					line.setGlow(true);
				}
			}
			for (Line line : horLines) {
				line.setGlow(false);
				if (line.getId() == lineID && rotation == 'h') {
					line.setOpponentColor(true);
					line.setGlow(true);
				}
			}

			if (!checkForActivation()) {
				player.setYourTurn(true);
			} else {
				for (Box activBox : activatedBoxes) {
					player.opponentPlusOne();
					activBox.setOpponentColor(true);
				}
				activatedBoxes = new ArrayList<Box>();
			}

			checkForVictory();

			if (gameEnd)
				player.setYourTurn(true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeIntFromDOS(DataOutputStream dos) {
		boolean missedLine = true;
		try {
			for (Line line : verLines) {
				if (line.isActivated() && !line.isLightsOn() && !line.isOpponentColor()) {
					dos.writeInt(line.getId());
					dos.writeChar('v');
					missedLine = false;
					line.setActivated(false);
					line.setLightsOn(true);
				}
			}
			for (Line line : horLines) {
				if (line.isActivated() && !line.isLightsOn() && !line.isOpponentColor()) {
					dos.writeInt(line.getId());
					dos.writeChar('h');
					missedLine = false;
					line.setActivated(false);
					line.setLightsOn(true);
				}
			}
			if (!missedLine) {
				dos.flush();
				if (!checkForActivation()) {
					player.setYourTurn(false);
				} else {
					activatedBoxes = new ArrayList<Box>();
				}
			}

			checkForVictory();

		} catch (

		IOException e) {
			e.printStackTrace();
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

	private void createBoxesAndLines() {
		int id = 1;
		double distance = ((1 / 10f) * boardWidth);

		for (double y = 0; y < boardHeight - 1; y += distance) {
			for (double x = 0; x < boardWidth - 1; x += distance) {
				Box tempBox = new Box();

				tempBox.setX((int) (x + boardX));
				tempBox.setY((int) (y + boardY));
				tempBox.setId(id++);

				boxes.add(tempBox);
			}
		}

		id = 1;

		for (double y = 0; y < boardHeight - 1; y += distance) {
			for (double x = distance; x < boardWidth - 1; x += distance) {
				Line tempLineVe = new Line(player);
				
				tempLineVe.setX((int) (x + boardX));
				tempLineVe.setY((int) (y + boardY));
				tempLineVe.setId(id++);

				verLines.add(tempLineVe);
			}
		}

		id = 1;

		for (double y = distance; y < boardHeight - 1; y += distance) {
			for (double x = 0; x < boardWidth - 1; x += distance) {
				Line tempLineHo = new Line(player);

				tempLineHo.setX((int) (x + boardX));
				tempLineHo.setY((int) (y + boardY));
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

	private void checkForVictory() {
		update();
		int crosses = 0;

		for (Box box : boxes) {
			if (box.isDrawCross() || box.isOpponentColor()) {
				crosses++;
			}
		}

		if (crosses == 100) {
			gameEnd = true;
			text.setX(220);
			if (player.getOpponentScore() > 50) {
				text.setText("You lost the game retard");
			} else if (player.getOpponentScore() < 50) {
				text.setText("You won the game genius");
			} else {
				text.setText("The game ended in a tie");
			}
		}
	}
}
