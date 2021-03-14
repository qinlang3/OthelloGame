package ca.utoronto.utm.othello.model;

import java.util.ArrayList;

/**
 * A player that plays with greedy strategy and improved by purgatory
 */
public class PlayerPurgatory extends PlayerGreedy {
	
	public PlayerPurgatory(Othello othello, char player) {
		super(othello, player);
	}
	
	/**
	 * 
	 * return the most greedy move in a list
	 */
	public Move getMost(ArrayList<Move> same) {
		Othello othelloCopy = othello.copy();
		Move bestMove = new Move(0, 0);
		int bestMoveCount=othelloCopy.getCount(this.player);
		
		for (Move move: same) {
			othelloCopy = othello.copy();
			if(othelloCopy.move(move.getRow(), move.getCol()) && othelloCopy.getCount(this.player)>bestMoveCount) {
				bestMoveCount = othelloCopy.getCount(this.player);
				bestMove = move;
			}
		}
		
		return bestMove;
	}
	
	public Move getMove() {
		Move bestMove = new Move(0, 0);
		ArrayList<ArrayList<Move>> whole = new ArrayList<ArrayList<Move>>();
		ArrayList<Move> corner = new ArrayList<Move>();
		ArrayList<Move> side = new ArrayList<Move>();
		ArrayList<Move> fourtimes4 = new ArrayList<Move>();
		ArrayList<Move> someelse = new ArrayList<Move>();
		whole.add(corner); whole.add(side); whole.add(fourtimes4); whole.add(someelse);
		
		for(int row=0;row<Othello.DIMENSION;row++) {
			for(int col=0;col<Othello.DIMENSION;col++) {
				if (this.othello.canMove(row, col)) {
					this.othello.sortMove(row, col, whole);
				}
			}
		}
		for (ArrayList<Move> sorted: whole) {
			if (!sorted.isEmpty()) {
				return getMost(sorted);
			}
		}
		return bestMove;
	}
}

