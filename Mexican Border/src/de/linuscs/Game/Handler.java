package de.linuscs.Game;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

import de.linuscs.Entity.Player;

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

	public void writeIntFromDOS(DataOutputStream dos, Player player) {
		for (GameObject object : gameObjects) {
			object.writeIntFromDOS(dos, player);
		}
	}

	public void readIntToDis(DataInputStream dis, Player player) {
		for (GameObject object : gameObjects) {
			object.readIntToDis(dis, player);
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
}
