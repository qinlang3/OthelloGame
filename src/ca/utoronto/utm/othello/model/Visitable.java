package ca.utoronto.utm.othello.model;

interface Visitable {
    public void accept(OthelloAndOthelloBoardVisitor vistor);
}
