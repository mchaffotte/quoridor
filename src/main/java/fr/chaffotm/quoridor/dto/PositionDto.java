package fr.chaffotm.quoridor.dto;

public class PositionDto {

    private final int column;

    private final int row;

    public PositionDto(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

}
