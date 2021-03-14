package ca.utoronto.utm.othello.model;
import ca.utoronto.utm.othello.viewcontroller.OthelloGame;
import ca.utoronto.utm.othello.viewcontroller.OthelloGameBoard;
import ca.utoronto.utm.util.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 * @author arnold
 *
 */
public class Othello extends Observable implements Visitable{
	public static final int DIMENSION=8; // This is an 8x8 game
	public String hint = "";
	private OthelloBoard board=new OthelloBoard(DIMENSION);
	private char whosTurn = OthelloBoard.P1;
	private int numMoves = 0;
	private int timeRemaining = 0;
	private boolean runOutOfTime = false;
	private char winner;
	private boolean ismoved;
	public int getTimeRemaining(){
		return timeRemaining;
	}
	public void runOutOfTime(char player){
		this.runOutOfTime = true;
		winner = OthelloBoard.otherPlayer(player);
	}
	/**
	 * Setting remaining time for current player
	 * @param timeRemaining int
	 */
	public void setTimeRemaining(int timeRemaining){
		this.timeRemaining = timeRemaining;
		notifyObservers();
	}
	/**
	 * restart a new game.
	 */
	public void restart() {
		hint = "";
		whosTurn = OthelloBoard.P1;
		numMoves = 0;
		board.restart();
		this.notifyObservers();
	}
	
	/**
	 * return the hint message
	 * 
	 * @return Hint or GreedyHint or Random Hint
	 */
	public String getHint() {
		return this.hint;
	}
	
	/**
	 * set the hint message
	 * 
	 */
	public void setHint(String message) {
		this.hint = message;
		this.notifyObservers();
	}
	
	/**
	 * reset the hint message to empty
	 * 
	 */
	public void resetHint() {
		this.hint = "";
	}
	
	/**
	 * get one of a greedy, random or any move
	 * 
	 */
	public Move getamove(String playermessage) {
		if (playermessage.equals("Greedy")) {
			PlayerGreedy player = new PlayerGreedy(this, this.getWhosTurn());
			return player.getMove();
		}
		else if (playermessage.equals("Random")) {
			PlayerRandom player = new PlayerRandom(this, this.getWhosTurn());
			return player.getMove();
		}
		return this.board.findaMove(whosTurn);
	}
	
	/**
	 * @return if the player move
	 * 
	 */
	public boolean getismoved() {
		return this.ismoved;
	}
	
	/**
	 * reset the ismoved
	 * 
	 */
	public void resetismoved() {
		this.ismoved = false;
	}
	
	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		return this.whosTurn;
	}
	
	/**
	 * 
	 * @param row 
	 * @param col
	 * @return the token at position row, col.
	 */
	public char getToken(int row, int col) {
		return this.board.get(row, col);
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public int row, col;
	public boolean move(int row, int col) {
		if(this.board.move(row, col, this.whosTurn)) {
			this.whosTurn = OthelloBoard.otherPlayer(this.whosTurn);
			char allowedMove = board.hasMove();
			if(allowedMove!=OthelloBoard.BOTH)this.whosTurn=allowedMove;
			this.numMoves++;
			this.ismoved = true;
			this.notifyObservers();
			return true;
		} else {
			this.notifyObservers();
			return false;
		}

	}
	


	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return board.getCount(player);
	}


	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		if(runOutOfTime) return this.winner;
		if(!this.isGameOver())return OthelloBoard.EMPTY;
		if(this.getCount(OthelloBoard.P1)> this.getCount(OthelloBoard.P2))return OthelloBoard.P1;
		if(this.getCount(OthelloBoard.P1)< this.getCount(OthelloBoard.P2))return OthelloBoard.P2;
		return OthelloBoard.EMPTY;
	}


	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		return this.whosTurn==OthelloBoard.EMPTY || runOutOfTime;
	}

	/**
	 * 
	 * @return a copy of this. The copy can be manipulated without impacting this.
	 */
	public Othello copy() {
		Othello o= new Othello();
		o.board=this.board.copy();
		o.numMoves = this.numMoves;
		o.whosTurn = this.whosTurn;
		return o;
	}


	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString()+"\n";
	}


	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		Random rand = new Random();


		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while(!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if(o.move(row, col)) {
				System.out.println("makes move ("+row+","+col+")");
				System.out.println(o.getBoardString()+ o.getWhosTurn()+" moves next");
			}
		}
	}
	
	/**
	 * 
	 * return if the position the player can move to
	 */
	public boolean canMove(int row, int col) {
		return this.board.canMove(row, col, this.whosTurn);
	}


	public void accept(OthelloAndOthelloBoardVisitor vistor){
		vistor.visit(this);
	}
	
	/**
	 * 
	 * Put the move in the appropriate position according to the priority
	 */
	public void sortMove(int row, int col, ArrayList<ArrayList<Move>> lst) {
		if (this.board.isCorner(row, col)) {
			lst.get(0).add(new Move(row, col));
			return;
		}
		if (this.board.isSide(row, col)){
			lst.get(1).add(new Move(row, col));	
			return;
		}
		if (this.board.is4times4(row, col)){
			lst.get(2).add(new Move(row, col));
			return;
		}
		lst.get(3).add(new Move(row, col));

	}

	public OthelloBoard getBoard(){
		return this.board;
	}

	public int getNumMoves(){
		return this.numMoves;
	}
	public void setNewGame(Othello othello){
		this.hint = othello.getHint();
		this.timeRemaining = othello.getTimeRemaining();
		this.board = othello.getBoard();
		this.whosTurn = othello.getWhosTurn();
		this.numMoves = othello.getNumMoves();
	}
}


