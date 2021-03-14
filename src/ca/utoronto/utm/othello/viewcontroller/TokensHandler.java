package ca.utoronto.utm.othello.viewcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class TokensHandler implements EventHandler<ActionEvent>{
	private OthelloGame othellogame;
	
	public TokensHandler(OthelloGame othellogame) {
		this.othellogame = othellogame;
	}
	
	@Override
	public void handle(ActionEvent event) {
		Button source=(Button)event.getSource();
		try {
			this.othellogame.move(source);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
