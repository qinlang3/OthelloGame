package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;


public class InfoGrid extends GridPane implements Observer {
    private OthelloGame othellogame;


    private Label movesnext;
    private Label whitecount;
    private Label gamestatus;
    private Label games;
    private Label p1;
    private Label p2;
    private Label lblTime;
    private TextField blackctxt, whitectxt, p1txt, p2txt, hinttxt;
    private Button greedyhint, randomhint;


    //Create info pane
    public InfoGrid(OthelloGame othellogame, ModeHandler modehandler, OtherEventsHandler otherhandler) {
        super();
        this.othellogame = othellogame;
        this.setVgap(8);
        this.setHgap(8);
        this.setPadding(new Insets(50, 50, 50, 50));

        // Create text fields for showing the who's next, P1counts and P2 counts
        //Initialize image for labels
        Image blacks = new Image(getClass().getResourceAsStream("black.png"), 19, 19, false, false);
        movesnext = new Label(" moves next", new ImageView(blacks));

        Label blackcount = new Label(" count:", new ImageView(blacks));

        blackctxt = new TextField("2");
        blackctxt.setPrefColumnCount(3);
        blackctxt.setEditable(false);

        Image whites = new Image(getClass().getResourceAsStream("white.png"), 20, 20, false, false);
        whitecount = new Label(" count:", new ImageView(whites));

        whitectxt = new TextField("2");
        whitectxt.setPrefColumnCount(3);
        whitectxt.setEditable(false);

        gamestatus = new Label("Game Status:");

        games = new Label("In Progress");
        lblTime = new Label("Time Remaining");
        p1 = new Label(":", new ImageView(blacks));
        p2 = new Label(":", new ImageView(whites));

        p1txt = new TextField("Human");
        p1txt.setPrefColumnCount(3);
        p1txt.setEditable(false);
        p2txt = new TextField("Human");
        p2txt.setPrefColumnCount(3);
        p2txt.setEditable(false);
        OpponentTxt opponent = new OpponentTxt(p2txt);
        this.othellogame.attach(opponent);

        //Set hint buttons
        Button hint = new Button("Hint");
        hint.setOnAction(otherhandler);
        greedyhint = new Button("Greedy Hint: OFF");
        randomhint = new Button("Random Hint: OFF");
        hint.setOnAction(otherhandler);
        greedyhint.setOnAction(otherhandler);
        randomhint.setOnAction(otherhandler);
        
        //Set Undo buttons
        Button undo = new Button("Undo");


        // Set restart buttons
        Button restart = new Button("Restart");
        this.add(restart, 0, 10);
        restart.setOnAction(otherhandler);

        this.add(movesnext, 0, 0);
        this.add(blackcount, 0, 1);
        this.add(blackctxt, 1, 1);
        this.add(whitecount, 0, 2);
        this.add(whitectxt, 1, 2);
        this.add(gamestatus, 0, 3);
        this.add(lblTime, 1, 0);
        this.add(games, 1, 3);
        this.add(p1, 0, 4);
        this.add(p1txt, 1, 4);
        this.add(p2, 0, 5);
        this.add(p2txt, 1, 5);
        this.add(hint, 0, 9);
        this.add(undo, 1, 9);
        this.add(greedyhint, 0, 10);
        this.add(randomhint, 1, 10);

        // Add selections
        Button HvsH = new Button("Play HumanVsHuman");
        this.add(HvsH, 0, 6, 2, 1);
        HvsH.setOnAction(modehandler);
        Button HvsG = new Button("Play HumanVsGreedy");
        this.add(HvsG, 0, 7, 2, 1);
        HvsG.setOnAction(modehandler);
        Button HvsR = new Button("Play HumanVsRandom");
        this.add(HvsR, 0, 8, 2, 1);
        HvsR.setOnAction(modehandler);
        Button HvsP = new Button("Play HumanVsPurgatory");
        this.add(HvsP, 0, 11, 2, 1);
        HvsP.setOnAction(modehandler);
        undo.setOnAction(new UndoHandler());


        // VIEW COMPONENTS
        OthelloInfoBoard othelloinfoboard = new OthelloInfoBoard(this);

        // MODEL->VIEW hookup
        this.othellogame.get().attach(othelloinfoboard);
        this.setVisible(true);

        // Set Timer

    }


    void setMovesnext(ImageView movesnext) {
        this.movesnext.setGraphic(movesnext);
    }

    void setBlackcount(String blackcount) {
        this.blackctxt.setText(blackcount);
    }

    void setWhitecount(String whitecount) {
        this.whitectxt.setText(whitecount);
    }
    
    void setWinnerImage(ImageView who) {
    	this.games.setGraphic(who);
    }

    void setGamestatus(String gamestatus) {
        this.games.setText(gamestatus);
    }

    public TextField getBlackctxt() {
        return blackctxt;
    }
    

    void switchgreedyhint() {
    	if (this.greedyhint.getText().equals("Greedy Hint: OFF")) {this.greedyhint.setText("Greedy Hint: ON");}
    	else {this.greedyhint.setText("Greedy Hint: OFF");}
    }
    
    void switchrandomhint() {
    	if (this.randomhint.getText().equals("Random Hint: OFF")) {this.randomhint.setText("Random Hint: ON");}
    	else {this.randomhint.setText("Random Hint: OFF");}
    }

    public TextField getWhitectxt() {
        return whitectxt;
    }


    public TextField getP1txt() {
        return p1txt;
    }

    public void setP1txt(String p1txt) {
        this.p1txt.setText(p1txt);
    }

    public TextField getP2txt() {
        return p2txt;
    }

	public void setP2txt(String p2txt) {
		this.p2txt.setText(p2txt);
	}

	public String getGameStatus(){
        return this.gamestatus.getText();
    }

    void setLblTime(int time){
        this.lblTime.setText(Integer.toString(time));
    }
    @Override
    public void update(Observable o, Object arg) {

    }

    private class UndoHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            othellogame.undo();
            othellogame.notifyObservers();
        }
    }
}

