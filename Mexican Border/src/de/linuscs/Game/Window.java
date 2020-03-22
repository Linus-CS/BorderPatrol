package de.linuscs.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Window extends JPanel implements Runnable {

	private static final long serialVersionUID = -2636892571186977079L;

	Game game;
	JFrame window = new JFrame("Mexikanische Grenze");

	private int width;
	private int height;

	public Window(int width, int height, Game game) {

		SwingUtilities.invokeLater(this);

		this.game = game;
		this.width = width;
		this.height = height;

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				game.handler.mouseClicked(e);
				if (game.connectionHandler != null)
					game.connectionHandler.mouseClicked(e);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (game.handler != null)
					game.handler.mouseMoved(e);
			}
		});

	}

	@Override
	public void run() {
		this.setBackground(Color.DARK_GRAY);

		window.setPreferredSize(new Dimension(width, height));
		window.setMinimumSize(new Dimension(width, height));
		window.setMaximumSize(new Dimension(width, height));

		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.add(this);
		window.pack();
		window.setLayout(null);
		window.setVisible(true);
	}

	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		game.render(g);
	}
}
