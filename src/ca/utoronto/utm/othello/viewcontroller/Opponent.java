package ca.utoronto.utm.othello.viewcontroller;

/**
 * A class stores the information about the current game's opponent, which is
 * "human", "random" or "greedy".
 * @author Lang Qin
 *
 */
public class Opponent {
	private String opponent;
	
	public Opponent() {
		this.opponent = "Human";
	}
	void setOpponent(String opponent) {
		this.opponent = opponent;
	}
	String getOpponent() {
		return this.opponent;
	}
}
