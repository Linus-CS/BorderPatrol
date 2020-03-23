package de.linuscs.Game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

public class Handler {

	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	public void init() {
		for (GameObject object : gameObjects) {
			object.init();
		}
	}

	public void render(Graphics g) {
		for (GameObject object : gameObjects) {
			object.render(g);
		}
	}

	public void update() {
		for (GameObject object : gameObjects) {
			object.update();
		}
	}

	public void addGameObject(GameObject object) {
		gameObjects.add(object);
	}

	public void writeIntFromDOS(DataOutputStream dos) {
		for (GameObject object : gameObjects) {
			object.writeIntFromDOS(dos);
		}
	}

	public void readIntToDis(DataInputStream dis) {
		for (GameObject object : gameObjects) {
			object.readIntToDis(dis);
		}
	}

	public void mouseMoved(MouseEvent e) {
		for (GameObject object : gameObjects) {
			object.mouseMoved(e);
		}
	}

	public void mouseClicked(MouseEvent e) {
		for (GameObject object : gameObjects) {
			object.mouseClicked(e);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		for (GameObject object : gameObjects) {
			object.keyReleased(e);
		}
	}
}
