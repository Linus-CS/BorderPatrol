package de.linuscs.Entity;

public class Player {

	private String name;
	private int id;
	private Entity side = Entity.Player1;
	
	private boolean accepted = false;
	private boolean yourTurn = false;
	
	public Entity getSide() {
		return side;
	}
	
	public void setSide(Entity side) {
		this.side = side;
	}
	
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	public boolean isYourTurn() {
		return yourTurn;
	}
	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}
	
	
	
}
