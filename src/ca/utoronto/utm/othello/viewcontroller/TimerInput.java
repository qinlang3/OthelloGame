package ca.utoronto.utm.othello.viewcontroller;


import ca.utoronto.utm.othello.model.Othello;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A view that collects input from user and start game pane.
 */
class TimerInput extends VBox {
    private TextField txtPlayer1, txtPlayer2;

    TimerInput() {
        Label lblPlayer1 = new Label("Enter the time for player 1 in second");
        Label lblPlayer2 = new Label("Enter the time for player 2 in second");
        txtPlayer1 = new TextField();
        txtPlayer2 = new TextField();
        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnAction(this::collectInputText);
        this.getChildren().addAll(lblPlayer1, txtPlayer1, lblPlayer2, txtPlayer2, btnSubmit);
    }

    private void collectInputText(Event x) {
        int t1 = 120, t2 = 120;
        try {
            t1 = Integer.parseInt(txtPlayer1.getText());
            t2 = Integer.parseInt(txtPlayer2.getText());
            if (t1 <= 0 || t2 <= 0) throw new Exception();
        } catch (Exception ignored) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("INAVLID INPUT, PLEASE PUT INTEGERS > 0");
            alert.showAndWait();
            return;
        }

        // If the input is valid, then start the game.
        ((Stage) this.getScene().getWindow()).close();
        Stage stage = new Stage();
        // Create and hook up the Model, View and the controller
        // MODEL
        Othello othello = new Othello();
        OthelloGame othellogame = new OthelloGame(othello, t1, t2);

        // CONTROLLER
        // CONTROLLER->MODEL hookup
        OtherEventsHandler otherhandler = new OtherEventsHandler(othellogame);
        TokensHandler tokenshandler = new TokensHandler(othellogame);
        ModeHandler modehandler = new ModeHandler(othellogame);
        //Main Scene Setup
        HBox grid = new HBox();
        Scene scene = new Scene(grid);
        stage.setTitle("Othello");
        stage.setScene(scene);

        //Scene combination
        TokensGrids tksPane = new TokensGrids(othellogame, tokenshandler);
        InfoGrid infoPane = new InfoGrid(othellogame, modehandler, otherhandler);
        grid.getChildren().addAll(tksPane, infoPane);
        //Start Timing
        othellogame.startTiming();
        // LAUNCH THE GUI
        stage.show();


    }
}
