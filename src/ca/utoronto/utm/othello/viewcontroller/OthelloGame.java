package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.othello.model.Player;
import ca.utoronto.utm.othello.model.PlayerGreedy;
import ca.utoronto.utm.othello.model.PlayerHuman;
import ca.utoronto.utm.othello.model.PlayerPurgatory;
import ca.utoronto.utm.othello.model.PlayerRandom;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A Othello controller that stores the current othello game and information about
 * user's opponent and takes control of all possible commands from the user interface.
 * @author kevin
 *
 */

public class OthelloGame extends Observable implements Observer {
	/**
	 * A timer class that stores the timing for each player
	 */
	private class CountTimer{
		private int timeRemaining1;
		private int timeRemaining2;
		private String currentTime;
		private int currentTimeRemaining;
		private TimerTask currentTiming;
		private Timer t;

		CountTimer(int t1, int t2){
			this.timeRemaining1 = t1;
			this.timeRemaining2 = t2;
			currentTimeRemaining = t1;
			currentTime = "t1";
			t = new Timer();
			currentTiming = new CountTime();
		}

		/**
		 * Switch to next player.
		 */
		void switchTerm(){
			if(currentTime.equals("t1")){
				timeRemaining1 = currentTimeRemaining;
				currentTimeRemaining = timeRemaining2;
				currentTime = "t2";
			}else{
				timeRemaining2 = currentTimeRemaining;
				currentTimeRemaining = timeRemaining1;
				currentTime = "t1";
			}
		}

		/**
		 * Start timing
		 */
		void startTiming(){
			t.schedule(currentTiming, 0, 1000);
		}

		/**
		 * A small class that operates in other thread to count time
		 */
		private class CountTime extends TimerTask{
			@Override
			public void run() {
				currentTimeRemaining -= 1;
				othello.setTimeRemaining(currentTimeRemaining);
				if(currentTimeRemaining <= 0){
					othello.runOutOfTime(othello.getWhosTurn());
					this.cancel();
				}
			}
		}
	}
	protected Othello othello;
	private Opponent opponent = new Opponent();
	private CountTimer ct;
	private ArrayList<Othello> lastothello;
	
	OthelloGame(Othello othello, int t1, int t2) {
		this.othello = othello;
		this.ct = new CountTimer(t1, t2);
		this.lastothello = new ArrayList<Othello>();
	}

	/**
	 * Start timing for the game.
	 */
	void startTiming(){
		this.ct.startTiming();
	}

	/**
	 * get the current othello
	 * @return Othello
	 */
	Othello get() {
		return this.othello;
	}
	
	/**
	 * Undo one step
	 */
	void undo() {
		if(this.lastothello.size() == 0) {return;}
		this.othello.setNewGame(this.lastothello.get(lastothello.size() - 1));
		this.lastothello.remove(lastothello.size() - 1);
		this.othello.notifyObservers();
	}
	
	/**
	 * @return a String representation of the opponent
	 */
	String getOpponent() {
		return this.opponent.getOpponent();
	}

	/**
	 * make the computer to make moves(as long as the turn does not change)
	 */
	private void Computermove() {
		if (this.opponent.getOpponent().equals("Random")) {
			while (this.othello.getWhosTurn()==OthelloBoard.P2) {
				Player player = new PlayerRandom(this.othello,OthelloBoard.P2);
				Move move = player.getMove();
				this.othello.move(move.getRow(), move.getCol());
			}
		}
		if (this.opponent.getOpponent().equals("Greedy")) {
			while (this.othello.getWhosTurn()==OthelloBoard.P2) {
				Player player = new PlayerGreedy(this.othello,OthelloBoard.P2);
				Move move = player.getMove();
				this.othello.move(move.getRow(), move.getCol());
			}
		}
		if (this.opponent.getOpponent().equals("Purgatory")) {
			while (this.othello.getWhosTurn()==OthelloBoard.P2) {
				Player player = new PlayerPurgatory(this.othello,OthelloBoard.P2);
				Move move = player.getMove();
				this.othello.move(move.getRow(), move.getCol());
			}
		}

	}
	
	/**
	 * update the information about the opponent.
	 */
	void updateOpponent(String mode) {
		if (mode.equals("Play HumanVsHuman")) {
			this.opponent.setOpponent("Human");
		}
		if (mode.equals("Play HumanVsRandom")) {
			this.opponent.setOpponent("Random");
		}
		if (mode.equals("Play HumanVsGreedy")) {
			this.opponent.setOpponent("Greedy");
		}
		if (mode.equals("Play HumanVsPurgatory")) {
			this.opponent.setOpponent("Purgatory");
		}
		this.notifyObservers();
		if (this.othello.getWhosTurn()==OthelloBoard.P2) {
			this.Computermove();
		}
	}
	
	/**
	 * update the information about the hint.
	 */
	public void updateHint(String hint_messege) {
		this.notifyObservers();
	}
	
	/**
	 * Make a move according to the position selected by user, and as a 
	 * result, the computer will also make a move if the opponent is not 
	 * human.
	 * @param source
	 */
	public void move(Button source) throws InterruptedException {
		int row = GridPane.getRowIndex(source);
		int col = GridPane.getColumnIndex(source);
		Othello x = this.othello.copy();
		if(this.othello.move(row,col)){
			this.lastothello.add(x);
			ct.switchTerm();
			synchronized (source){
				source.wait(500);
			}
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					source.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("AnimationIcon.png"),40,40,false,false)));
				}
			});
			notifyObservers();
		}
//		System.out.println(lastothello.size());
//		System.out.println(lastothello.get(lastothello.size() - 1).getBoardString());
		this.Computermove();
	}
	
	/**
	 * 
	 * restart a new game
	 */
	void restart() {
		this.othello.restart();
	}

	@Override
	public void update(Observable o) {
		notifyObservers();
	}
}

