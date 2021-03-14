package ca.utoronto.utm.othello.viewcontroller;
import ca.utoronto.utm.othello.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class OtherEventsHandler implements EventHandler<ActionEvent>{
	private OthelloGame othellogame;

	OtherEventsHandler(OthelloGame othellogame) {
		this.othellogame = othellogame;
	}

	@Override
	public void handle(ActionEvent event) {
		Button source=(Button)event.getSource();
		if (source.getText().equals("Hint")) {
			this.othellogame.othello.setHint("Hint");
		}
		if (source.getText()=="Restart") {
			this.othellogame.restart();
		}
		if(source.getText().equals("Greedy Hint: OFF") || source.getText().equals("Greedy Hint: ON")) {
			this.othellogame.othello.setHint("Greedy");
		}
		if(source.getText().equals("Random Hint: OFF") || source.getText().equals("Random Hint: ON")) {
			this.othellogame.othello.setHint("Random");
		}
	}

}
