package de.linuscs.Menu;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.linuscs.Game.Game;
import de.linuscs.Game.GameObject;

public class Background extends GameObject {

	private Image image;

	public Background(String imagePath) {
		try {
				image = ImageIO.read(getClass().getResourceAsStream("/MAINMENU.png")).getScaledInstance(Game.WIDTH, Game.HEIGHT, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}
	}
}