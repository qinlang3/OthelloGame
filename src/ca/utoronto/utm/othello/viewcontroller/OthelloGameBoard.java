package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
/**
 * 
 * An observer class, which will update the view of the TokensGrid as the game going.
 */
public class OthelloGameBoard implements Observer{
	private TokensGrids grid;
	private boolean greedyhint;
	private boolean randomhint;
	private Move randommove;
	
	public OthelloGameBoard(TokensGrids grid) {
		this.grid = grid;
		this.greedyhint = false;
		this.randomhint = false;
		this.randommove = null;
	}
	
	public void update(Observable o) {
		//System.out.println(((Othello)o).getBoardString());
		Platform.runLater(new Runnable() {//Use runLater to update GUI in main thread
			@Override
			public void run() {
				Othello othello = (Othello) o;
				Image black = new Image(getClass().getResourceAsStream("black.png"),38,38,false,false);
				Image white = new Image(getClass().getResourceAsStream("white.png"),40,40,false,false);
				Image outline = new Image(getClass().getResourceAsStream("outline4.png"),40,40,false,false);
				String hint = othello.getHint();
				if(randommove == null) {randommove = othello.getamove("random");}
				if(hint.equals("Greedy")) {
					if (greedyhint) {
						greedyhint = false;
						return;}
					greedyhint = true;
				}
				if(hint.equals("Random")) {
					if (randomhint) {
						randomhint = false;
						return;}
					randomhint = true;
				}
				for (int row=0;row<=7;row++) {
					for (int col=0;col<=7;col++) {
						grid.cancelbackgroundcolor(row, col);
					}
				}
				if (greedyhint) {hint = "Greedy";}
				if (randomhint) {hint = "Random";}
				if (hint.equals("Hint") || hint.equals("Greedy")) {
					Move move = othello.getamove(hint);
					grid.setbackgroundcolor(move.getRow(), move.getCol(), "yellow");
					othello.resetHint();
				}
				if (hint.equals("Random")) {
					if (othello.getismoved()) {
						randommove = othello.getamove("Random"); 
						othello.resetismoved();}
					grid.setbackgroundcolor(randommove.getRow(), randommove.getCol(), "yellow");
				}
				for (int row=0;row<=7;row++) {
					for (int col=0;col<=7;col++) {
						if (othello.getToken(row, col)==OthelloBoard.P1) {
							grid.setImage(row, col, black);
						}
						if (othello.getToken(row, col)==OthelloBoard.P2) {
							grid.setImage(row, col, white);
						}
						// Empty the grid
						if (othello.getToken(row, col) == OthelloBoard.EMPTY) {
							Button btn = (Button) grid.getButton(row, col);
							btn.setGraphic(null);
							grid.setImage(row, col, null);
						}

						// the available move
						if (othello.canMove(row, col)) {
							grid.setImage(row, col, outline);
						}
					}
				}
			}
		});
	}
}
				
					
					

