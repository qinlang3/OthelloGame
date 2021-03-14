package ca.utoronto.utm.othello.viewcontroller;
import java.util.Optional;

import ca.utoronto.utm.othello.model.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A view class that stores the game board view.
 */
class TokensGrids extends GridPane{

    private Button [] [] buttons;

    TokensGrids(OthelloGame othellogame, TokensHandler tokenshandler){
        super();
        // MODEL
        Othello othello = othellogame.get();
        this.setHgap(8);
        this.setVgap(8);
        this.setPadding(new Insets (50,0,50,50));
        this.buttons = new Button[8][8];
        for(int row=0;row<=7;row++) {
            for (int col=0;col<=7;col++) {
                this.buttons[row][col] = new Button();
                this.add( this.buttons[row][col], col, row);
                this.buttons[row][col].setMinSize(50,50);
                this.buttons[row][col].setMaxSize(50,50);
                this.buttons[row][col].setOnAction(tokenshandler);
            }
        }

        //initialize the black and white tokens.
        Image black = new Image(getClass().getResourceAsStream("black.png"), 38, 38, false, false);
        setImage(3, 3, black);
        Image white = new Image(getClass().getResourceAsStream("white.png"), 40, 40, false, false);
        setImage(3, 4, white);
        setImage(4, 3, white);
        setImage(4, 4, black);
        Image outline = new Image(getClass().getResourceAsStream("outline4.png"), 40, 40, false, false);
        setImage(2, 4, outline);
        setImage(3, 5, outline);
        setImage(5, 3, outline);
        setImage(4, 2, outline);
      

        // VIEW COMPONENTS
        OthelloGameBoard othellogameboard = new OthelloGameBoard(this);

        // MODEL->VIEW hookup
        othello.attach(othellogameboard);
        this.setVisible(true);
    }
    
    /**
     * 
     * return the button at the given row and column.
     */
    Button getButton(int row, int col) {
    	return this.buttons[row][col];
    }
    
    /**
     * 
     * Set Image to the button at the given row and column.
     */
    void setImage(int row, int col, Image pic) {
    	ImageView view = new ImageView();
    	view.setImage(pic);
    	this.buttons[row][col].setGraphic(view);
    }
    
    /**
     * 
     * Set background color to the button at the given row and column.
     */
    void setbackgroundcolor(int row, int col, String color) {
    	this.buttons[row][col].setStyle("-fx-background-color: " + color);
    }
    
    /**
     * 
     * Cancel background setting to the button at the given row and column.
     */
    public void cancelbackgroundcolor(int row, int col) {
    	this.buttons[row][col].setStyle(null);
    }

}

