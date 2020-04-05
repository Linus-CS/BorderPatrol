package de.linuscs.Entity;

public class Player {

	private String name;
	private int id;
	private int opponentScore;
	
	private Entity side = Entity.PLAYER1;
	
	private boolean accepted = false;
	private boolean yourTurn = false;
	private boolean host = false;
	
	public void opponentPlusOne() {
		opponentScore += 1;
	}
	
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
	
	public int getOpponentScore() {
		return opponentScore;
	}
	
	public boolean isHost() {
		return host;
	}
	
	public void setHost(boolean host) {
		this.host = host;
	}
}
