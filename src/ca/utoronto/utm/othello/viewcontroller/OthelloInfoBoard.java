package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * An observer class, which will update the view of the infoGrid as the game going.
 */
public class OthelloInfoBoard implements Observer{
	private InfoGrid grid;

	public OthelloInfoBoard(InfoGrid grid) {
		this.grid = grid;
	}

	/**
	 * Update information board
	 * @param o
	 */
	public void update(Observable o) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Othello othello = (Othello) o;
				String hint = othello.getHint();
				grid.setLblTime(((Othello) o).getTimeRemaining());
				if (hint.equals("Greedy")) {
					grid.switchgreedyhint();
					othello.resetHint();
					return;
				}
				if (hint.equals("Random")) {
					grid.switchrandomhint();
					othello.resetHint();
					return;
				}
				Image blacks = new Image(getClass().getResourceAsStream("black.png"),19,19,false,false);
				Image whites = new Image(getClass().getResourceAsStream("white.png"),19,19,false,false);
				if (othello.getWhosTurn()==OthelloBoard.P1) {
					grid.setMovesnext(new ImageView(blacks));
				}
				if (othello.getWhosTurn()==OthelloBoard.P2) {
					grid.setMovesnext(new ImageView(whites));
				}
				if (othello.getWhosTurn()==OthelloBoard.EMPTY) {
					grid.setMovesnext(null);
				}
				grid.setBlackcount(othello.getCount(OthelloBoard.P1)+"");
				grid.setWhitecount(othello.getCount(OthelloBoard.P2)+"");
				if(othello.isGameOver()) {
					String result = "";
					if (othello.getWinner()==OthelloBoard.P1) {
						grid.setGamestatus(null);
						grid.setWinnerImage(new ImageView(blacks));
						grid.setGamestatus("black coin won!");
						result = "balck coin won";
					}

					if (othello.getWinner()==OthelloBoard.P2) {
						grid.setGamestatus(null);
						grid.setWinnerImage(new ImageView(whites));
						grid.setGamestatus("white coin won!");
						result = "white coin won";
					}

					if (othello.getWinner()==OthelloBoard.EMPTY) {
						grid.setGamestatus("Tied Game!");
						result = "tied game";
					}
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText(result);
					alert.setTitle("Game result");
					alert.showAndWait();
					((Stage)grid.getParent().getScene().getWindow()).close();
				}
			}
		});

	}
}
