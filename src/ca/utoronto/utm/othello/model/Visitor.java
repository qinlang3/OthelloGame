package ca.utoronto.utm.othello.model;

public interface Visitor {
    public void visit(Othello o);
    public void visit(OthelloBoard ob);
}
