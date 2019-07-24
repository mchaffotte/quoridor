package fr.chaffotm.quoridor.dto;

import java.util.Objects;

public class PositionDto {

    private int column;

    private int row;

    protected PositionDto() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionDto that = (PositionDto) o;
        return column == that.column &&
                row == that.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public String toString() {
        return "PositionDto{" +
                "column=" + column +
                ", row=" + row +
                '}';
    }

}
