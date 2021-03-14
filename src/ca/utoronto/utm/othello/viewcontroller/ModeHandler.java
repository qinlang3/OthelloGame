package ca.utoronto.utm.othello.viewcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ModeHandler implements EventHandler<ActionEvent>{
	private OthelloGame othellogame;
	
	ModeHandler(OthelloGame othellogame) {
		this.othellogame = othellogame;
	}
	
	@Override
	public void handle(ActionEvent event) {
		Button source=(Button)event.getSource();
		othellogame.updateOpponent(source.getText());
	}
}
