package ca.utoronto.utm.othello.model;

public class OthelloAndOthelloBoardVisitor implements Visitor{

    OthelloAndOthelloBoardVisitor(){

    }
    public void visit(Othello o){
        o.getWhosTurn();
        o.getCount(o.getWhosTurn());
        o.move(o.row, o.col);
        o.getWinner();
        o.isGameOver();
        o.getBoardString();
        o.copy();
        o.getamove("");
        o.getToken(o.row, o.col);
        o.setHint(o.hint);
        o.canMove(o.row, o.col);
    }
    public void visit(OthelloBoard ob){
        ob.get(ob.row, ob.col);
        char player = ob.get(ob.row, ob.col);
        ob.move(ob.row, ob.col, player);
        ob.toString();
        ob.getCount(player);
        ob.hasMove();
        ob.canMove(ob.row, ob.col, player);
        ob.copy();
        ob.findaMove(player);
        ob.getDimension();
        ob.restart();
    }
}
