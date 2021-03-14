package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.scene.control.TextField;

/**
 * A OpponentTxt class which will update the textfield of information about the opponent 
 * @author Lang Qin
 *
 */
public class OpponentTxt implements Observer{
	private TextField txt;
	
	OpponentTxt(TextField txt) {
		this.txt = txt;
	}

	public void update(Observable o) {
		OthelloGame othellogame = (OthelloGame) o;
		this.txt.setText(othellogame.getOpponent());
	}
}
