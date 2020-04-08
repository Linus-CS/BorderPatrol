package de.linuscs.Game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class GameObject{
	
	public void init() {
		// TODO Auto-generated method stub
	}
	
	public void render(Graphics g) {
		// TODO Auto-generated method stub
	}
	
	public void update() {
		// TODO Auto-generated method stub
	}
	
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	public void writeIntFromDOS(DataOutputStream dos) {
		// TODO Auto-generated method stub
	}

	public void readIntToDis(DataInputStream dis) {
		// TODO Auto-generated method stub
	}
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
