package fr.chaffotm.quoridor.model;

public class Move {

    private Position to;

    public Move(final Position from, final int deltaColumn, final int deltaRow) {
        int column = from.getColumn() + deltaColumn;
        int row= from.getRow() + deltaRow;
        to = new Position(column, row);
    }

    public Position getTo() {
        return to;
    }

}
